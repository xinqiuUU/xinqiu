package com.yc.service;

import com.yc.bean.Address;
import com.yc.bean.model.JsonModel;
import jakarta.servlet.http.HttpServletRequest;

public interface AddressBiz {

    //获取默认地址
    public JsonModel getmodifyDefaultAddress(HttpServletRequest request);
    //设置  默认地址
    public JsonModel modifyDefaultAddress(String addr_id, HttpServletRequest request);
    //删除已有地址
    public JsonModel deleteAddress(String addr_id, HttpServletRequest request);
    //获取保存的地址
    public JsonModel getSaveAddress( HttpServletRequest request);
    // 保存地址
    public JsonModel saveAddress(Address address, HttpServletRequest request);
}
