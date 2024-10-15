package com.yc.bean;

import lombok.Data;

import java.util.List;

/*
    底部 信息 封装类
 */
@Data
public class Footer {
    private List<String> customerService; //客房服务
    private List<String> company;//公司
    private List<String> socialMedia;//社交媒体

}
