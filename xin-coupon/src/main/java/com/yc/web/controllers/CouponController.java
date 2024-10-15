package com.yc.web.controllers;

import com.yc.bean.UserCoupon;
import com.yc.bean.model.JsonModel;
import com.yc.service.CouponService;
import com.yc.service.UserCouponService;
import com.yc.web.model.UserCouponDetailsDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;
    @Autowired
    private UserCouponService userCouponService;
    //每日一卷的优惠券
    @RequestMapping("/getOneCoupon")
    public String getOneCoupon(Integer uid) {
        String result = couponService.getOneCoupon(uid);
        return result;
    }

    //****发布优惠券，初始化库存 cid 优惠券ID，totalStock 库存数量
    @RequestMapping("/admin/publishCoupon")
    public String publishCoupon() {
        couponService.publishCoupon();
        return "发布成功";
    }
    //****测试抢券操作，用户不指定优惠券ID
    @RequestMapping("/xin/grabCoupon")
    public String grabCoupon( Integer uid ) {
        // 拦截器已经将用户信息放入request中 取出即可
//        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
//        String uid = userClaims.get("uid").toString();
        String result = couponService.grabCoupon(Integer.valueOf(uid));
        return result;
    }

    // 抢券操作，用户不指定优惠券ID
    @RequestMapping("/grabCoupon")
    public String grabCoupon( HttpServletRequest request ) {
        // 拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();
        String result = couponService.grabCoupon(Integer.valueOf(uid));
        return result;
    }

    //查询用户优惠券
    @RequestMapping("/getCoupon")
    public JsonModel getCoupon(HttpServletRequest request) {
        // 拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();
        List<UserCouponDetailsDTO> list = userCouponService.getUserCoupon(Integer.valueOf(uid));
        JsonModel jm = new JsonModel();
        jm.setCode(1);
        jm.setObj(list);
        return jm;
    }

    //根据用户uid和优惠劵cid查询优惠券
    @RequestMapping("/getUserCouponByUidAndCid")
    public UserCoupon getUserCouponByUidAndCid(Integer uid, Integer cid) {
        return userCouponService.getUserCouponByUidAndCid(uid, cid);
    }

    // 使用优惠券
    @RequestMapping("/exchangeCoupon")
    public String exchangeCoupon(Integer uid, Integer id ,Double totalPrice) {
        String result = userCouponService.exchangeCoupon(uid, id ,totalPrice);
        return result;
    }
    //删除优惠劵
    @RequestMapping("/deleteCoupon")
    public JsonModel deleteCoupon(Integer id) {
        int result = userCouponService.deleteCoupon(id);
        JsonModel jm = new JsonModel();
        if (result <= 0){
            jm.setCode(0);
            jm.setObj("删除失败");
        }else {
            jm.setCode(1);
            jm.setObj("删除成功");
        }
        return jm;
    }
    // 检查优惠券是否过期
    @RequestMapping("/checkCoupon")
    public JsonModel checkCoupon(Integer cid) {
        JsonModel jm = new JsonModel();
        int result = userCouponService.checkCoupon(cid);
        if (result == 0) {
            jm.setCode(0);
            jm.setObj("优惠券已过期");
        } else {
            jm.setCode(1);
        }
        return jm;
    }
    //退款后把优惠劵的状态改为可用
    @RequestMapping("/xin/refundCoupon")
    public int refundCoupon(Integer id) {
        return userCouponService.refundCoupon(id);
    }
}
