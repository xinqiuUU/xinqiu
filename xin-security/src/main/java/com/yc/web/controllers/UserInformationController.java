package com.yc.web.controllers;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yc.bean.User;
import com.yc.bean.model.JsonModel;
import com.yc.dao.UserMapper;
import com.yc.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/security")
public class UserInformationController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserMapper userMapper;
    /**
     * 修改用户的头像
     * @param file 头像文件
     */
    @PostMapping("/changeAvatar")
    public JsonModel changeAvatar(@RequestPart MultipartFile file ,String uid) {
        JsonModel jm = new JsonModel();
        try {
            fileService.upload(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = "https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/" + file.getOriginalFilename();
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("uid", uid);
        updateWrapper.set("head_photo", url);
        userMapper.update(updateWrapper);
        jm.setCode(1);
        jm.setObj(url);
        return jm;
    }

    //  修改用户信息
    @PostMapping("/alterInformation")
    public JsonModel alterInformation( @RequestParam Integer uid ,@RequestParam String uname, @RequestParam String tel, @RequestParam String email) {
        JsonModel jm = new JsonModel();
        User user = new User();
        // 更新用户信息
        user.setUid(uid);
        user.setUname(uname);
        user.setTel(tel);
        user.setEmail(email);

        // 使用 MyBatis-Plus 的 update 方法
        int result = userMapper.updateById(user); // 假设 userMapper 是 MyBatis-Plus 的 mapper

        if (result > 0) {
            jm.setCode(1);
            jm.setObj("修改成功");
        } else {
            jm.setCode(0);
            jm.setError("修改失败");
        }
        return jm;
    }
    // 自动注入  Spring Security中的PasswordEncoder接口来加密用户密码
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    /*
     * 修改密码
     */
    @PostMapping("/alterPassword")
    public JsonModel alterPassword(@RequestParam Integer uid , @RequestParam String pwd, HttpServletRequest req) {
        JsonModel jm = new JsonModel();
        //对明文密码进行加密
        String password =   passwordEncoder.encode(  pwd ) ;
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("uid", uid);
        updateWrapper.set("pwd", password);
        // 更新用户密码
        int result = userMapper.update(updateWrapper);
        if (result > 0) {
            jm.setCode(1);
            jm.setObj("修改成功");
        } else {
            jm.setCode(0);
            jm.setError("修改失败");
        }
        return jm;
    }

}
