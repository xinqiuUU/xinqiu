package com.yc.bean;

import lombok.Data;

@Data
public class AliPay {
    private String traceNo; // 商户订单号
    private double totalAmount; // 订单金额
    private String subject; // 订单名称
    private String alipayTraceNo; // 支付宝订单号
    private Double postage; // 运费
}

