package com.yc.web.model;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 回复实体类
 */
@Data
public class Reply {
    private String replierName; // 回复者姓名
    private String head_photo;  // 回复者头像
    private String replyToName; // 被回复者姓名
    private String message;     // 回复内容
    private String replyDate;   // 回复日期
    // 获取当前时间的静态方法
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}