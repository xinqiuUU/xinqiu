package com.yc.configuration;

import com.yc.web.interceptors.DetailCountInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 注册拦截器  的配置类
@Configuration
public class SpringMVCWebConfig implements WebMvcConfigurer {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        registry.addInterceptor( new DetailCountInterceptor( redisTemplate ) ) // 注册拦截器
                .addPathPatterns("/product/findById/**");  //特定的路径才拦截
    }

}
