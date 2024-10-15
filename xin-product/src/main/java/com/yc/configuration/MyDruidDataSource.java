package com.yc.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@RefreshScope
@Configuration
@Slf4j
public class MyDruidDataSource {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean  //IOC
    @Primary  //优先使用这个代码IOC 表示默认数据源
    @RefreshScope  //动态刷新
    public DataSource druid(){
        log.info("====初始化druid数据源====");
        DruidDataSource ds=new DruidDataSource();
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(this.driverClassName);
        ds.setUrl(url);
        return  ds;
    }


}
