package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_coupon")
public class UserCoupon {
    @TableId(type = IdType.AUTO)
    @TableField("id")       // 指定数据库中的字段名
    private Integer id;     // 唯一标识
    private Integer uid;    // 用户ID
    private Integer cid;    // 优惠券ID
    private Integer status; // 优惠券的使用状态， 删除:0  默认可用:1 已使用:2 已失效:3
    private String get_date;  // 领取时间

}
