package com.yc.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDetail implements Serializable {
    private int pid;
    private int aid;
    private String pname;
    private String description;
    private String type;
    private String firm;
    private double normprice;
    private double realprice;
    private String createtime;
    private String updatetime;
    private int status;

    //购物车中的数量
    private int count;

}
