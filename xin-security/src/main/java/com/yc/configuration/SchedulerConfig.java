package com.yc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {

    //定时任务线程池
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10); // 设置线程池大小
        scheduler.setThreadNamePrefix("captcha-task-"); // 设置线程名称前缀
        scheduler.initialize();
        return scheduler;
    }
}
