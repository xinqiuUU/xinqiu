package com.yc.service;

import java.util.List;
import java.util.Map;

public interface SalesBiz {

    // 按季度统计销售情况
    List<int[]> getQuarterSales();

    // 按年度统计销售情况
    int[] getYearDate(); // 获取年度数据

    // 按月份统计销售情况
    int[] getMonthDate(int year); // 获取指定年的月销量数据
}
