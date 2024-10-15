package com.yc.web.model;

import lombok.Data;

/*
    商品图片表
 */
@Data
public class ProductPic {
    private Integer ppid;
    private Integer pid;
    private String url;
    private Integer is_primary;
}
