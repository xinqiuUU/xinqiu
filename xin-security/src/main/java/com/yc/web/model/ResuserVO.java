package com.yc.web.model;

import lombok.Data;

@Data   //前端传给后端的数据
public class ResuserVO {
    private Integer uid;
    private String username;
    private String password;
    private String email;  // 邮箱
    private String captcha;  //验证码
}
