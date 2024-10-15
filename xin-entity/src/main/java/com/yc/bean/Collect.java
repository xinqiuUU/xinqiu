package com.yc.bean;

import lombok.Data;

import java.util.List;

/*
       用户收藏的商品
 */

@Data
public class Collect {
    private Long timestamp; //时间戳
    private Integer pid;    //商品pid
    private String pname;   //商品名称
    //商品图片地址  oss
    private List<String> urls;
}
