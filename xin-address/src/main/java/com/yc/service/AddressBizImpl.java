package com.yc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yc.bean.Address;
import com.yc.bean.model.JsonModel;
import com.yc.dao.AddressMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Transactional
@Service
public class AddressBizImpl implements AddressBiz{

    // 注入AddressBizImpl的实现类
    private AddressMapper addressMapper;
    @Autowired
    public void setAddressBiz(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }
    //  获取 默认地址  根据 uid 从缓存中获取
//    @Cacheable(value = "defaultAddress", key = "#request.getAttribute('userClaims').get('uid')") // 根据用户ID缓存
    @Override
    public JsonModel getmodifyDefaultAddress(HttpServletRequest request) {
        JsonModel jm = new JsonModel();
        //拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();
        QueryWrapper<Address> qw = new QueryWrapper<>();
        qw.eq("uid", uid).eq("status", 1);
        Address address = addressMapper.selectOne(qw);
        if (address == null){
            jm.setCode(0);
            jm.setObj("获取默认地址失败");
            return jm;
        }else {
            jm.setCode(1);
            jm.setObj(address);
            return jm;
        }
    }
    // 设置 默认地址 更新 uid 缓存
    @Override
//    @CachePut(value = "defaultAddress", key = "#request.getAttribute('userClaims').get('uid')") // 更新缓存
    public JsonModel modifyDefaultAddress(String addr_id, HttpServletRequest request) {
        JsonModel jm = new JsonModel();
        //拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();
        UpdateWrapper<Address> qw = new UpdateWrapper<>();
        qw.eq("uid", uid).set("status", 0);
        addressMapper.update(null, qw);
        UpdateWrapper<Address> qw2 = new UpdateWrapper<>();
        qw2.eq("uid", uid).eq("addr_id", addr_id).set("status", 1);
        int result = addressMapper.update(null, qw2);
        if (result > 0) {
            jm.setCode(1);
            jm.setObj("设置默认地址成功");
        } else {
            jm.setCode(0);
            jm.setObj("设置默认地址失败");
        }
        return jm;
    }

    // 逻辑删除地址 ，清除 uid 缓存
    @Override
//    @CacheEvict(value = "defaultAddress", key = "#request.getAttribute('userClaims').get('uid')") // 清除缓存
    public JsonModel deleteAddress(String addr_id, HttpServletRequest request) {
        JsonModel jm = new JsonModel();
        //拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();
        QueryWrapper<Address> qw = new QueryWrapper<>();
        qw.eq("uid", uid);
        int result = addressMapper.deleteById(addr_id);
        if (result > 0) {
            jm.setCode(1);
            jm.setObj("删除地址成功");
        } else {
            jm.setCode(0);
            jm.setObj("删除地址失败");
        }
        return jm;
    }

    //获取所有地址
    @Override
    public JsonModel getSaveAddress( HttpServletRequest request) {
        JsonModel jm = new JsonModel();
        //拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();
        QueryWrapper<Address> qw = new QueryWrapper<>();
        qw.eq("uid", uid);
        List<Address> addressList = addressMapper.selectList(qw);
        jm.setCode(1);
        jm.setObj(addressList);
        return jm;
    }

    // 保存地址
    @Override
//    @CacheEvict(value = "defaultAddress", key = "#address.uid") // 清除缓存
    public JsonModel saveAddress(Address address, HttpServletRequest request) {
        JsonModel jm = new JsonModel();
        //拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();
        address.setUid( Integer.valueOf(uid) );
        // 定义邮箱的正则表达式
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(emailPattern);
        // 创建匹配器对象
        Matcher matcher = pattern.matcher(address.getEmail()); // 检查邮箱地址是否匹配正则表达式
        if (!matcher.matches()) {
            jm.setCode(0);
            jm.setObj("邮箱格式不正确");
            return jm;
        }
        // 定义电话号码的正则表达式（这里只是一个示例，可以根据具体需求调整）
        String phonePattern = "^\\+?[0-9. ()-]{7,}$";
        // 编译正则表达式
        pattern = Pattern.compile(phonePattern);
        // 创建匹配器对象
        matcher = pattern.matcher(address.getTel());
        if (!matcher.matches()) {
            jm.setCode(0);
            jm.setObj("电话号码格式不正确");
            return jm;
        }
        if (address.getStatus() == 1){
            UpdateWrapper<Address> qw = new UpdateWrapper<>();
            qw.eq("uid", uid).set("status", 0);
            addressMapper.update(null, qw);
        }
        int result = addressMapper.insert(address);
        if (result > 0) {
            jm.setCode(1);
            jm.setObj("保存地址成功");
        } else {
            jm.setCode(0);
            jm.setObj("保存地址失败");
        }
        return jm; // 返回JsonModel
    }
}
