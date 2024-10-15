package com.yc.web.controllers;

import com.google.gson.Gson;
import com.yc.api.ProductApi;
import com.yc.bean.model.JsonModel;
import com.yc.web.model.Product;
import com.yc.web.model.ProductDetail;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private ProductApi productApi;

    @Autowired
    private RedisTemplate redisTemplate;

    // 加入购物车
    @Transactional // 开启事务
    @RequestMapping(value = "/addCart",method = {RequestMethod.POST , RequestMethod.PUT})
    public JsonModel addCart(@RequestParam Integer pid, @RequestParam Integer count, HttpServletRequest request){
        //拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();
        String cartKey = "cart:user_cart_" + uid; // Redis 键名
        // 检查用户是否已加入该商品
        Boolean exists = redisTemplate.opsForHash().hasKey(cartKey, pid.toString());
        if (exists != null && exists) {
            // 获取 Redis 中的商品数量
            String countStr = (String) redisTemplate.opsForHash().get(cartKey, pid.toString());
            int redisCount = countStr != null ? Integer.parseInt(countStr) : 0; // Redis 中的商品数量
            count += redisCount; // 更新数量

            if (count <= 0) {
                // 移除商品
                redisTemplate.opsForHash().delete(cartKey, pid.toString());
            } else {
                // 存储购物车数据
                redisTemplate.opsForHash().put(cartKey, pid.toString(), String.valueOf(count));
            }
        } else {
            // 新增商品
            redisTemplate.opsForHash().put(cartKey, pid.toString(), String.valueOf(count));
        }
        JsonModel jm = new JsonModel();
        jm.setCode(1);
        jm.setObj("加入购物车成功");
        return jm; // 假设你有一个静态方法用于返回成功响应
    }

    // 查看购物车
    @RequestMapping(value = "/getCart", method =  RequestMethod.POST)
    public JsonModel getCart(HttpServletRequest request) {
        // 拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();
        String cartKey = "cart:user_cart_" + uid; // Redis 键名

        // 获取购物车中的商品数量
        Map<Object, Object> cartItems = redisTemplate.opsForHash().entries(cartKey);
        List<ProductDetail> productDetails = new ArrayList<>();

        for (Map.Entry<Object, Object> entry : cartItems.entrySet()) {
            String pid = entry.getKey().toString(); // 商品ID
            int count = Integer.parseInt(entry.getValue().toString()); // 商品数量

            // 根据商品ID获取商品详情
            JsonModel productDetail = productApi.getProductDetails(Integer.parseInt(pid));
            Gson gson = new Gson();
            Product product = gson.fromJson(gson.toJson(productDetail.getObj()), Product.class);
            // 构建ProductDetail对象 并添加到列表中
            productDetails.add(new ProductDetail(product, count));
        }
        JsonModel jm = new JsonModel();
        jm.setCode(1);
        jm.setObj(productDetails);
        return jm; // 返回购物车中商品的列表
    }

}
