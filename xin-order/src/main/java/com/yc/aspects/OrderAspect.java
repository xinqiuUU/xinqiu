package com.yc.aspects;


import com.google.gson.Gson;
import com.yc.api.CouponApi;
import com.yc.api.UserApi;
import com.yc.bean.MessageBean;
import com.yc.bean.ResponseResult;
import com.yc.bean.User;
import com.yc.bean.model.JsonModel;
import com.yc.dao.OrderMapper;
import com.yc.service.JmsMessageProcessor;
import com.yc.web.model.Orders;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect // 切面
@Component
@Order(100)  // 优先级 越小越先执行
public class OrderAspect {

    //支付成功  通知方法
    @Pointcut("execution(* com.yc.web.controllers.AliPayController.payNotify(..))")
    public void payNotify() {}

    //退款  通知方法
    @Pointcut("execution(* com.yc.web.controllers.AliPayController.refund(..))")
    public void refund() {}

//    @Pointcut("payNotify()")
//    public void all() {}

    @Autowired
    private JmsMessageProcessor jmsMessageProcessor;

    private UserApi userApi;
    @Autowired
    public void setOrdersMapper(UserApi userApi) {
        this.userApi = userApi;
    }

    private RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 增强类型:后置( after afterReturning , afterThrowing, finally)
    @AfterReturning("payNotify()")
    public void payNotify(  JoinPoint jp ) {  //连接点 即目标方法的参数
        Object[] obj = jp.getArgs(); // 第一个参数
        HttpServletRequest request = (HttpServletRequest) obj[0];
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
        }
        String orderId = params.get("out_trade_no");// 订单号
        String gmtPayment = params.get("gmt_payment"); // 支付时间
        String buyerId = params.get("buyer_id"); // 买家id
        String buyer_pay_amount =params.get("buyer_pay_amount");// 支付金额
        Orders order = orderMapper.selectById(orderId);
        ResponseResult result = userApi.getUser(order.getUid());
        // 使用 Gson 进行序列化
        Gson gson = new Gson();
        // 将 result.getObj() 转换为 User 对象
        User user = gson.fromJson(gson.toJson(result.getObj()), User.class);
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId); // 订单号
        map.put("gmtPayment",gmtPayment);// 支付时间
        map.put("buyerId",buyerId);// 买家id
        map.put("buyer_pay_amount",buyer_pay_amount);// 支付金额
        map.put("email",user.getEmail());
        // 发送邮件
        new Thread( ()->{
//            mailBiz.sendMail(email,"账户变动通知",info);
            jmsMessageProcessor.sendMessage( new MessageBean(map ,"账户支出")   );
        }).start();
    }

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CouponApi couponApi;
    // 增强类型:后置( after afterReturning , afterThrowing, finally)
    @AfterReturning(pointcut = "refund()" ,returning = "result")
    public void refund(JoinPoint jp , JsonModel result) {  //连接点 即目标方法的参数
        Object[] obj = jp.getArgs(); // 第一个参数
        String orderId = (String) obj[0];
        if (result.getCode() == 1){
            Map<String, Object> map = (Map<String, Object>) redisTemplate.opsForValue().get("order:" + orderId);

            String gmtPayment = (String) map.get("gmt_payment"); // 支付时间
            String buyerId = (String) map.get("buyer_id"); // 买家id
            String buyer_pay_amount = (String) map.get("buyer_pay_amount");// 支付金额

            Orders order = orderMapper.selectById(orderId);// 订单信息
            couponApi.refundCoupon(Integer.valueOf(order.getId()));// 退款优惠券
            ResponseResult resultUser = userApi.getUser(order.getUid());//获取用户信息
            // 使用 Gson 进行序列化
            Gson gson = new Gson();
            // 将 result.getObj() 转换为 User 对象
            User user = gson.fromJson(gson.toJson(resultUser.getObj()), User.class);
            Map<String,Object> sendMap = new HashMap<>();
            sendMap.put("orderId",orderId); // 订单号
            sendMap.put("gmtPayment",gmtPayment);// 支付时间
            sendMap.put("buyerId",buyerId);// 买家id
            sendMap.put("buyer_pay_amount",buyer_pay_amount);// 支付金额
            sendMap.put("email",user.getEmail());
            // 发送邮件
            new Thread( ()->{
//            mailBiz.sendMail(email,"账户变动通知",info);
                jmsMessageProcessor.sendMessage( new MessageBean(sendMap ,"账户收入")   );
            }).start();
        }
    }

}
