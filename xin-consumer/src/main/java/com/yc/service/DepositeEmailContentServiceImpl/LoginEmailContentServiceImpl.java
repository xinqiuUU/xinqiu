package com.yc.service.DepositeEmailContentServiceImpl;


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service("验证码")
public class LoginEmailContentServiceImpl implements EmailContentStrategyService{
    @Autowired
    private VelocityContext context;// 模板上下文
    @Autowired
    @Qualifier("loginTemplate") // 模板名称transferTemplate注入
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
            context.put("captcha", map.get("captcha"));
            context.put("currentDate", partDf.format(date));//当前时间
            template.merge(context,writer);  //合并内容。替换占位符
            return writer.toString();       //从流获取最终的字符后
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}

