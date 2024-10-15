package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync  //开启spring async异步
public class JmsConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmsConsumerApplication.class, args);
    }

}
