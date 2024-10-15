package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author SLGRoutine
 * @date 2024/6/21
 */
@Data
public class Admin implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer aid;// #用户ID，主键
    private String pwd;          //密码
    private String aname;          //用户名
    private String email;          //邮箱
    private String tel;            //电话
    private Integer status;        //商家状态(0：封禁，1：正常.....)
    private Integer type;          //管理员类型(0:系统管理员，1：商家)
    private String province;       //省份
    private String city;           // 市
    private String createtime;     //创建时间
    private String updatetime;       //更新时间
    private Integer role      ;      //管理员状态
}
