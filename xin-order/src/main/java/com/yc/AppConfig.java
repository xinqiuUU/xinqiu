package com.yc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration  // 配置类  相当于springmvc.xml
@ComponentScan  // 扫描本包下的所有组件
@EnableDiscoveryClient  //注册到注册中心 nacos
@EnableCaching          //开启缓存
@EnableFeignClients   //开启feign 远程调用
@MapperScan("com.yc.dao")  //扫描mapper接口  自动生成实现类
public class AppConfig {

}
