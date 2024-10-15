package com.yc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@MapperScan("com.yc.dao")  //扫描mapper接口  自动生成实现类
@EnableDiscoveryClient  //注册到注册中心 nacos
@EnableCaching          //开启缓存
public class AppConfig {

}
