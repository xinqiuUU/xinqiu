package com.yc.api;

import com.yc.bean.UserCoupon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "xin-coupon") // 抢卷服务 xin-coupon 服务名
public interface CouponApi {


    // 根据用户uid和优惠券cid查询优惠券
    @GetMapping("/coupon/getUserCouponByUidAndCid")
    UserCoupon getUserCouponByUidAndCid(@RequestHeader("token") String token,
                                        @RequestParam("uid") Integer uid,
                                        @RequestParam("cid") Integer cid);

    // 使用优惠券
    @PostMapping("/coupon/exchangeCoupon")
    String exchangeCoupon(@RequestHeader("token") String token,
                          @RequestParam("uid") Integer uid,
                          @RequestParam("id") Integer id,
                          @RequestParam("totalPrice") Double totalPrice);

    // 退款优惠券
    @PostMapping("/coupon/xin/refundCoupon")
    int refundCoupon(@RequestParam("id") Integer id);
}
