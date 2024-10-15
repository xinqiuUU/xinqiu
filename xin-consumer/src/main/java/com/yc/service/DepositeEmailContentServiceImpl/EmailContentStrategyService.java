package com.yc.service.DepositeEmailContentServiceImpl;


import java.util.Map;

/*
    策略接口
 */
public interface EmailContentStrategyService {

    default String getEmailContent(Map<String,Object> map) {
        return "";
    }
}
