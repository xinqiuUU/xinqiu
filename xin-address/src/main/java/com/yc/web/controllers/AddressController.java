package com.yc.web.controllers;



import com.yc.bean.Address;
import com.yc.bean.model.JsonModel;
import com.yc.service.AddressBiz;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


//全部  Rest API 风格
@RestController
@RequestMapping("/address")   //请求路径的前缀  http://localhost:8080/product/ + xxx
@Slf4j
@Tag(name = "地址API" , description = "用户地址管理相关接口")
@RefreshScope  // 动态刷新配置文件
public class AddressController {

    @Autowired
    private AddressBiz addressBiz;

    //  获取默认地址
    @RequestMapping(value = "/getmodifyDefaultAddress",method = {RequestMethod.GET ,RequestMethod.POST})
    public JsonModel getmodifyDefaultAddress( HttpServletRequest request) {
        // 调用服务层方法获取默认地址
        JsonModel jm  = addressBiz.getmodifyDefaultAddress(request);

        return jm; // 返回JsonModel对象
    }


    //修改默认地址
    @PostMapping(value = "/modifyDefaultAddress")
    public JsonModel modifyDefaultAddress(@RequestBody Map<String, String> params, HttpServletRequest request) {

        String addr_id = params.get("addr_id");
        // 调用服务层方法修改默认地址
        JsonModel jm  = addressBiz.modifyDefaultAddress(addr_id, request);

        return jm; // 返回JsonModel对象
    }

    // 逻辑删除地址
   @RequestMapping(value = "/deleteAddress",method = RequestMethod.POST)
    public JsonModel deleteAddress( @RequestParam String addr_id,  HttpServletRequest request) {
        // 调用服务层方法删除地址
        JsonModel jm  = addressBiz.deleteAddress(addr_id , request);

        return jm; // 返回JsonModel对象
    }

    //  获取所有地址
    @RequestMapping(value = "/getSaveAddress" ,method = RequestMethod.GET)
    public JsonModel getSaveAddress(HttpServletRequest request) {
        // 调用服务层方法保存地址
        JsonModel jm  = addressBiz.getSaveAddress(request);

        return jm; // 返回JsonModel对象
    }

    // 保存地址
    @PostMapping("/saveAddress")
    public JsonModel saveAddress(@RequestBody Address address, HttpServletRequest request) {
        // 调用服务层方法保存地址
        JsonModel jm  = addressBiz.saveAddress(address, request);

        return jm; // 返回JsonModel对象
    }
}
