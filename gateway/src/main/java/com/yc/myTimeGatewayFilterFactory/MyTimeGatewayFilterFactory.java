package com.yc.myTimeGatewayFilterFactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*
  自定义全局过滤器  用于统计每个请求的处理时长 并记录到日志中
  1.实现GlobalFilter接口
  2.重写filter方法
  3.在filter方法中获取请求参数，进行业务处理
  4.在filter方法中调用chain.filter方法，将请求转发到下一个过滤器
  5.在filter方法中返回Mono<Void>对象
 */

@Slf4j
@Component
public class MyTimeGatewayFilterFactory implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("进入了MyTimeGatewayFilterFactory 全局过滤器");
        //记录开始时间
        long startTime = System.currentTimeMillis();
        //调用chain.filter方法，将请求转发到下一个过滤器
        //Promise
        return chain.filter(exchange).then(
                //记录结束时间  filter后期处理
                Mono.fromRunnable( ()->{
                    long endTime = System.currentTimeMillis();
                    log.info(  exchange.getRequest().getPath() + "请求处理总时长：" + (endTime - startTime) + "ms");
                })
        );
    }

    @Override
    public int getOrder() {
        //返回一个整数，表示过滤器的执行顺序，数字越小，执行顺序越靠前
        return Ordered.LOWEST_PRECEDENCE;
    }
}