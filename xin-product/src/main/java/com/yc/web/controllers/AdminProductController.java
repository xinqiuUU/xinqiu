package com.yc.web.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.AdminJsonModel;
import com.yc.dao.ProductMapper;
import com.yc.dao.ProductPicMapper;
import com.yc.utils.PicUrl;
import com.yc.utils.ProductPicUtil;
import com.yc.web.model.Comment;
import com.yc.web.model.Product;
import com.yc.web.model.ProductPic;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//全部  Rest API 风格
@RestController
@RequestMapping("/product/admin")   //请求路径的前缀  http://localhost:8080/product/ + xxx
@Slf4j
@Tag(name = "商品API" , description = "商品管理相关接口")
@RefreshScope  // 动态刷新配置文件
public class AdminProductController {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductPicUtil productPicUtil;
    @Autowired
    private ProductPicMapper productPicMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    // 查看所有商品  并计算平均评分
    @GetMapping("/getAllProductsWithRating")
    public AdminJsonModel getAllProductsWithRating(@RequestParam int page, @RequestParam int limit) {
        AdminJsonModel jm = new AdminJsonModel();

        // 计算偏移量
        int offset = (page - 1) * limit;

        // 创建查询条件，获取所有产品
        List<Product> products = productMapper.selectList(null);
        // 获取评论信息
        List<Comment> comments = mongoTemplate.findAll(Comment.class);

        Map<Integer, List<Integer>> productRatingMap = new HashMap<>(); // 使用 Long 类型的 pid

        // 统计每个产品的评分
        for (Comment comment : comments) {
            Integer productId = Integer.valueOf(comment.getPid()); // 确保此处 pid 是 Long 类型
            if (productId != null) {
                productRatingMap.computeIfAbsent(productId, k -> new ArrayList<>()).add(comment.getRating());
            }
        }

        // 计算每个产品的平均评分
        for (Product product : products) {
            List<Integer> ratings = productRatingMap.get(product.getPid());
            if (ratings != null && !ratings.isEmpty()) {
                double averageRating = ratings.stream().mapToInt(Integer::intValue).average().orElse(0);
                product.setRating(averageRating); // 设置产品的平均评分
            }
        }

        // 分页处理
        int totalProducts = products.size(); // 获取所有产品的数量
        long count = totalProducts; // 总记录数
        int start = Math.min(offset, totalProducts);
        int end = Math.min(start + limit, totalProducts);
        for (Product product : products){
            if ( product.getRating() == null){
                product.setRating(0.0);
            }else {
                //保留两位小数
                product.setRating(Math.round(product.getRating() * 100.0) / 100.0);
            }
        }
        // 根据评分降序排序
        products.sort(Comparator.comparing(Product::getRating).reversed());

        // 获取分页结果
        List<Product> paginatedProducts = products.subList(start, end); // 分页结果
        for (Product product : paginatedProducts){
            productPicUtil.getProductPic(product, product.getPid());
            List<String> urls = product.getUrls();
            if (urls!= null &&!urls.isEmpty()){
                product.setUrl(urls.get(0));
            }
        }
        // 设置返回值
        jm.setCode(0);
        jm.setMsg("商品查询成功");
        jm.setData(paginatedProducts);
        jm.setCount(count);

        return jm;
    }



    /**
     * 获取指定商品的商品描述，但是是反转义为html
     * pid  前端发送的请求参数
     */
    @PostMapping("/getHtmlDescription")
    public AdminJsonModel getHtmlDescription(@RequestParam int pid) {
        AdminJsonModel jm = new AdminJsonModel();

        // 使用 MyBatis Plus 查询商品
        Product product = productMapper.selectById(pid);

        if (product == null) {
            jm.setCode(1);
            jm.setMsg("商品详情查看失败");
            return jm;
        }

        // 商品描述
        String translation = product.getDescription();

        jm.setCode(0);
        jm.setMsg("用户详情查询成功");
        jm.setData(translation);
        return jm;
    }

    // 查看所有商品
    @GetMapping("/getAllProducts")
    public AdminJsonModel getAllProducts(@RequestParam String page, @RequestParam String limit,
                                         @RequestParam(required = false) String pname,
                                         @RequestParam(required = false) String type,
                                         @RequestParam(required = false) String firm,
                                         @RequestParam(required = false) Integer status) {
        AdminJsonModel jm = new AdminJsonModel();

        // 确保页面参数和每页条数参数有效
        int currentPage = Integer.parseInt(page);
        int pageSize = Integer.parseInt(limit);

        // 创建分页对象
        Page<Product> productPage = new Page<>(currentPage, pageSize);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();

        // 添加查询条件
        if (pname != null && !pname.isEmpty()) {
            queryWrapper.like("pname", pname);
        }
        if (type != null && !"-1".equals(type)) {
            queryWrapper.like("type", type);
        }
        if (firm != null && !firm.isEmpty()) {
            queryWrapper.like("firm", firm);
        }
        if (status != null && status != -1) {
            queryWrapper.eq("status", status);
        }
        // 添加排序条件，根据 updatetime 降序排序
        queryWrapper.orderByDesc("updatetime"); // 或者使用 orderByAsc("updatetime") 进行升序排序

        // 查询商品列表
        IPage<Product> productIPage = productMapper.selectPage(productPage, queryWrapper);
        List<Product> productList = productIPage.getRecords();

        // 查询总记录数
        long totalCount = productIPage.getTotal();

        // 如果查询结果为空
        if (productList == null || productList.isEmpty()) {
            jm.setCode(1);
            jm.setMsg("查询商品失败，商品列表为空");
            return jm;
        }
        for (Product product : productList){
            productPicUtil.getProductPic(product, product.getPid());
            List<String> urls = product.getUrls();
            if (urls!= null &&!urls.isEmpty()){
                product.setUrl(urls.get(0));
            }
        }

        jm.setCode(0);
        jm.setMsg("商品列表查询成功");
        jm.setCount(totalCount);
        jm.setData(productList);

        return jm; // 返回结果
    }


    // 查看商品图片
    @PostMapping("/getProductPic")
    public AdminJsonModel getProductPic(@RequestParam int pid) {
        AdminJsonModel jm = new AdminJsonModel();

        List<Map<String , Object>> picUrls = productPicMapper.selectUrlByPidMap(pid);
        if (picUrls.isEmpty()) {
            jm.setCode(1);
            jm.setMsg("图片查询失败");
            return jm;
        }

        jm.setCode(0);
        jm.setMsg("图片查询成功");
        jm.setData(picUrls);
        return jm;
    }

    // 新增商品
    @RequestMapping(value = "/addProduct" ,method = RequestMethod.POST)
    public AdminJsonModel addProduct(@ModelAttribute Product product) {
        AdminJsonModel jm = new AdminJsonModel();

        // 默认管理员ID
        int aid = 1;

        // 处理商品描述
//        product.setDescription(HtmlSanitizer.translation(product.getDescription()));

        // 设置初始价格
        product.setRealprice(product.getNormprice());

        // 添加商品
        product.setAid(aid);
        int isProductSaved = productMapper.insert(product);

        if (isProductSaved <= 0) {
            jm.setCode(1);
            jm.setMsg("商品添加失败");
            return jm;
        }

        // 获取刚插入商品的pid
        int pid = product.getPid();

        // 上传的图片地址
        List<String> picList = PicUrl.getaUrls();

        // 批量插入图片
        List<ProductPic> productPics = new ArrayList<>();
        for (int i = 0; i < picList.size(); i++) {
            if (!picList.get(i).equals("0")) {
                ProductPic pic = new ProductPic();
                pic.setPid(pid);
                pic.setUrl(picList.get(i));
                pic.setIs_primary(i == 0 ? 1 : 0);
                productPics.add(pic);
            }
        }
        try {
            productPicMapper.insert(productPics);// 批量插入图片
        } catch (Exception e) {
            e.printStackTrace();
            jm.setCode(1);
            jm.setMsg("商品图片添加失败");
            return jm;
        }
        jm.setCode(0);
        jm.setMsg("添加商品成功");
        redisTemplate.delete("newProductsCache::newProduct");
        return jm;
    }

    /**
     * 更新商品信息
     */
    @PostMapping("/updateProduct")
    public AdminJsonModel updateProduct( @RequestParam Integer pid,
                                         @RequestParam String pname,
                                         @RequestParam String type,
                                         @RequestParam String firm,
                                         @RequestParam String description,
                                         @RequestParam Integer status,
                                         @RequestParam Double normprice,
                                         @RequestParam Double realprice)  {
        AdminJsonModel jm = new AdminJsonModel();

        // 创建更新对象
        Product product = new Product();
        product.setPid(pid);
        product.setPname(pname);
        product.setType(type);
        product.setFirm(firm);
        product.setDescription(description);
        product.setStatus(status);
        product.setNormprice(normprice);
        product.setRealprice(realprice);
        // 执行更新操作
        int isUpdated = productMapper.updateById(product);
        if (isUpdated <= 0) {
            jm.setCode(1);
            jm.setMsg("商品更新失败");
        } else {
            jm.setCode(0);
            jm.setMsg("产品更新成功");
        }
        return jm;
    }


    // 商品图片上传
    @PostMapping("/updateProductPic")
    public AdminJsonModel updateProductPic(@RequestParam int pid) {
        AdminJsonModel jm = new AdminJsonModel();

        // 上传的图片地址
        List<String> picList = PicUrl.getaUrls();
        System.out.println("商品ID: " + pid);
        System.out.println("上传的图片列表: " + picList);

        // 开始事务
        try {
            // 删除旧的图片记录
            productPicMapper.delete(new QueryWrapper<ProductPic>().eq("pid", pid));

            // 批量插入新的图片记录
            List<ProductPic> productPics = new ArrayList<>();
            for (int i = 0; i < picList.size(); i++) {
                String url = picList.get(i);
                if (!"0".equals(url)) {
                    ProductPic productPic = new ProductPic();
                    productPic.setPid(pid);
                    productPic.setUrl(url);
                    productPic.setIs_primary(i == 0 ? 1 : 0); // 第一个设置为主图
                    productPics.add(productPic);
                }
            }
            if (!productPics.isEmpty()) {
                productPicMapper.insert(productPics); // 批量插入
            }
            jm.setCode(0);
            jm.setMsg("商品图片更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            jm.setCode(1);
            jm.setMsg("商品图片更新失败: " + e.getMessage());
        }
        return jm;
    }

    // 商品上下架
    @PostMapping("/putAndDown")
    public AdminJsonModel putAndDown(@RequestParam String status, @RequestParam String idsStr) {
        AdminJsonModel jm = new AdminJsonModel();

        // 将id字符串转换为List
        List<Integer> idList = extractNumbers(idsStr);

        // 创建更新条件
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", status.equals("1") ? 1 : 0)
                .in("pid", idList);

        // 执行更新操作
        int result = productMapper.update(null, updateWrapper);

        if (result <= 0) {
            jm.setCode(1);
            jm.setMsg("商品上下架失败");
            return jm;
        }

        jm.setCode(0);
        jm.setMsg("商品上下架成功");
        return jm;
    }

    /**
     * 获取字符串中的所有id
     * begin:1,2,3,4,5,
     *
     * @param input
     * @return 数字集合
     */
    public static List<Integer> extractNumbers(String input) {
        List<Integer> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }

        return numbers;
    }


}
