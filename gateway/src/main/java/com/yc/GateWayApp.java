package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 开启服务发现 nacos 注册中心
public class GateWayApp {

    public static void main(String[] args) {
        System.out.println("GateWayApp服务启动成功");
        SpringApplication.run(GateWayApp.class, args);
    }
}