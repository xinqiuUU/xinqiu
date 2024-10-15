package com.yc.web.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.bean.ProductDetail;
import lombok.Data;

import java.util.List;

@Data
@TableName("order_detail")
public class OrderDetail {
    @TableId(type = IdType.AUTO)
    private Integer detail_id;          //订单表 id
    private Integer order_id;          //订单表
    private Integer pid;               //外键，用户id
    private Integer amount;            //数量
    private Double smallCount;         //小计
}
