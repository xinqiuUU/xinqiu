package com.yc.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//给特定服务配置负载均衡策略 默认是轮询策略
//@LoadBalancerClient(name = "res-food")

//如果要对某个服务指定负载均衡策略的话 则需加上 configuration 属性
//@LoadBalancerClient(name = "res-food", configuration = MyLoadBalancerClientConfiguration.class)

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //WEB 的 RestTemplate 模板客户端 发送web请求 获取数据  用于调用API资源
    @LoadBalanced // 加入客户端负载均衡  以后这个RestTemplate 就可以调用服务名  而不是IP地址
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*
        CORS（跨域资源共享）配置
        解决浏览器的跨域问题
        开发浏览器调用API资源
        用于配置跨域资源共享（CORS）
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) { // 重写父类或接口中的方法
        registry.addMapping("/**")            // 对所有路径启用 CORS 配置
                .allowedOrigins("*")                     // 允许所有来源的请求
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的 HTTP 方法
                .allowedHeaders("*") // 允许所有的请求头
                .maxAge(3600);     // 预检请求的有效期（秒）
    }

}
