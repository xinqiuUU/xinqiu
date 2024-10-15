package com.yc.web.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductDetail implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer pid;
    private Integer aid;
    private String pname;
    private String description;
    private String type;
    private String firm;
    private Double normprice;
    private Double realprice;
    private String createtime;
    private String updatetime;
    private Integer status;

    //商品图片地址  oss
    @TableField(exist = false)
    private List<String> urls;

    @TableField(exist = false)
    //购物车中的数量
    private Integer count;

    @TableField(exist = false)
    //商品的平均评分
    private Double rating;

}
