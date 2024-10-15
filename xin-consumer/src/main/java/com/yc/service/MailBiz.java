package com.yc.service;

import com.google.gson.Gson;
import com.yc.model.Email;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Log
public class MailBiz {

    @Autowired  // 注入JavaMailSender
    private JavaMailSender MailSender;

    @Value("${spring.mail.properties.mail.smtp.from}")
    public String fromemail;

//    @Autowired
//    private RedisTemplate redisTemplate;
    //返回前端的数据
    List<Email> results = new ArrayList<>();

    @Autowired
    private WebSocketServer webSocketServer;

    @Async  // 异步发送邮件
    public void send(Map<String,Object> map, String subject, String text) throws IOException {
//        SimpleMailMessage message = new SimpleMailMessage(); //不包括附件
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendTime = df.format(date);
        Gson go = new Gson();
        MimeMessage mm = MailSender.createMimeMessage();  //可以包括附件
        String to = (String) map.get("email");// 收件人邮箱
        try{
            // 邮件内容                                        true 表示可以加附件
            MimeMessageHelper message = new MimeMessageHelper(mm, true,"UTF-8");
            message.setFrom(fromemail);// 发件人
            message.setTo(to);// 收件人
            message.setSubject(subject);// 邮件主题
            message.setText(text , true);// 邮件内容 一定要有 true 代表当成html代码
            MailSender.send( mm );// 发送邮件

//            Email email = new Email(1,fromemail,to,subject,sendTime);
//            results.add(email);
//            webSocketServer.send( go.toJson(results) ); // 发送websocket消息 通知前端需要刷新了 即前端需要去redis数据库中读取消息了
        }catch (Exception e){
            e.printStackTrace();
            log.info("邮件发送失败:"+e.getMessage());
//            Email email = new Email(0,fromemail,to,subject,sendTime);
//            results.add(email);
//            webSocketServer.send( go.toJson(results) ); // 发送websocket消息 通知前端需要刷新了 即前端需要去redis数据库中读取消息了
        }

    }

}
