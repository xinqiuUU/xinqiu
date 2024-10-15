package com.yc.web.controllers;

import com.yc.bean.model.JsonModel;
import com.yc.service.OrderBiz;
import com.yc.web.model.Orders;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {


    private OrderBiz orderService;
    @Autowired
    public void setOrderService(OrderBiz orderService) {
        this.orderService = orderService;
    }

    // 获取用户订单
    @GetMapping("/getOrder")
    public JsonModel getOrder(HttpServletRequest request) {
        JsonModel jsonModel = new JsonModel();
        List<Orders> orders = orderService.getOrder(request);
        jsonModel.setCode(1);
        jsonModel.setObj(orders);
        return jsonModel;
    }
    // 根据订单号 检查订单是否支付  0 待支付， 1 已付款， 2 已发货，-1,被取消订单  -3 退款  -4退款审核中，
    @GetMapping("/checkPay")
    public JsonModel checkPay(@RequestParam("out_trade_no") String outTradeNo) {
        JsonModel jm = new JsonModel();
        int result = orderService.checkPay(outTradeNo );
        if (result == 1) {
            jm.setCode(1);
            jm.setObj("已支付");
        } else {
            jm.setCode(0);
            jm.setError("未支付");
        }
        return jm;
    }

    // 修改订单
    @PutMapping("/modifyOrderContent")
    public JsonModel modifyOrderContent(@RequestBody Orders orders) {
        JsonModel jm = new JsonModel();
        int result = orderService.modifyOrderContent(orders);
        if (result == 1) {
            jm.setCode(1);
            jm.setObj("修改成功");
        } else {
            jm.setCode(0);
            jm.setError("修改失败");
        }
        return jm;
    }

    // 取消订单
    @PutMapping("/confirmCancellation")
    public JsonModel confirmCancellation(HttpServletRequest request, @RequestBody Orders orders) {
        JsonModel jm = new JsonModel();
        int result = orderService.confirmCancellation(request, orders);
        if (result == 1) {
            jm.setCode(1);
            jm.setObj("取消成功");
        } else {
            jm.setCode(0);
            jm.setError("取消失败");
        }
        return jm;
    }

    // 申请退款或退货
    @PutMapping("/refundProduct")
    public JsonModel refundProduct(@RequestBody Orders orders) {
        JsonModel jm = new JsonModel();
        int result = orderService.refundProduct(orders);
        if (result == 1) {
            jm.setCode(1);
            jm.setObj("申请成功");
        } else {
            jm.setCode(0);
            jm.setError("申请失败");
        }
        return jm;
    }

    // 确认收货
    @PutMapping("/confirmReceipt")
    public JsonModel confirmReceipt(@RequestBody Orders orders) {
        JsonModel jm = new JsonModel();
        int result = orderService.confirmReceipt(orders);
        if (result == 1) {
            jm.setCode(1);
            jm.setObj("确认成功");
        } else {
            jm.setCode(0);
            jm.setError("确认失败");
        }
        return jm;
    }

    // 支付订单
    @PutMapping("/payOrder")
    public JsonModel payOrder(@RequestBody Orders orders) {
        JsonModel jm = new JsonModel();
        int result = orderService.payOrder(orders);
        if (result == 1) {
            jm.setCode(1);
            jm.setObj("支付成功");
        } else {
            jm.setCode(0);
            jm.setError("支付失败");
        }
        return jm;
    }

}
