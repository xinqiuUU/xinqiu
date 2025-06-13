package com.yc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Coupon;
import com.yc.bean.UserCoupon;
import com.yc.dao.CouponMapper;
import com.yc.dao.UserCouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
@Service
public class CouponService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private UserCouponMapper userCouponMapper;

    //定时发布优惠劵
    @Scheduled(cron = "0 0 0 * * ?") // 每天凌晨0点执行定时任务
    public void publishCoupon() {
        // 生成3折到7折之间的随机折扣
        int discount = ThreadLocalRandom.current().nextInt(3, 8); // 生成 [3, 7] 之间的随机数

        // 创建新的优惠券
        Coupon coupon = new Coupon();
        coupon.setCode("XINQIU");
        coupon.setType("折扣券");
        coupon.setValue(discount + "折"); // 随机折扣 3~7 折

        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 设置开始时间为当天的 00:00:00
        LocalDateTime startDate = today.atStartOfDay(); // 00:00:00
        // 设置结束时间为当天的 23:59:59
        LocalDateTime endDate = today.atTime(23, 59, 59); // 23:59:59
        // 格式化时间为字符串（如果需要）
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        coupon.setStart_date(startDate.format(formatter)); // 开始时间
        coupon.setEnd_date(endDate.format(formatter)); // 结束时间
        coupon.setQuantity(5); // 库存数量
        // 插入数据库
        couponMapper.insert(coupon);
        // 获取优惠券ID
        Integer cid = coupon.getCid();
        String couponKey = "coupon:stock:" + cid;
        String currentCouponKey = "coupon:current"; // 当前抢券的优惠券ID
        // 初始化库存到 Redis
        redisTemplate.opsForValue().set(couponKey, 5); // 初始化库存 5 张
        // 计算从现在到当天23:59:59的剩余秒数
        LocalDateTime now = LocalDateTime.now();
        long secondsUntilEndOfDay = ChronoUnit.SECONDS.between(now, endDate);
        // 设置Redis中过期时间为当天23:59:59
        redisTemplate.expire(couponKey, secondsUntilEndOfDay, TimeUnit.SECONDS);
        // 设置当前抢券的优惠券ID，并设置相同的过期时间
        redisTemplate.opsForValue().set(currentCouponKey, cid, secondsUntilEndOfDay, TimeUnit.SECONDS);
    }

    // 商家发布优惠券，初始化库存 cid 优惠券ID，totalStock 库存数量
    public void publishCoupon(Integer cid, Integer totalStock) {
        String couponKey = "coupon:stock:" + cid;
        String currentCouponKey = "coupon:current"; // 当前抢券的优惠券ID
        redisTemplate.opsForValue().set(couponKey, totalStock);
        // 设置优惠券24小时有效
        redisTemplate.expire(couponKey, 1, TimeUnit.DAYS);// 24小时后过期
        // 初始化当前抢券的优惠券ID
        redisTemplate.opsForValue().set(currentCouponKey, cid , 1, TimeUnit.DAYS);// 24小时后过期
    }

    // 查询库存
    public Integer getCouponStock(Integer cid) {
        String couponKey = "coupon:stock:" + cid;
        return (Integer) redisTemplate.opsForValue().get(couponKey);
    }

    // 抢券操作，用户不指定优惠券ID
    public String grabCoupon(Integer uid) {
        String couponKey = "coupon:stock:" + redisTemplate.opsForValue().get("coupon:current");  // 当前优惠券库存
        String userKey = "coupon:user:" + uid;  // 用户抢券记录
        String currentCouponKey = "coupon:current"; // 当前抢券的优惠券ID

        // 判断用户是否已经抢过
        Boolean alreadyGrabbed = redisTemplate.hasKey(userKey);
        if (Boolean.TRUE.equals(alreadyGrabbed)) {
            return "您已经抢过优惠券";
        }

        // 判断是否还有库存
        Integer stock = (Integer) redisTemplate.opsForValue().get(couponKey);
        if (stock != null && stock > 0) {
            // 获取当前优惠券ID
            Integer cid = (Integer) redisTemplate.opsForValue().get(currentCouponKey);
            if (cid == null) {
                return "未找到有效的优惠券";
            }

            // 使用分布式锁防止超卖
            String lockKey = "lock:coupon";
            // 尝试获取锁 10秒超时 防止死锁
            Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, uid, 10, TimeUnit.SECONDS);
            if (Boolean.TRUE.equals(lock)) {
                try {
                    // 扣减库存
                    Long newStock = redisTemplate.opsForValue().decrement(couponKey);
                    if (newStock >= 0) {
                        // 抢券成功，记录用户信息
                        redisTemplate.opsForValue().set(userKey, true, 1, TimeUnit.DAYS); // 记录一天
                        // 记录到数据库
                        UserCoupon userCoupon = new UserCoupon();
                        userCoupon.setUid(uid);
                        userCoupon.setCid(cid); // 使用从Redis获取的优惠券ID
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date currentDate = new Date();
                        userCoupon.setGet_date(sdf.format(currentDate)); // 设置领取时间为当前时间
                        // 保存用户优惠券记录到数据库
                        userCouponMapper.insert(userCoupon);
                        return "抢券成功，您的优惠券已发放";
                    } else {
                        return "优惠券已抢完";
                    }
                } finally {
                    // 释放锁
                    redisTemplate.delete(lockKey);
                }
            } else {
                return "系统繁忙，请稍后再试";
            }
        }
        return "优惠券已抢完";
    }

    // 领取 每日一卷的优惠券  code 优惠券码 为  xinqiu
    public String  getOneCoupon(Integer uid){
        String couponCode = "xinqiu"; // 优惠券码

        QueryWrapper<Coupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", couponCode);
        Coupon coupon = couponMapper.selectOne(queryWrapper); // 从数据库中查找优惠券
        if (coupon == null) {
            return "优惠券不存在";
        }
        // 检查用户今天是否已领取过该优惠券
        List<UserCoupon> userCoupons = userCouponMapper.findTodayUserCouponsByUidAndCoupon(uid, coupon.getCid());
        if (userCoupons != null && !userCoupons.isEmpty()) {
            return "您今天已经获得过优惠券";
        }

        if (coupon!= null) {
            UserCoupon userCoupon = new UserCoupon();
            userCoupon.setUid(uid);
            userCoupon.setCid(coupon.getCid()); // 使用从数据库获取的优惠券ID
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            userCoupon.setGet_date(sdf.format(currentDate)); // 设置领取时间为当前时间
            // 保存用户优惠券记录到数据库
            userCouponMapper.insert(userCoupon);
            return "恭喜您，获得优惠券";
        }
        return "hello coupon";
    }


}
