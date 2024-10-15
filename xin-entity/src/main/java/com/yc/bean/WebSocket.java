package com.yc.bean;

import lombok.Data;

/*
       聊天  类
 */
@Data
public class WebSocket {
    private String name;   //判断是管理员还是用户
    private String timestamp;//时间戳
    private String content;//聊天内容
}
