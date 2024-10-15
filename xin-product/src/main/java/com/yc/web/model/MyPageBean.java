package com.yc.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// VO对象，对应页面相关
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageBean<T> {
    // 以下变量是传递给页面的已知参数
    private int pageno;         // 当前的页码
    private int size;            // 每页显示条数
    private String sortby;       // 排序字段
    private String sort;         // 排序方式: asc/desc

    // 以下两个变量是将查询结果传递给前端的参数
    private List<T> records;     // 查询结果

    // 需要业务层后端计算
    private long total;          // 总记录数
    private int totalPages;      // 总页数
    private int pre;             // 上一页页码
    private int next;            // 下一页页码

    private Product product;     // 查询的参数 fname, detail => like

    // 计算 pre, next, totalPages
    public void calculate() {
        // 关心的页数信息
        // 计算总页数
        long totalPages = this.total % size == 0 ?
                total / size : total / size + 1;
        this.totalPages = (int) totalPages;

        // 上一页页码
        if (pageno <= 1) {
            pre = 1;
        } else {
            pre = pageno - 1;
        }

        // 下一页的页码
        if (pageno == totalPages) {
            next = (int) totalPages;
        } else {
            next = pageno + 1;
        }
    }
}
