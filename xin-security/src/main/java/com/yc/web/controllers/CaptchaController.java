package com.yc.web.controllers;

import com.yc.bean.ResponseResult;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/*
    用于生成邮件验证码的控制器
 */
@RestController
@RequestMapping("/security")
@Slf4j
public class CaptchaController {

    //因为验证码生成后要存在session中，所以这个方法的参数由springmvc自动注入 HttpSession
    //因为使用的是springboot 3 这里的HttpSession 是 jakarta.servlet.http.HttpSession
    @GetMapping("/captcha")
    public ResponseResult getCaptcha(@RequestParam String email, HttpSession session) {
        if (email == null || email.trim().equals("")){
            return ResponseResult.error("邮箱不能为空");
        }
        // 生成验证码
        Random random = new Random();
        int captcha = 100000 + random.nextInt(900000); // 生成一个六位数的随机数
//        mailBiz.send(email,"验证码","验证码:"+captcha);
        // 将验证码存入session中
        session.setAttribute("captcha", captcha);
        session.setAttribute("email", email);
        return ResponseResult.ok("验证码已发送");
    }

}
