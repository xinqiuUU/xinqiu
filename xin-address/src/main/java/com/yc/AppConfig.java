package com.yc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 配置类
@ComponentScan // 扫描bean
@MapperScan("com.yc.dao") // 扫描dao
@EnableDiscoveryClient // 开启服务发现 nacos 注册中心
@EnableCaching  //开启缓存
public class AppConfig {

}
