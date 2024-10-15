package com.yc.myTimeGatewayFilterFactory;

import com.yc.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/*
  自定义过滤器   token 过滤器   局部路由
  1. 继承AbstractGatewayFilterFactory
  2. 重写apply方法
  3. 重写shortcutFieldOrder方法
 */

@Component
@Slf4j
public class TokenFilterFactory extends AbstractGatewayFilterFactory<TokenFilterFactory.Config> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public TokenFilterFactory(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
          //业务逻辑  从请求头中获取token  判断token是否有效
          log.info("进入了token认证过滤器");
          String token = exchange.getRequest().getHeaders().getFirst("token");
          if (token == null || jwtTokenUtil.isTokenExpired(token)){
              // 未登录  未授权  401  未认证
              exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
              return exchange.getResponse().setComplete();
          }
          return chain.filter(exchange);
        };
    }

    public static class Config{
        //配置属性
    }
}
