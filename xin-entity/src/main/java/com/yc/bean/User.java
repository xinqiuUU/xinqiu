package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author SLGRoutine
 * @date 2024/6/20
 */

@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer uid ;      //#用户ID，主键(自动生成)
    private String uname ;      // #用户名(登陆注册时必填的)
    private String head_photo;  //用户头像
    private String pwd ;        //#密码
    private String sex ;        //  #性别
    private Integer age ;       //  #年龄
    private String  email ;     //#邮箱
    private String tel ;        //#电话
    private Integer status ;    //#用户状态(0：封禁，1：正常.....)
    private Integer logins ;    //#访问(登录)次数
    private String province ;   //#省份
    private String city;        // #市
    private String county ;     //#详细地址
    private String createtime ; //,#创建时间(自动生成)
    private String updatetime ; //#更新时间（
}
