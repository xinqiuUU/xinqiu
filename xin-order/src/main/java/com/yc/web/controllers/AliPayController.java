package com.yc.web.controllers;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.easysdk.factory.Factory;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yc.api.CartApi;
import com.yc.api.CouponApi;
import com.yc.bean.AliPay;
import com.yc.bean.UserCoupon;
import com.yc.bean.model.JsonModel;
import com.yc.configuration.AliPayConfig;
import com.yc.dao.OrderDetailMapper;
import com.yc.dao.OrderMapper;
import com.yc.web.model.Orders;
import com.yc.web.model.ProductDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

// 完整版 sdk 解决了  subject中文乱码问题
@RestController
@RequestMapping("/order/alipay")
public class AliPayController {

    private OrderMapper ordersMapper;
    @Autowired
    public void setOrdersMapper(OrderMapper ordersMapper) {
        this.ordersMapper = ordersMapper;
    }
    private AliPayConfig aliPayConfig;
    @Autowired
    public void setAliPayConfig(AliPayConfig aliPayConfig) {
        this.aliPayConfig = aliPayConfig;
    }
    @Autowired
    private CartApi cartApi;

    private RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private OrderDetailMapper orderDetailMapper;
    @Autowired
    public void setOrderDetailMapper(OrderDetailMapper orderDetailMapper) {
        this.orderDetailMapper = orderDetailMapper;
    }
    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";

    @Autowired
    private CouponApi couponApi;// 优惠券接口

    // 支付 下单订
    @Transactional // 开启事务
    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay( Integer id ,Orders orders , HttpServletResponse res ,HttpServletRequest req) throws Exception {
        // 拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) req.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();
        String token = req.getHeader("token");
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);
        // 2. 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类

//        String notifyUrl = aliPayConfig.getNotifyUrl();
//        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8.toString());
//        request.setNotifyUrl(notifyUrl + "?token=" + encodedToken); // 异步回调地址
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());// 异步回调地址
        request.setReturnUrl(AliPayConfig.return_url); // 支付成功的回调地址

        // 从redis中获取购物车数据
        JsonModel jm = cartApi.getCart(token);

        List<LinkedHashMap<String, Object>> linkedHashMaps = (List<LinkedHashMap<String, Object>>) jm.getObj();

        Gson gson = new Gson();
        String json = gson.toJson(linkedHashMaps); // 转换为 JSON 字符串

        Type productListType = new TypeToken<List<ProductDetail>>() {}.getType();
        List<ProductDetail> productDetails = gson.fromJson(json, productListType); // 从 JSON 字符串转换为 List<ProductDetail>

        double totalAmount = 0;// 订单总金额
        for (ProductDetail productDetail : productDetails){
            totalAmount += productDetail.getSmallCount();
        }
        //判断是否使用优惠劵
        if ("".equals(id) || id==null){ // 没有使用优惠劵
            // 使用 BigDecimal 保留两位小数
            BigDecimal bd = new BigDecimal(totalAmount+orders.getPostage());
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            totalAmount = bd.doubleValue();
        }else {
            //有优惠劵
            totalAmount = Double.parseDouble(couponApi.exchangeCoupon(token,Integer.valueOf(uid),id,totalAmount+orders.getPostage())); // 修改优惠券状态
        }
        orders.setTotalprice(totalAmount); // 订单总金额
        orders.setUid(Integer.valueOf(uid));// 订单用户id
        orders.setId(id);// 用户优惠劵id
        // 订单表
        ordersMapper.insert(orders);
        // 订单详情表
        for (ProductDetail productDetail : productDetails){
            orderDetailMapper.insertOrderDetail(orders.getOrder_id(), productDetail.getProduct().getPid(), productDetail.getCount(), productDetail.getSmallCount());
        }
        // 清空redis中的购物车数据
        redisTemplate.delete("cart:user_cart_"+uid);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orders.getOrder_id());  // 我们自己生成的订单编号
        bizContent.put("total_amount", totalAmount); // 订单的总金额
        bizContent.put("subject", "科技之城支付平台");   // 支付的名称
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置

        request.setBizContent(bizContent.toString());

        // 调用接口更新订单状态为已支付
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_id", orders.getOrder_id());// 订单编号
        updateWrapper.set("status", 1);
        ordersMapper.update(updateWrapper);

        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        res.setContentType("text/html;charset=" + CHARSET);
        res.getWriter().write(form);// 直接将完整的表单html输出到页面
        res.getWriter().flush();
        res.getWriter().close();
    }

    // 支付成功回调，异步通知
    @Transactional // 开启事务
    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }
            String tradeNo = params.get("out_trade_no"); // 我的订单编号
            String gmtPayment = params.get("gmt_payment"); // 支付时间
            String alipayTradeNo = params.get("trade_no"); // 支付宝回调的订单流水号
            Map<String, Object> map = new HashMap<>();
            map.put("out_trade_no", tradeNo);// 我的订单编号
            map.put("buyer_pay_amount", params.get("buyer_pay_amount"));//买家付款金额
            map.put("trade_no", alipayTradeNo); // 支付宝回调的订单流水号
            map.put("gmt_payment", gmtPayment); // 支付时间
            map.put("buyer_id", params.get("buyer_id")); // 买家id
            // 调用接口更新订单状态为已支付
            UpdateWrapper updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("order_id", tradeNo);// 订单编号
            updateWrapper.set("status", 1);
            int result = ordersMapper.update(updateWrapper);
            if (result > 0) {
                // 更新订单状态为已支付成功
                // 将支付信息存入 Redis    key: order:订单号  value: map
                redisTemplate.opsForValue().set("order:" + tradeNo, map);
                System.out.println("更新订单状态为已支付成功");
            } else {
                // 更新订单状态为已支付失败
                System.out.println("更新订单状态为已支付失败");
            }
            return "success";
        }
        return "error";
    }

    // 再次支付
    @Transactional // 开启事务
    @GetMapping("/againPay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void againPay(Orders orders , HttpServletResponse res ,HttpServletRequest req) throws Exception {

        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);
        // 2. 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类

//        String notifyUrl = aliPayConfig.getNotifyUrl();
//        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8.toString());
//        request.setNotifyUrl(notifyUrl + "?token=" + encodedToken); // 异步回调地址
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());// 异步回调地址
        request.setReturnUrl("http://localhost/order.html"); // 支付成功的回调地址

        Orders o =  ordersMapper.selectById(orders.getOrder_id());
        // 清空redis中的购物车数据
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orders.getOrder_id());  // 我们自己生成的订单编号
        bizContent.put("total_amount", o.getTotalprice()); // 订单的总金额
        bizContent.put("subject", "科技之城支付平台");   // 支付的名称
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置

        request.setBizContent(bizContent.toString());

        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        res.setContentType("text/html;charset=" + CHARSET);
        res.getWriter().write(form);// 直接将完整的表单html输出到页面
        res.getWriter().flush();
        res.getWriter().close();
    }

    // 退款
    @GetMapping("/refund")
    public JsonModel refund( String order_id ,String refundReasons ) throws AlipayApiException {
        // 7天无理由退款
        JsonModel jm = new JsonModel();
        String now = DateUtil.now();
        Orders orders = ordersMapper.selectById(order_id);
        if (orders != null) {
            // hutool工具类，判断时间间隔
            long between = DateUtil.between(
                    DateUtil.parseDateTime(orders.getOrdertime()),// 订单的支付时间
                    DateUtil.parseDateTime(now), // 当前时间
                    DateUnit.DAY // 时间单位
            );
            if (between > 7) {
                // 超过7天，不支持退款
                jm.setCode(0);
                jm.setError("该订单已超过7天，不支持退款");
                return jm;
            }
        }
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(
                GATEWAY_URL,
                aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(),
                FORMAT,
                CHARSET,
                aliPayConfig.getAlipayPublicKey(),
                SIGN_TYPE
        );
        // 2. 创建 Request，设置参数
        Map<String,Object> map = (Map<String, Object>) redisTemplate.opsForValue().get("order:" + order_id);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("trade_no", map.get("trade_no")); // 支付宝回调的订单流水号
        bizContent.put("refund_amount", map.get("buyer_pay_amount")); // 订单的总金额
        bizContent.put("out_request_no", map.get("out_trade_no")); //  我的订单编号

        request.setBizContent(bizContent.toString());

        // 3. 执行请求
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        System.out.println("调用成功");
        // 4. 更新数据库状态
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_id", order_id );
        updateWrapper.set("status", -3);
        updateWrapper.set("refund_reasons", refundReasons);
//            ordersMapper.updatePayState(aliPay.getTraceNo(), "已退款", now);
        int result = ordersMapper.update(updateWrapper); // 退款成功，更新数据库状态
        if (result > 0) {
            jm.setCode(1);
            jm.setObj("退款成功");
            return jm;
        } else {
            jm.setCode(0);
            jm.setError("退款失败");
            return jm;
        }
    }

}
