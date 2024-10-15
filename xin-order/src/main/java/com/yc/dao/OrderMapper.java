package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.ProductDetail;
import com.yc.web.model.Orders;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends BaseMapper<Orders> {

    //获取用户订单
    @Select("SELECT product.*, o.amount AS count " +
            "FROM product " +
            "INNER JOIN (SELECT pid, amount FROM order_detail WHERE order_id = #{orderId}) o " +
            "ON product.pid = o.pid")
    List<ProductDetail> selectProductDetailsByOrderId(Integer orderId);

    //获取季度销售额
    @Select("SELECT YEAR(o.ordertime) AS year, " +
            "QUARTER(o.ordertime) AS quarter, " +
            "SUM(od.amount) AS total_sales " +
            "FROM orders o " +
            "JOIN order_detail od ON o.order_id = od.order_id " +
            "GROUP BY YEAR(o.ordertime), QUARTER(o.ordertime) " +
            "ORDER BY year, quarter")
    List<Map<String, Object>> getQuarterSales();

    //获取年度销售额
    @Select("SELECT YEAR(o.ordertime) AS sales_year, SUM(od.amount) AS total_sales " +
            "FROM orders o JOIN order_detail od ON o.order_id = od.order_id " +
            "WHERE o.status >= 1 " +
            "GROUP BY sales_year " +
            "ORDER BY sales_year")
    List<Map<String, Object>> getYearSales(); // 获取年度销量数据

    //获取指定年月份的销量数据
    @Select("SELECT MONTH(ord.ordertime) AS month, YEAR(ord.ordertime) AS year, SUM(det.amount) AS total_sales " +
            "FROM orders ord JOIN order_detail det ON ord.order_id = det.order_id " +
            "WHERE YEAR(ord.ordertime) = #{year} AND ord.status >= 1 " +
            "GROUP BY YEAR(ord.ordertime), MONTH(ord.ordertime) " +
            "ORDER BY year, month")
    List<Map<String, Object>> getMonthlySales(int year); // 获取指定年月份的销量数据
}
