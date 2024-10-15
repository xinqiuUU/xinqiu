package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.UserCoupon;
import com.yc.web.model.UserCouponDetailsDTO;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCouponMapper extends BaseMapper<UserCoupon> {
    // 查询用户今天领取指定优惠券的记录
    @Select("SELECT * FROM user_coupon " +
            "WHERE uid = #{uid} AND cid = #{cid} AND DATE(get_date) = DATE(NOW())")
    List<UserCoupon> findTodayUserCouponsByUidAndCoupon(@Param("uid") Integer uid, @Param("cid") Integer cid);

    //查询用户优惠券的详细信息  删除为 0  默认可用 1 已使用 2 已失效 3
    @Select("SELECT uc.id, uc.uid, c.cid, c.code, c.type, c.value, c.start_date, c.end_date, uc.status, uc.get_date " +
            "FROM user_coupon uc " +
            "JOIN coupon c ON uc.cid = c.cid " +
            "WHERE uc.uid = #{uid} AND uc.status >= 1")
    List<UserCouponDetailsDTO> getUserCouponsWithDetails(@Param("uid") Integer uid);
}
