package com.yc.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

// 优惠券详情
@Data
public class UserCouponDetailsDTO implements Serializable {
    private Integer id;         // 优惠券详情ID
    private Integer uid;        // 用户ID
    private Integer cid;        // 优惠券ID
    private String code;        // 优惠券代码
    private String type;        // 优惠券类型
    private String value;       // 优惠券折扣
    private String start_date;    // 优惠券开始时间
    private String end_date;      // 优惠券结束时间
    private Integer status;     // 优惠券的使用状态， 删除为 0  默认可用 1 已使用 2 已失效 3
    private String get_date;      // 用户领取时间
}
