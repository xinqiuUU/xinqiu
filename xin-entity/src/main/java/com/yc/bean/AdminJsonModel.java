package com.yc.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author SLGRoutine
 * @date 2024/6/20
 */
@Data
public class AdminJsonModel implements Serializable {
    private Integer code;//同意一下，后台成功为0，失败为1
    private String msg;
    private Object data;
    private Long count;
}
