package com.yc.configuration;

import com.yc.web.interceptors.JwtTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 注册拦截器  的配置类
@Configuration
public class SpringMVCWebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtTokenInterceptor jwtTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        registry.addInterceptor( jwtTokenInterceptor ) // 注册拦截器
                .addPathPatterns("/cart/**");  //特定的路径才拦截
    }

}
