package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

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

}
