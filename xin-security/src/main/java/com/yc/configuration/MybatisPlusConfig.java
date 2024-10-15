package com.yc.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    配置 MyBatis-Plus 的分页插件
 */
@Configuration
public class MybatisPlusConfig {

    /*
     MybatisPlusInterceptor : MyBatis-Plus 的核心拦截器，用于处理各种插件功能
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //PaginationInnerInterceptor 内拦截器，专门用于处理分页逻辑   指定数据库类型
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
