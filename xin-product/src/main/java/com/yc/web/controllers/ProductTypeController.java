package com.yc.web.controllers;

import com.yc.bean.AdminJsonModel;
import com.yc.bean.model.JsonModel;
import com.yc.dao.ProductMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//全部  Rest API 风格
@RestController
@RequestMapping("/product/admin")   //请求路径的前缀  http://localhost:8080/product/ + xxx
@Slf4j
@Tag(name = "商品API" , description = "商品管理相关接口")
@RefreshScope  // 动态刷新配置文件
public class ProductTypeController {

    @Autowired
    private ProductMapper productMapper; //商品服务

    /**
     * 根据商品类型统计购买数量
     */
    @RequestMapping(value = "/getProdcutTotalCountByType")
    public AdminJsonModel getProdcutTotalCountByType(){
        List<Map<String,Object>> list = productMapper.getProdcutTotalCountByType();
        // 处理查询结果
        for (Map<String, Object> map : list) {
            Object value = map.remove("name"); // 更改为name
            if (value != null) {
                map.put("name", value);
            }
        }
        AdminJsonModel jm = new AdminJsonModel();
        if (list.size()<=0||list==null){
            jm.setCode(1);
            jm.setMsg("数据查询失败");
            return jm;
        }
        jm.setCode(0);
        jm.setData(list);
        return jm;
    }

}
