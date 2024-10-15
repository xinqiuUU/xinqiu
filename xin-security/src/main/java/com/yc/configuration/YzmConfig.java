package com.yc.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// 阿里云滑动验证码配置类
@Configuration
@ConfigurationProperties(prefix = "aliyun.yzm")
@Data
public class YzmConfig {
    private String accessKeyId;
    private String accessKeySecret;
}
