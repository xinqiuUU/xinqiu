package com.yc.service;

import com.yc.dao.ProductMapper;
import com.yc.utils.ProductPicUtil;
import com.yc.web.model.Comment;
import com.yc.web.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
   精品推荐的服务类
 */
@Service
public class PremiumService {
    @Autowired
    private MongoTemplate mongoTemplate;// 注入 MongoTemplate 实例
    @Autowired
    private RedisTemplate redisTemplate;// 注入 RedisTemplate 实例
    @Autowired
    private ProductMapper productMapper;//商品dao
    @Autowired
    private ProductPicUtil productPicUtil;//图片工具类

    // 每月最后一天更新缓存
    @Scheduled(cron = "0 0 0 L * ?") // 每月最后一天的 00:00
    @CacheEvict(value = "premiums", allEntries = true) // 清除缓存
    public void updateCachedComments() {
        List<Product> products = getCommentsFromLastMonth();
        // 存入缓存并设置过期时间为30天
        redisTemplate.opsForValue().set("premiums", products);
        redisTemplate.expire("premiums", 31, TimeUnit.DAYS); // 设置缓存过期时间为 31 天
    }

    // 从数据库获取上个月的精品推荐
    private List<Product> getCommentsFromLastMonth() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        // 获取上个月的开始和结束日期
        String startMonth = sdf.format(new Date(now.getTime() - (60L * 24 * 60 * 60 * 1000)));
        String endMonth = sdf.format(new Date(now.getTime()));
        String startDateString = startMonth + "-01 00:00:00";
        String endDateString = endMonth + "-30 23:59:59";

        // 构建查询条件
        MatchOperation match = Aggregation.match(Criteria.where("createTime").gte(startDateString).lte(endDateString));

        // 根据 pid 分组并计算平均评分
        GroupOperation group = Aggregation.group("pid")
                .avg("rating").as("averageRating")
                .first("pid").as("pid");

        // 排序
        Aggregation aggregation = Aggregation.newAggregation(match, group, Aggregation.sort(Sort.by(Sort.Order.desc("averageRating"))));

        // 执行聚合查询
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, Comment.class, Map.class);
        List<Product> products = new ArrayList<>();
        for (Map map : results.getMappedResults()){
            String pid = (String) map.get("pid");
            // 获取商品详情并判断商品是否下架
            Product product = productMapper.getProductDetails(Integer.valueOf(pid));
            if (product.getStatus() == 1) {  // 假设 status = 1 表示商品未下架
                productPicUtil.getProductPic(product, Integer.valueOf(pid));
                products.add(product);
            }
        }
        // 获取前四个商品
        return products;
    }

    // 从缓存获取上个月的精品推荐
    public List<Product> getCachedPremiums() {
        // 从缓存中获取精品推荐
        List<Product> products = (List<Product>) redisTemplate.opsForValue().get("premiums");
        // 如果缓存中没有数据，调用更新缓存的方法
        if (products == null || products.isEmpty()) {
            // 没有缓存或缓存为空，调用更新方法
            updateCachedComments();
            // 再次从缓存中获取数据
            products = (List<Product>) redisTemplate.opsForValue().get("premiums");
        }
        return products;
    }

}
