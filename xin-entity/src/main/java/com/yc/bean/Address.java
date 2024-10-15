package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/*
      收货地址 类
 */
@TableName("delivery_address")
@Data
public class Address implements Serializable {
    @TableId(type = IdType.AUTO)
    @TableField("addr_id")  // 指定数据库中的字段名
    private Integer addr_id;  // 收货地址主键id
    private Integer uid;      //外键，用户ID

    private String addressee;     //收件人姓名
    private String email;     //邮箱
    private String tel;       //电话号码

    private String province;  //省份
    private String city;      //市
    private String county;    //县
    private String details;   //详细地址
    private Integer status;   //1 为默认地址  0 为备用地址

}
