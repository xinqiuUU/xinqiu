package com.yc.web.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.AdminJsonModel;
import com.yc.dao.OrderMapper;
import com.yc.service.OrderBiz;
import com.yc.utils.TimeStampUtil;
import com.yc.web.model.Orders;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/order/admin")
public class AdminOrderController {

    @Autowired
    private OrderMapper orderMapper;

    // 订单删除 批量删除
    @PostMapping("/deleteOrders")
    public AdminJsonModel deleteOrders(@RequestParam String idsStr) {
        AdminJsonModel jm = new AdminJsonModel();

        // 将 ID 字符串转换为 List
        List<Integer> orderIds = extractNumbers(idsStr);
        System.out.println(orderIds);

        // 调用 Service 批量更新订单状态
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("order_id", orderIds);
        updateWrapper.set("status", -3); // 设置状态为 -3 已退款
        boolean success = orderMapper.update(null, updateWrapper) > 0;

        if (!success) {
            jm.setCode(1);
            jm.setMsg("批量删除订单失败");
            return jm;
        }

        jm.setCode(0);
        jm.setMsg("批量删除订单成功");
        return jm;
    }

    // 退款该订单
    @PostMapping("/deleteOrder")
    public AdminJsonModel deleteOrder(
            @RequestParam int order_id,
            @RequestParam int status) {

        AdminJsonModel jm = new AdminJsonModel();

        try {
            Orders order = new Orders();
            order.setOrder_id(order_id);
            order.setStatus(status);
            order.setRefund_reasons("");
            int refunded = orderMapper.updateById(order);
            if (refunded <= 0) {
                jm.setCode(1);
                jm.setMsg("订单退款失败");
                return jm;
            }
        } catch (Exception e) {
            e.printStackTrace();
            jm.setCode(1);
            jm.setMsg("订单退款异常: " + e.getMessage());
            return jm;
        }

        jm.setCode(0);
        jm.setMsg("订单退款成功");
        return jm;
    }

    // 订单发货 批量发货
    @PostMapping("/deliverGoods")
    public AdminJsonModel deliverGoods(@RequestParam String idsStr) {
        AdminJsonModel jm = new AdminJsonModel();

        // 将 id 字符串转换为 List
        List<Integer> orderIds = extractNumbers(idsStr);
        System.out.println(orderIds);

        // 批量更新订单状态
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("order_id", orderIds);
        updateWrapper.set("status", 2); // 设置状态为 2 发货
        boolean updated = orderMapper.update(null, updateWrapper) > 0;

        if (!updated) {
            jm.setCode(1);
            jm.setMsg("批量发货失败");
            return jm;
        }
        jm.setCode(0);
        jm.setMsg("批量发货成功");
        return jm;
    }


    // 订单发货
    @PostMapping("/updateOrder")
    public AdminJsonModel updateOrder(
            @RequestParam int order_id,
            @RequestParam String firm) {

        AdminJsonModel jm = new AdminJsonModel();

        // 创建一个 Orders 实体对象并更新状态和快递公司
        Orders order = new Orders();
        order.setOrder_id(order_id); // 设置订单 ID
        order.setStatus(2); // 更新状态为 2 发货
        order.setDelivery_company(firm); // 更新快递公司
        int updated = orderMapper.updateById(order);
        if (updated <= 0) {
            jm.setCode(1);
            jm.setMsg("订单信息更新失败");
            return jm;
        }
        jm.setCode(0);
        jm.setMsg("订单信息更新成功");
        return jm;
    }

    // 订单查询 status  0 待支付， 1 已付款， 2 已发货，-1,被取消订单  -3 退款  -4退款审核中，
    @GetMapping("/getAllOrders")
    public AdminJsonModel getAllOrders(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam int status,
            @RequestParam(required = false) String order_id,
            @RequestParam(required = false) String tel,
            @RequestParam(required = false) Integer searchStatus) {

        AdminJsonModel jm = new AdminJsonModel();

        // 构建查询条件
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();

        if (order_id != null && !order_id.isEmpty()) {
            queryWrapper.eq("order_id", Integer.parseInt(order_id));
        }
        if (tel != null && !tel.isEmpty()) {
            queryWrapper.like("phone", tel);
        }

        if (searchStatus != null && searchStatus != -100) {
            queryWrapper.eq("status", searchStatus);
        } else {
            if (status == -1) {
                queryWrapper.le("status", -1);
            } else if (status == 1) {
                queryWrapper.ge("status", 1);
            } else if (status == 0) {
                queryWrapper.eq("status", 0);
            }
        }
        queryWrapper.orderByDesc("ordertime");// 按订单时间倒序排序
        // 分页查询
        Page<Orders> orderPage = new Page<>(page, limit);
        orderMapper.selectPage(orderPage, queryWrapper);
        // 处理订单数据
        for (Orders order : orderPage.getRecords()) {
            String ordertime = order.getOrdertime();
            long timeStage = TimeStampUtil.getTimeInterval(ordertime);
            order.setTimeStage(timeStage);
        }

        jm.setCode(0);
        jm.setMsg("订单查询成功");
        jm.setData(orderPage.getRecords());
        jm.setCount(orderPage.getTotal());

        return jm;
    }

    /**
     * 获取字符串中的所有id
     * begin:1,2,3,4,5,
     * @param input
     * @return 数字集合
     */
    public static ArrayList<Integer> extractNumbers(String input) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }

        return numbers;
    }

}
