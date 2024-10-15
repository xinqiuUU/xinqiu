package com.yc.web.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Admin;
import com.yc.bean.AdminJsonModel;
import com.yc.dao.AdminMapper;
import com.yc.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/security/admin")
public class AdminController {


    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private JwtTokenUtil jwtUtil;
    // 自动注入  Spring Security中的PasswordEncoder接口来加密用户密码
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //按密码登录
    @PostMapping("/loginByPwd")
    public AdminJsonModel loginByPwd(String email, String pwd , HttpServletRequest req) {
        AdminJsonModel jm = new AdminJsonModel();

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("email", email);
        String encodedPassword = passwordEncoder.encode(pwd);
        log.info("加密后的密码：" + encodedPassword);
        Admin admin = adminMapper.selectOne(wrapper);
        boolean b = passwordEncoder.matches(pwd, admin.getPwd());
        admin.setPwd(null);
        if (b) {
            HttpSession session = req.getSession();
            session.setAttribute("adminSession", admin);
            jm.setCode(0);// 成功
            jm.setData(admin);
        } else {
            jm.setCode(1);// 失败
            jm.setMsg("管理员登录失败");
        }
        return jm;
    }
    //退出登录
    @PostMapping("/logout")
    public AdminJsonModel logout( HttpServletRequest req ) {
        AdminJsonModel jm = new AdminJsonModel();
        req.getSession().removeAttribute("adminSession");
        jm.setCode(0);// 成功
        jm.setMsg("管理员退出成功");
        return jm;
    }

    //获取管理员信息
    @PostMapping("/getInfo")
    public AdminJsonModel getInfo(String aid) {
        AdminJsonModel jm = new AdminJsonModel();
        Admin admin = adminMapper.selectById(aid);
        admin.setPwd(null);
        jm.setCode(0);// 成功
        jm.setData(admin);
        jm.setMsg("管理员查询成功");
        return jm;
    }


}
