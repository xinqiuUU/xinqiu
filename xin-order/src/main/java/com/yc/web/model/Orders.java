package com.yc.web.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.bean.ProductDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 订单表
 * @author SLGRoutine
 * @date 2024/6/22
 */
@Data
@TableName("orders")
public class Orders implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer order_id;          //订单表
    private Integer uid;               //外键，用户id
    private String ordertime;          //订单创建时间(自动生成)
    private Double totalprice;         //总价格
    //    #订单状态  0 待支付， 1 已付款， 2 已发货，-1,被取消订单  -3 退款  -4退款审核中，
    private Integer status;            //订单状态  0待支付，1已付款，2已发货，
    private String address;            //发货地址
    private String refund_reasons;     //退单原因
//    如果是给别人买
    private String addressee;           //收件人
    private String emailee ;            //收件人邮箱
    private String phone;               //收件人电话
    private Double postage;             //运费
    private String delivery_company;    //物流公司
    private Integer id;                 //用户优惠劵id
    //关联的表的字段

    @TableField(exist = false)
    private Long timeStage;//这个是下单到目前为止的时间

    @TableField(exist = false)
    private List<ProductDetail> productDetail;

}
