package com.yc.web.controllers;

import com.yc.bean.ResponseResult;
import com.yc.bean.User;
import com.yc.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/security")
public class AuserController {

    @Autowired
    private UserMapper userMapper;

    //根据uid查询用户
    @GetMapping("/getUser")
    public ResponseResult getUser(Integer uid){
        User user = userMapper.selectById(uid);
        return ResponseResult.ok( "查询成功" ).setData( user );
    }

}
