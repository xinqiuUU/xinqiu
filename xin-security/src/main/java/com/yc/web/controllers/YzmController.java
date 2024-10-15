package com.yc.web.controllers;


import com.aliyun.tea.TeaException;
import com.yc.bean.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.aliyun.captcha20230305.models.VerifyCaptchaRequest;
import com.aliyun.captcha20230305.models.VerifyCaptchaResponse;
import com.aliyun.teaopenapi.models.Config;
/*
    用于生成邮件验证码的控制器
 */
@RestController
@RequestMapping("/security")
@Slf4j
public class YzmController {

    @PostMapping ("/yzm")
    public ResponseResult regYzm(@RequestParam String captchaVerifyParam) throws Exception {
        log.info("验证码为："+captchaVerifyParam);
        // ====================== 1. 初始化配置 ======================
        Config config = new Config();
        // 设置您的AccessKey ID 和 AccessKey Secret。
        // getEnvProperty只是个示例方法，需要您自己实现AccessKey ID 和 AccessKey Secret安全的获取方式。
        config.accessKeyId = "";
        config.accessKeySecret = "";
        //设置请求地址
        config.endpoint = "captcha.cn-shanghai.aliyuncs.com";
        // 设置连接超时为5000毫秒
        config.connectTimeout = 5000;
        // 设置读超时为5000毫秒
        config.readTimeout = 5000;
        // ====================== 2. 初始化客户端（实际生产代码中建议复用client） ======================
        com.aliyun.captcha20230305.Client client = new com.aliyun.captcha20230305.Client(config);
        // 创建APi请求
        VerifyCaptchaRequest request = new VerifyCaptchaRequest();
        request.captchaVerifyParam = captchaVerifyParam;
        //对前端传来的数据  进行  验证    得到验证结果
        VerifyCaptchaResponse response = client.verifyCaptcha(request);
        // ====================== 3. 发起请求） ======================
        try {
            // 建议使用您系统中的日志组件，打印返回
            // 获取验证码验证结果（请注意判空），将结果返回给前端。出现异常建议认为验证通过，优先保证业务可用，然后尽快排查异常原因。
            Boolean captchaVerifyResult = response.body.result.verifyResult; //验证结果  true为通过，false为不通过
            if (captchaVerifyResult){
                return ResponseResult.ok("验证成功");
            }else {
                return ResponseResult.error(); //  验证  结果  true为通过，false为不通过
            }
        } catch (TeaException error) {
            // 建议使用您系统中的日志组件，打印异常
            // 出现异常建议认为验证通过，优先保证业务可用，然后尽快排查异常原因。
            Boolean captchaVerifyResult = true;
            log.error("Error message:"+error.getMessage());
            return ResponseResult.ok(); //  验证  结果  true为通过，false为不通过
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 建议使用您系统中的日志组件，打印异常
            // 出现异常建议认为验证通过，优先保证业务可用，然后尽快排查异常原因。
            Boolean captchaVerifyResult = true;
            log.error("Error message:"+error.getMessage());
            return ResponseResult.ok(); //  验证  结果  true为通过，false为不通过
        }
    }
}
