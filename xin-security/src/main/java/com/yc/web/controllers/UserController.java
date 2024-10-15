package com.yc.web.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.aspects.CaptchaAspect;
import com.yc.bean.ResponseResult;
import com.yc.bean.Resuser;
import com.yc.bean.User;
import com.yc.dao.UserMapper;
import com.yc.service.ResuserBiz;
import com.yc.utils.JwtTokenUtil;
import com.yc.web.model.ResuserVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/security")
public class UserController {

    @Autowired
    private ResuserBiz resuserBiz;

    //注册
    @PostMapping("/register")
    public ResponseResult register(@RequestBody @Valid ResuserVO resuser){
        try{
            String email = this.resuserBiz.regUser( resuser );
            resuser.setEmail( email ); // 注册成功后，将用户id设置到resuser对象中
            resuser.setPassword( "" ); // 注册成功后，将密码设置为""
            return ResponseResult.ok( "注册成功" ).setData( resuser );
        }catch (Exception e){
            log.error( "注册失败", e );
            return ResponseResult.error( e.getMessage() );
        }
    }

    // 注入 认证管理器
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtUtil;
    @Autowired
    private CaptchaAspect captchaAspect;

    //邮箱密码登录
    @PostMapping("/login")
    public ResponseResult login(@RequestBody ResuserVO resuserVO , HttpSession session){
        try {
            // 用户认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(resuserVO.getEmail(), resuserVO.getPassword()));

            // 将认证信息设置到 SecurityContext 中
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 认证成功后，获取用户信息
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 生成JWT
            Map<String, String> payload = new HashMap<>();
            payload.put( "email", ((Resuser)userDetails).getEmail() );
            payload.put("roles", userDetails.getAuthorities().toString());
            payload.put( "uid", String.valueOf(((Resuser) userDetails).getUid()));
            payload.put( "uname", ((Resuser) userDetails).getUname() );

            String jwtToken = jwtUtil.encodeJWT(payload);

            return ResponseResult.ok("登录成功").setData(jwtToken);
        } catch (AuthenticationException e) {
            return ResponseResult.error("登录失败:邮箱或密码错误");
        }
    }

    private UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    //邮件登录
    @PostMapping("/emailLogin")
    public ResponseResult emailLogin(@RequestBody ResuserVO resuserVO , HttpSession session){
        //获取验证码
        String captcha = String.valueOf( session.getAttribute( "captcha" ) ) ;
        String email = String.valueOf( session.getAttribute( "email" ) ) ;

        //验证码校验  验证码  忽略大小写
        if(!captcha.equals( resuserVO.getCaptcha() ) || !email.equals( resuserVO.getEmail() ) ){
            return ResponseResult.error( "验证码或邮箱错误" );
        }
        session.removeAttribute( "captcha" );
        session.removeAttribute( "email" );
        //用户登录后 取消定时器  防止验证码被重复使用
        captchaAspect.cancelCaptchaRemoval();
        //生成jwt负载 JSON Web Token
        Map<String,String> payload = new HashMap<>();
        payload.put("email", resuserVO.getEmail());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq( "email", resuserVO.getEmail() );
        User user = userMapper.selectOne( wrapper );
        payload.put( "uid", user.getUid().toString());
        //生成token
        String jwtToken = jwtUtil.encodeJWT( payload );
        return ResponseResult.ok( "登录成功" ).setData( jwtToken );
    }

    //退出
    @PostMapping("/logout")
    public ResponseResult logout(@RequestHeader("token") String token){
        //这里可以实现JWT 黑名单机制  或者让客户端删除存储的JWT
        log.info( token );
        //例如 将token 添加到redis黑名单中
        return ResponseResult.ok( "退出成功" );
    }

    //权限认证  只有登录成功后才能访问该接口
    @PostMapping("/checkLogin")
    public ResponseResult check(){
        log.info( "权限认证成功" );
        // 从 SecurityContext 中获取当前认证的用户信息。
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPwd( "" );
        return ResponseResult.ok( "成功" ).setData( user );
    }


}
