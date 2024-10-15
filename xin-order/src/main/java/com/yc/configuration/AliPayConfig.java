package com.yc.configuration;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
    private String appId;     // 应用ID
    private String appPrivateKey; // 应用私钥
    private String alipayPublicKey; // 支付宝公钥
    private String notifyUrl; // 异步回调地址
    public static String return_url = "http://localhost/checkout.html";

    @PostConstruct
    public void init() {
        // 设置参数（全局只需设置一次）
        Config config = new Config(); // 实例化对象
        config.protocol = "https"; // 协议
        config.gatewayHost = "openapi.alipaydev.com"; // 网关地址
        config.signType = "RSA2"; // 签名类型
        config.appId = this.appId; // 应用ID
        config.merchantPrivateKey = this.appPrivateKey; // 应用私钥
        config.alipayPublicKey = this.alipayPublicKey; // 支付宝公钥
        config.notifyUrl = this.notifyUrl; // 异步回调地址
        Factory.setOptions(config); // 设置参数
        System.out.println("=======支付宝SDK初始化成功=======");
    }

}
