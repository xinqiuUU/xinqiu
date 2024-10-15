package com.yc.service;

import com.yc.dao.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional  //开启事务
@Slf4j
public class SalesBizImpl implements SalesBiz{

    @Autowired
    private OrderMapper orderMapper;

    // 按季度统计销售情况
    @Override
    public List<int[]> getQuarterSales() {
        List<int[]> resultList = new ArrayList<>();
        List<Map<String, Object>> list = orderMapper.getQuarterSales(); // 调用 Mapper 方法获取季度销量数据

        for (int i = 0; i < 4; i++) {
            resultList.add(new int[10]); // 创建一个大小为10的数组，用于存储2015到2024年的销量数据
        }

        // 根据查询结果填充 resultList
        for (Map<String, Object> map : list) {
            int year = (int) map.get("year");
            BigDecimal totalSalesBd = (BigDecimal) map.get("total_sales");
            int totalSales = totalSalesBd.intValue(); // 将 BigDecimal 转换为 int
            int quarter = (int) map.get("quarter");

            // 计算在 resultList 中的位置
            int yearIndex = year - 2015; // 例如，2023 -> 索引8 (2015 + 8 = 2023)
            int quarterIndex = quarter - 1; // 例如，季度4 -> 索引3

            // 将总销量赋值到 resultList 的正确位置
            resultList.get(quarterIndex)[yearIndex] = totalSales;
        }

        return resultList; // 返回结果列表
    }

    // 按年度统计销售情况  返回2024年往前10年的数据
    @Override
    public int[] getYearDate() {
        List<Map<String, Object>> list = orderMapper.getYearSales(); // 调用 Mapper 方法获取年度销量数据

        // 确定数组的长度，从2015年到2024年，共10年
        int yearsRange = 2024 - 2015 + 1;
        int[] totalSalesArray = new int[yearsRange]; // 返回的数据数组
        Arrays.fill(totalSalesArray, 0); // 初始化数组，确保每个年份的初始值为0

        // 遍历 list 中的每个 Map 对象
        for (Map<String, Object> map : list) {
            int salesYear = (int) map.get("sales_year"); // 获取 sales_year 的值
            BigDecimal totalSalesDecimal = (BigDecimal) map.get("total_sales"); // 获取 total_sales 的值
            int totalSales = totalSalesDecimal.intValue(); // 将 BigDecimal 转换为 int 类型

            // 计算年份对应的数组索引
            int index = salesYear - 2015; // 因为从2015年开始，索引从0开始

            // 将 total_sales 存入数组对应位置
            if (index >= 0 && index < totalSalesArray.length) {
                totalSalesArray[index] = totalSales;
            }
        }

        return totalSalesArray; // 返回结果数组
    }

    // 按月份统计销售情况
    @Override
    public int[] getMonthDate(int year) {
        List<Map<String, Object>> list = orderMapper.getMonthlySales(year); // 调用 Mapper 方法获取月销量数据

        int[] monthlySales = new int[12]; // 初始化一个长度为12的数组
        Arrays.fill(monthlySales, 0); // 确保每个月的初始值为0

        // 遍历集合，填充月份数据
        for (Map<String, Object> map : list) {
            int month = (int) map.get("month"); // 获取月份
            Number totalSales = (Number) map.get("total_sales"); // 获取总销售额

            if (totalSales != null) {
                int totalSalesInt = totalSales.intValue(); // 转换为 int
                if (month >= 1 && month <= 12) {
                    monthlySales[month - 1] = totalSalesInt; // month-1 是因为数组下标从0开始
                } else {
                    throw new IllegalArgumentException("无效的月份值: " + month);
                }
            } else {
                throw new IllegalArgumentException("该月份的总销量为 null: " + month);
            }
        }

        System.out.println(year + " 年的月销量为: " + Arrays.toString(monthlySales));
        return monthlySales; // 返回结果数组
    }
}
