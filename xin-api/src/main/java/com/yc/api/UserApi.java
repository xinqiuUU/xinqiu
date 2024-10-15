package com.yc.api;

import com.yc.bean.ResponseResult;
import com.yc.bean.model.JsonModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient( name = "xin-security")  //name 服务名称 所以访问 地址最终会为: http://res-food
public interface UserApi {

    // 根据用户uid查看详情
    @GetMapping(value = "/security/getUser")  // 需要确保请求路径前缀匹配
    public ResponseResult getUser(@RequestParam("uid") Integer uid);

}
