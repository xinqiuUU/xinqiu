package com.yc.service;

import com.yc.service.DepositeEmailContentServiceImpl.StrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class VelocityTemplateBizImpl {

    @Autowired
    private StrategyContext strategyContext; // 策略上下文

    // 生成邮件内容
    public String genEmailContent(String opType, Map<String,Object> map) {
        String info = "";
        info = strategyContext.getEmailContent(opType, map );
        return info;
    }
}
