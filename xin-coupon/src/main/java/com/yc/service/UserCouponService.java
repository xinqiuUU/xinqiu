package com.yc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yc.bean.Coupon;
import com.yc.bean.User;
import com.yc.bean.UserCoupon;
import com.yc.dao.CouponMapper;
import com.yc.dao.UserCouponMapper;
import com.yc.web.model.UserCouponDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserCouponService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private UserCouponMapper userCouponMapper;
    //获取用户的优惠券
    public List<UserCouponDetailsDTO> getUserCoupon(Integer uid){
        List<UserCouponDetailsDTO> couponDetails = userCouponMapper.getUserCouponsWithDetails(uid);
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 判断是否过期
        for (UserCouponDetailsDTO couponDetail : couponDetails) {
            LocalDateTime now = LocalDateTime.now();
            // 将字符串转换为 LocalDateTime
            LocalDateTime endDate = LocalDateTime.parse(couponDetail.getEnd_date(), formatter);

            // 比较当前时间与结束时间
            if (now.isAfter(endDate)) {
                UpdateWrapper updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", couponDetail.getId());
                updateWrapper.set("status", 3); // 已失效
                userCouponMapper.update(updateWrapper); // 更新数据库中的状态
                couponDetail.setStatus(3); // 更新对象中的状态
            }
        }
        return couponDetails;
    }
    //根据用户uid和优惠劵cid查询优惠券
    public UserCoupon getUserCouponByUidAndCid(Integer uid, Integer cid) {
        QueryWrapper<UserCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid).eq("cid", cid);
        return userCouponMapper.selectOne(queryWrapper);
    }

    //使用优惠劵
    public String exchangeCoupon(Integer uid, Integer id , Double totalPrice) {
        // 检查用户是否已经使用过该优惠券
        QueryWrapper<UserCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid).eq("id", id).eq("status", 1);//1为可用
        UserCoupon userCoupon = userCouponMapper.selectOne(queryWrapper);
        // 检查用户是否已经使用过该优惠券
        if (userCoupon == null) { // 不可用
            BigDecimal bd = new BigDecimal(totalPrice);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            return String.valueOf(bd.doubleValue()); // 不打折
        }else { // 1 可用
            Coupon coupon = couponMapper.selectById(userCoupon.getCid());
            BigDecimal bd = new BigDecimal(totalPrice);
            // 去掉"折"字，保留数字部分
            String discountStr = coupon.getValue().replaceAll("[^\\d]", "");
            BigDecimal discount = new BigDecimal(discountStr).divide(new BigDecimal(10)); // 8 折转换为 0.8
            bd = bd.multiply(discount); // 计算折扣后的价格
            // 保留两位小数，四舍五入
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            UpdateWrapper updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", userCoupon.getId());
            updateWrapper.set("status", 2); // 已使用
            userCouponMapper.update( updateWrapper ); // 已使用
            return String.valueOf(bd.doubleValue()); // 打折
        }
    }
    //用户删除优惠券
    public int deleteCoupon(Integer id) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("status", 0); // 已删除
        return  userCouponMapper.update( updateWrapper ); // 已删除
    }
    //判断用户优惠劵是否过期
    public int checkCoupon(Integer cid) {
        Coupon coupon = couponMapper.selectById(cid);
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        // 将字符串转换为 LocalDateTime
        LocalDateTime endDate = LocalDateTime.parse(coupon.getEnd_date(), formatter);
        if (now.isAfter(endDate)) {
            UpdateWrapper updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", cid);
            updateWrapper.set("status", 3); // 已失效
            couponMapper.update(updateWrapper); // 更新数据库中的状态
            return 0; // 已过期
        } else {
            return 1; // 未过期
        }
    }
    // 退款后把优惠劵的状态改为可用
    public int refundCoupon(Integer id) {
        UserCoupon userCoupon = userCouponMapper.selectById(id);
        int cid = userCoupon.getCid();
        Coupon coupon = couponMapper.selectById(cid);
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        // 将字符串转换为 LocalDateTime
        LocalDateTime endDate = LocalDateTime.parse(coupon.getEnd_date(), formatter);

        if (now.isAfter(endDate)) { // 已过期
            UpdateWrapper updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            updateWrapper.set("status", 3); // 已失效
            userCouponMapper.update(updateWrapper); // 更新状态为已失效
            return 0; // 已过期
        } else {
            UpdateWrapper updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            updateWrapper.set("status", 1); // 改为可用
            userCouponMapper.update(updateWrapper); // 更新状态为可用
            return 1; // 未过期
        }
    }

}
