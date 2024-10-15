package com.yc.web.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Product implements Serializable {
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

    //商品全部图片地址  oss
    @TableField(exist = false)
    private List<String> urls;

    @TableField(exist = false)
    private String url;//图片地址

    @TableField(exist = false) // 表示该字段不是数据库表中的字段
    private Double rating;//评分

}
