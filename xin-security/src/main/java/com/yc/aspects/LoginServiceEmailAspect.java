package com.yc.aspects;

import com.yc.bean.MessageBean;
import jakarta.servlet.http.HttpSession;
import com.yc.service.JmsMessageProcessor;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect // 切面
@Component
@Order(10)  // 优先级
public class LoginServiceEmailAspect {

    //存款
    @Pointcut("execution(* com.yc.web.controllers.CaptchaController.getCaptcha(..))") //邮件切点
    public void login() {}

    @Pointcut("login() ")
    public void all() {}

    @Autowired
    private JmsMessageProcessor jmsMessageProcessor;


    // 增强类型: 后置 (afterReturning)
    @AfterReturning(pointcut = "all()", returning = "result")
    public void sendEmail(JoinPoint jp, Object result ) {
        Object[] obj = jp.getArgs(); // 第一个参数  账户id
        String email = (String) obj[0];
        HttpSession session = (HttpSession) obj[1];
        String captcha = String.valueOf( session.getAttribute( "captcha" ) ) ;
        Map<String,Object> map = new HashMap<>();
        map.put("email",email);
        map.put("captcha",captcha);

        new Thread( ()->{
//            mailBiz.sendMail(email,"账户变动通知",info);
            jmsMessageProcessor.sendMessage( new MessageBean(map,"验证码") );

        }).start();
    }

}
