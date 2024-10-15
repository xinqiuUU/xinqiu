package com.yc.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yc.bean.ProductDetail;
import com.yc.dao.OrderMapper;
import com.yc.web.model.Orders;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional  //开启事务
@Slf4j
public class OrderBizImpl implements OrderBiz  {

    private OrderMapper orderMapper;
    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }
    //获取用户订单
    @Override
    public List<Orders> getOrder(HttpServletRequest request) {
        // 拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid)
                .ne("status", -1) // 不等于 -1
                .ne("status", -2) // 不等于 -2
                .orderByDesc("ordertime");
        List<Orders> orders = orderMapper.selectList(queryWrapper);
        for (Orders order : orders) {
            List<ProductDetail> productDetails =  orderMapper.selectProductDetailsByOrderId(order.getOrder_id());
            order.setProductDetail(productDetails);
        }
        return orders;
    }

    // 修改订单  update orders set addressee = ?,emailee = ?, phone = ?, address = ? where order_id = ?
    @Override
    public int modifyOrderContent(Orders orders) {
        UpdateWrapper<Orders> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_id", orders.getOrder_id())
                .set("addressee", orders.getAddressee())
                .set("emailee", orders.getEmailee())
                .set("phone", orders.getPhone())
                .set("address", orders.getAddress());
        // 使用 update 方法进行更新
        return orderMapper.update( null ,wrapper);
    }

    // 取消订单 update orders set refund_reasons = ?,status = -1 where order_id = ?
    @Override
    public int confirmCancellation( HttpServletRequest request , Orders orders) {
        UpdateWrapper<Orders> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_id", orders.getOrder_id())
             .set("refund_reasons", orders.getRefund_reasons())
             .set("status", -1);
        // 使用 update 方法进行更新
        return orderMapper.update( null,wrapper);
    }

    // 申请退款或退货 update orders set refund_reasons = ?,status = -4 where order_id = ?
    @Override
    public int refundProduct(Orders orders) {
        UpdateWrapper<Orders> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_id", orders.getOrder_id())
          .set("refund_reasons", orders.getRefund_reasons())
          .set("status", -3);
        // 使用 update 方法进行更新
        return orderMapper.update( null,wrapper);
    }

    // 确认收货 update orders set status = 3 where order_id = ?
    @Override
    public int confirmReceipt(Orders orders) {
        UpdateWrapper<Orders> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_id", orders.getOrder_id())
                .set("status", 3);
        return orderMapper.update(null, wrapper);
    }

    // 未支付的订单重新支付 支付订单 update orders set status = ? where order_id = ?
    @Override
    public int payOrder(Orders orders) {
        UpdateWrapper<Orders> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_id", orders.getOrder_id())
             .set("status", orders.getStatus());
        return orderMapper.update(null, wrapper);
    }

    //检查订单是否支付 0 待支付， 1 已付款， 2 已发货，-1,被取消订单  -3 退款  -4退款审核中，
    @Override
    public int checkPay(String outTradeNo) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", outTradeNo);
        Orders orders = orderMapper.selectOne(queryWrapper);
        if (orders.getStatus() == 1) {
            return 1;
        }
        return 0;
    }
}

