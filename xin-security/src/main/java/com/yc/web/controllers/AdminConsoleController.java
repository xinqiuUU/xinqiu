package com.yc.web.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.AdminJsonModel;
import com.yc.bean.Orders;
import com.yc.dao.AdminMapper;
import com.yc.dao.OrdersMapper;
import com.yc.dao.ProductMapper;
import com.yc.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/security/admin")
public class AdminConsoleController {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private UserMapper userMapper;

    //获取（导航栏）总信息
    @PostMapping("/getTotalInfo")
    public AdminJsonModel getTotalInfo() {
        AdminJsonModel jm = new AdminJsonModel();
        //返回的结果
        List list = new ArrayList();
        // 构建查询条件
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("status", 2);  // status >= 2
        long x1 = ordersMapper.selectCount(queryWrapper);// 订单总数
        long x2 = productMapper.selectCount(null);// 商品总数
        long x3 = adminMapper.selectCount(null);// 管理员总数
        long x4 = userMapper.selectCount(null);// 用户总数
        list.add(x1);
        list.add(x2);
        list.add(x3);
        list.add(x4);
        jm.setCode(0);// 成功
        jm.setData(list);
        jm.setMsg("查询（导航栏首项）成功");
        return jm;
    }
}
