package com.yc.aspects;

import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Order(11)  // 优先级
public class CaptchaAspect {

    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    public CaptchaAspect(ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    private ScheduledFuture<?> scheduledTask; // 用于跟踪定时任务

    @AfterReturning(value = "execution(* com.yc.web.controllers.CaptchaController.getCaptcha(..))", returning = "result")
    public void scheduleCaptchaRemoval(JoinPoint jp, Object result) {
        Object[] obj = jp.getArgs(); // 第一个参数  账户id
        String email = (String) obj[0];
        HttpSession session = (HttpSession) obj[1];
        // 用户未登录，设置定时任务在5分钟后清除验证码
        scheduledTask = taskScheduler.schedule(() -> {
            if (email.equals(session.getAttribute("email"))) {
                session.removeAttribute("captcha");
                session.removeAttribute("email");
                System.out.println("验证码已清除: " + email);
            }
        }, new java.util.Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)));

        System.out.println("验证码将在5分钟后清除: " + email);
    }

    // 登录时取消定时器
    public void cancelCaptchaRemoval() {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
            scheduledTask = null; // 清空引用
            System.out.println("定时任务已取消");
        }
    }
}
