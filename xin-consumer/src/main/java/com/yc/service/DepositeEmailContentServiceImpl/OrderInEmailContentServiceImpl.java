package com.yc.service.DepositeEmailContentServiceImpl;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service("账户收入")
public class OrderInEmailContentServiceImpl implements EmailContentStrategyService{
    @Autowired
    private VelocityContext context;// 模板上下文
    @Autowired
    @Qualifier("orderInTemplate") // 模板名称transferTemplate注入
    private Template template;

    @Autowired
    @Qualifier("fullDf") // 模板名称withdrawTemplate注入
    private DateFormat fullDf;
    @Autowired
    @Qualifier("partDf") // 模板名称withdrawTemplate注入
    private DateFormat partDf;

    @Override
    public String getEmailContent(Map<String,Object> map) {
        try(StringWriter writer = new StringWriter()){
            Date date = new Date();
            context.put("email", map.get("email"));
            context.put("subject", "账户收入");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String gmtPayment = map.get("gmtPayment").toString();
            Date date1 = inputFormat.parse(gmtPayment); // 将字符串解析为 Date 对象
            context.put("optime", fullDf.format(date1)); //下单时间
            context.put("money",map.get("buyer_pay_amount") );// 交易金额
            context.put("buyerId",map.get("buyerId") );// 交易流水号
            context.put("currentDate", partDf.format(date));//当前时间

            template.merge(context,writer);  //合并内容。替换占位符
            return writer.toString();       //从流获取最终的字符后
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}

