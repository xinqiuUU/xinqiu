package com.yc.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

//消息实体类
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageBean {
    private Map<String,Object> map; // 操作类型 存放数据
    private String opType;
}
