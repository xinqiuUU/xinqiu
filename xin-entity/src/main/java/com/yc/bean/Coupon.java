package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("coupon")
public class Coupon {
    @TableId(type = IdType.AUTO) // 自增主键
    @TableField("cid")  // 指定数据库中的字段名
    private Integer cid;  // 优惠券的唯一标识
    private String code;       // 优惠券代码，必须唯一
    private String type;        // 优惠券类型，如折扣券
    private String value;   // 优惠券的折扣金额
    private String start_date;     // 优惠券生效的起始日期
    private String end_date;       // 优惠券失效的结束日期
    private Integer quantity;    // 优惠券的总数量

}