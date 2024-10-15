package com.yc.api;

import com.yc.bean.model.JsonModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient( name = "xin-product")  //name 服务名称 所以访问 地址最终会为: http://res-food
public interface ProductApi {

    // 根据商品pid查看详情
    @GetMapping(value = "/product/getProductDetails")  // 需要确保请求路径前缀匹配
    public JsonModel getProductDetails(@RequestParam("pid") Integer pid);

}
