package com.yc.service;

import com.google.gson.Gson;
import com.yc.bean.MessageBean;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log
public class JmsMessageProcessor {

    @Value("${spring.activemq.queueName}")
    private String queueName;// 队列名称

    @Autowired
    private JmsTemplate jmsTemplate; // 注入 JmsTemplate 实例

    @Async("taskExecutor")
    // 发送消息到队列
    public void sendMessage(MessageBean messageBean) {
        Gson gson = new Gson();
        String json = gson.toJson(    messageBean  );
        jmsTemplate.convertAndSend( queueName, json );// 发送消息到队列
        log.info("发送消息到队列");
    }
}
