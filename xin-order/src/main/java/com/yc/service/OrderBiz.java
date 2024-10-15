package com.yc.service;


import com.yc.web.model.Orders;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface OrderBiz {

    //获取用户订单
    public List<Orders> getOrder(HttpServletRequest request);

    //修改订单
    public int modifyOrderContent(Orders orders);

    //取消订单
    public  int confirmCancellation( HttpServletRequest request ,Orders orders);

    //申请退款或退货
    public int refundProduct(Orders orders);

    //确认收货
    public int confirmReceipt(Orders orders);

    // 未支付的订单重新支付 支付订单
    public int payOrder(Orders orders);

    //检查订单是否支付
    public int checkPay(String outTradeNo);

}
