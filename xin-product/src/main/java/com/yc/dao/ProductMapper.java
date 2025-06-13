package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.web.model.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

// 商品表的映射接口
public interface ProductMapper extends BaseMapper<Product> {

    /*
        BaseMapper 是 MyBatis-Plus 提供的一个基础接口，包含了常用的 CRUD（创建、读取、更新、删除）操作方法
     */
    // 使用 MyBatis 注解进行自定义查询
    // 修改后的自定义 SQL 查询，确保字段与实体类对应
    // 查看最新商品
    @Select("SELECT product.pid, product.aid, product.pname, product.description, product.type, " +
            "product.firm, product.normprice, product.realprice, product.createtime, product.updatetime, " +
            "product.status " +
            "FROM product " +
            "INNER JOIN product_pic ON product.pid = product_pic.pid " +
            "ORDER BY product.createtime DESC " +
            "LIMIT 1")
    Product selectNewProducts();

    // 根据 pid 查询商品详细信息
    @Select("SELECT pid, aid, pname, description, type, firm, normprice, realprice, createtime, updatetime, status " +
            "FROM product WHERE pid = #{pid}")
    Product getProductDetails(Integer pid);

    @Select("SELECT pid, aid, pname, description, type, firm, normprice, realprice, createtime, updatetime, status " +
            "FROM product WHERE type = #{type} AND status = 1")
    List<Product> DetailList(String type);


    // 根据热度查询热门商品
    @Select("""
    SELECT p.* 
    FROM product p
    JOIN (
        SELECT od.pid 
        FROM order_detail od
        JOIN orders o ON od.order_id = o.order_id  
        WHERE o.ordertime >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
        GROUP BY od.pid 
        ORDER BY SUM(od.amount) DESC 
        LIMIT 8
    ) AS Tpid ON p.pid = Tpid.pid
    WHERE p.status = 1
    """)
    List<Product> getHotProducts();


    // 统计每种商品的销售数量
    @Select("select p.type AS name,sum(od.amount) as value from product p  join order_detail od on p.pid = od.pid group by  p.type")
    List<Map<String,Object>> getProdcutTotalCountByType();

    // 统计商品数量
    @Select("select count(*) from product")
    long getProductCount();

}
