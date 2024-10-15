package com.yc.configuration;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/*
    模板引擎配置
 */
@Configuration
public class VelocityConfig {
    // 模板引擎
    @Bean
    public VelocityEngine velocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        return velocityEngine;
    }
    // 模板上下文
    @Bean
    @Scope("prototype")
    public VelocityContext velocityContext() {
        VelocityContext context = new VelocityContext();
        return context;
    }

    //账户收入模板
    @Bean
    public Template orderInTemplate( @Autowired VelocityEngine velocityEngine) {
        Template template = velocityEngine.getTemplate("vms/orderIn.vm", "utf-8");
        return template;
    }
    //账户支出模板
    @Bean
    public Template orderOutTemplate( @Autowired VelocityEngine velocityEngine) {
        Template template = velocityEngine.getTemplate("vms/orderOut.vm", "utf-8");
        return template;
    }
    //用户登录模板
    @Bean
    public Template loginTemplate( @Autowired VelocityEngine velocityEngine) {
        Template template = velocityEngine.getTemplate("vms/login.vm", "utf-8");
        return template;
    }
    // 完整时间格式
    @Bean
    public DateFormat fullDf(){
        DateFormat fullDf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return fullDf;
    }
    // 部分时间格式
    @Bean
    public DateFormat partDf(){
        DateFormat partDf = new SimpleDateFormat("yyyy年MM月dd日 北京时间hh时");
        return partDf;
    }


}
