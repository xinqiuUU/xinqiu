package com.yc.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//spring doc 显示界面的主题信息
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info( new Info()
                        .title("SpringBoot 3.0 整合 Swagger3 生成接口文档")
                        .version("1.0")
                        .description("用户认证鉴权API文档"));
    }


}
