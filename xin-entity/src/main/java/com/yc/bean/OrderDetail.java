package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetail {
    @TableId(type = IdType.AUTO)
    private Integer order_id;          //订单表 id
    private Integer uid;               //外键，用户id
    private String ordertime;          //订单创建时间(自动生成)
    private String delivery_company;   //快递公司
    private Double postage;            //邮费
    private Double totalprice;         //总价格
//    #订单状态  0 待支付， 1 已付款， 2 已发货，-1,被取消订单  -3 退款  -4退款审核中，
    private Integer status;            //订单状态  0待支付，1已付款，2已发货，

    private String refund_reasons;     //退款原因

    private String addressee;          //收件人姓名
    private String emailee;            //邮箱
    private String phone;              //电话号码
    private String address;            //发货地址

//    private List<ProductDetail> productDetail;

}
