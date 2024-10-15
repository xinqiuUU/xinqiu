package com.yc.web.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Admin;
import com.yc.bean.AdminJsonModel;
import com.yc.dao.AdminMapper;
import com.yc.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


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
    public AdminJsonModel loginByPwd(String email, String pwd ,String picCode, HttpServletRequest req) {
        AdminJsonModel jm = new AdminJsonModel();

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("email", email);
        String encodedPassword = passwordEncoder.encode(pwd);
        log.info("加密后的密码：" + encodedPassword);
        Admin admin = adminMapper.selectOne(wrapper);
        boolean b = passwordEncoder.matches(pwd, admin.getPwd());
        admin.setPwd(null);
        String token = (String) req.getSession().getAttribute("code");
        if (b && picCode.equals(token)) {
            HttpSession session = req.getSession();
            session.setAttribute("adminSession", admin);
            session.removeAttribute("code");
            jm.setCode(0);// 成功
            jm.setData(admin);
        } else {
            jm.setCode(1);// 失败
            jm.setMsg("账户或密码错误或验证码错误");
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

    //密码登录生成的验证码
    @GetMapping("/code")
    public void code(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //post 请求中写业务功能
        //几板斧 哈哈
        //resp.setContentType("text/html;charset=utf-8");
        int width=120;
        int height=30;
        //创建一个内存中带缓存的图片对象
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        //获取2D画笔对象
        Graphics g=image.getGraphics();
        //给画笔对象设置颜色，为图片喷绘背景色
        g.setColor(Color.white);
        g.fillRect(0,0,width,height);
        //设置颜色，绘制图片边框
        g.setColor(Color.black);
        g.drawRect(1,1,width-2,height-2);


        //先使用一个常量字符串测试程序设计思路 String
        String yzm="";
        //想办法将yzm，在0-9和a-z、A-Z中随机组合4个字符的字符串  定义一个字符仓库
        String baseChar="0123";
        //随机数对象
        Random rd=new Random();
        for (int i = 0; i <4; i++) {
            int pos=rd.nextInt(baseChar.length());
            yzm=yzm+baseChar.charAt(pos);

        }
        g.setColor(Color.RED);  //红色画笔
        g.setFont(new Font("宋体",Font.ITALIC,20));
        g.drawString(yzm,15,22);  //绘制文字到画布
        //绘制一些干扰线
        /*
         * 在图片上画随机线条
         * @param g
         * */
        g.setColor(Color.blue);
        for (int i = 0; i < 10; i++) {
            int x1= rd.nextInt(width);
            int y1=rd.nextInt(height);
            int x2= rd.nextInt(width);
            int y2= rd.nextInt(height);
            g.drawLine(x1,y1,x2,y2);
        }

        //  ****关键地方****   把 验证码code 设置到  session 中 每个用户都可以有一个独特的会话 session
        HttpSession session=req.getSession();
        session.setAttribute("code",yzm);

        //给客户端设置MINE类型
        resp.setHeader("content-type","image/jpeg");   //******
        //使用响应对象的字节输出流，写到客户端浏览器
        //设置响应头控制浏览器不要缓存
        resp.setDateHeader("expries", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");

        ImageIO.write(image,"jpg",resp.getOutputStream());
    }


}
