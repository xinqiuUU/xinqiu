package com.yc.web.controllers;

import com.google.gson.Gson;
import com.yc.api.ProductApi;
import com.yc.bean.Collect;
import com.yc.bean.model.JsonModel;
import com.yc.web.model.Product;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//全部  Rest API 风格
@RestController
@RequestMapping("/cart")   //请求路径的前缀  http://localhost:8080/product/ + xxx
@Slf4j
@Tag(name = "用户收藏商品API" , description = "用户收藏商品管理相关接口")
@RefreshScope  // 动态刷新配置文件
public class CollectController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ProductApi productApi;
    // 获取 收藏商品列表
    @RequestMapping(value = "/getCollectShopping",method = RequestMethod.GET)
    public JsonModel getCollectShopping( HttpServletRequest request){
        JsonModel jm = new JsonModel();

        // 拦截器已经将用户信息放入request中取出
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();

        // 从 Redis 获取用户的收藏商品 IDs
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Object>> sortedSetMembers = zSetOps.rangeWithScores("collect:user_collect_" + uid, 0, -1);

        // 构建收藏商品列表
        List<Collect> collectList = sortedSetMembers.stream().map(member -> {
            Integer memberValue = Integer.valueOf(member.getValue().toString()); // 用户收藏的商品 pid
            Long score = member.getScore().longValue(); // 收藏的时间戳

            Collect collect = new Collect();
            collect.setPid(memberValue);
            collect.setTimestamp(score);
            // 根据商品ID获取商品详情
            JsonModel productDetail = productApi.getProductDetails(memberValue);
            Gson gson = new Gson();
            Product product = gson.fromJson(gson.toJson(productDetail.getObj()), Product.class);
            collect.setPname(product.getPname());
            collect.setUrls(product.getUrls());
            return collect;
        }).collect(Collectors.toList());

        jm.setCode(1);
        jm.setObj(collectList);
        return jm; // 返回 JsonModel
    }

    // 取消收藏商品
    @RequestMapping(value = "/cancelCollect", method = RequestMethod.POST)
    public JsonModel cancelCollect(@RequestParam String pid, HttpServletRequest request) {
        JsonModel jm = new JsonModel();

        // 拦截器已经将用户信息放入request中取出
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();
        // 从 Redis 获取用户收藏商品的有序集合
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        Double score = zSetOps.score("collect:user_collect_" + uid, pid);

        // 如果该商品不存在，则添加到收藏夹
        if (score == null) {
            long currentTimestamp = System.currentTimeMillis() / 1000;
            // 添加新成员到有序集合
            zSetOps.add("collect:user_collect_" + uid, pid, currentTimestamp);
            jm.setCode(1);
            jm.setObj("商品已成功添加到收藏夹");
        } else {
            // 取消收藏
            zSetOps.remove("collect:user_collect_" + uid, pid);
            jm.setCode(1);
            jm.setObj("商品已成功取消收藏");
        }

        return jm; // 返回 JsonModel
    }


}
