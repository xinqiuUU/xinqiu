package com.yc.utils;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于暂时存储前端请求中的OSS图片地址
 * @author SLGRoutine
 * @date 2024/6/21
 */

public  class PicUrl {
    /**
     * 用于暂时存储前端请求中的OSS图片地址
     * 如果图片传输失败，会存在字符串0
     */
    public static   List<String> urls = new ArrayList<>();

    public static List<String> getaUrls() {
        return urls;
    }

    public static void addUrl(String url) {
        urls.add(url);
    }

    public static void clearUrls() {
        urls.clear();
    }

}
