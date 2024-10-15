package com.yc.web.model;

import lombok.Builder;
import lombok.Data;

// 响应结果 的 模型类
@Data
@Builder  //lombok 提供的 建造者模式 ， 可以 链式调用 ， 可以 省略 构造方法
public class ResponseResult {
    private Integer code;
    private String msg;
    private Object obj;

    public static ResponseResult ok( String message ) {
        return ResponseResult.builder().code( 1 ).msg( message ).build();
    }

    public static ResponseResult ok( ) {
        return ResponseResult.builder().code( 1 ).msg( "成功" ).build();
    }

    public static ResponseResult error( String message ) {
        return ResponseResult.builder().code( 0 ).msg( message ).build();
    }

    public static ResponseResult error(  ) {
        return ResponseResult.builder().code( 0 ).msg( "失败" ).build();
    }

    public <T> ResponseResult setData(T obj) {
        this.obj = obj;
        return this;
    }
}
