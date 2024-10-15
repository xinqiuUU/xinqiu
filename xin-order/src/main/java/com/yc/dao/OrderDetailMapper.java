package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.web.model.OrderDetail;
import org.apache.ibatis.annotations.Insert;


public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    @Insert("INSERT INTO order_detail (order_id, pid, amount, smallprice) " +
            "VALUES (#{orderId}, #{pid}, #{amount}, #{smallPrice})")
    int insertOrderDetail(Integer orderId, Integer pid, Integer amount, Double smallPrice);

}
