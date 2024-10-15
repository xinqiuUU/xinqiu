package com.yc.web.controllers;


import com.yc.bean.model.JsonModel;
import com.yc.service.PremiumService;
import com.yc.service.ProductBiz;
import com.yc.web.model.MyPageBean;
import com.yc.web.model.Product;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//全部  Rest API 风格
@RestController
@RequestMapping("/product")   //请求路径的前缀  http://localhost:8080/product/ + xxx
@Slf4j
@Tag(name = "商品API" , description = "商品管理相关接口")
@RefreshScope  // 动态刷新配置文件
public class ProductController {

    @Autowired
    private ProductBiz productBiz; //商品服务

    // 查看最新商品
    @GetMapping(value = "/newProduct")  //GET  http://localhost:8080/resfood/findById/2
    public JsonModel selectNewProducts() {
        Product newProduct =  productBiz.selectNewProducts();
        JsonModel jm = new JsonModel();
        jm.setCode(1);
        jm.setObj(newProduct);
        return jm;
    }
    // 根据商品pid查看详情
    @GetMapping(value = "/getProductDetails")
    public JsonModel getProductDetails(@RequestParam("pid") Integer pid){
        Product product = productBiz.getProductDetails(pid);
        JsonModel jm = new JsonModel();
        jm.setCode(1);
        jm.setObj(product);
        return jm;
    }
    // 根据商品type
    @GetMapping(value = "/getRelatedProducts")
    public JsonModel getRelatedProducts(@RequestParam("type") String type){
        List<Product> products = productBiz.getRelatedProducts(type);
        JsonModel jm = new JsonModel();
        jm.setCode(1);
        jm.setObj(products);
        return jm;
    }
    // 查看热门商品
    @GetMapping(value = "/getHotProducts")
    public JsonModel getHotProducts(){
        List<Product> products = productBiz.getHotProducts();
        JsonModel jm = new JsonModel();
        jm.setCode(1);
        jm.setObj(products);
        return jm;
    }
    // 查看所有商品
    @PostMapping(value = "/getAllProducts")
    public JsonModel getAllProducts(@RequestBody @Parameter(description = "分页信息") MyPageBean myPageBean) {
        MyPageBean<Product> page = productBiz.getAllProducts(myPageBean.getProduct(), myPageBean.getPageno(), myPageBean.getSize(), myPageBean.getSortby(), myPageBean.getSort());
        JsonModel jm = new JsonModel();
        jm.setCode(1);
        jm.setObj(page);
        return jm;
    }

    @Autowired
    private PremiumService premiumService;
    //获取精品推荐getPremiums
    @GetMapping(value = "/getPremiums")
    public JsonModel getPremiums() {
        List<Product> products = premiumService.getCachedPremiums().subList(0,4);
        JsonModel jm = new JsonModel();
        jm.setCode(1);
        jm.setObj(products);
        return jm;
    }

}
