package com.yc.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author SLGRoutine
 * @date 2024/6/26
 */
public class TimeStampUtil {
    /**
     * 注意，存进来的TimeStamp
     * @param eventDateTimeStr
     * @return
     */
    public static Long getTimeInterval(String eventDateTimeStr) {
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 解析给定的事件时间字符串为 LocalDateTime 对象
        LocalDateTime eventDateTime = LocalDateTime.parse(eventDateTimeStr, formatter);

        // 获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 计算时间差
        Duration duration = Duration.between(currentDateTime, eventDateTime);

        // 获取时间差的天数
        long daysDifference = duration.toDays()>0?duration.toDays():-duration.toDays();

        return daysDifference;
    }
}
