package com.yc.api;

import com.yc.bean.model.JsonModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient( name = "xin-cart")  //name 服务名称 所以访问 地址最终会为: http://res-food
public interface CartApi {

    // 查看购物车
    @RequestMapping(value = "/cart/getCart", method = RequestMethod.POST)
    JsonModel getCart(@RequestHeader("token") String token) ;
}
