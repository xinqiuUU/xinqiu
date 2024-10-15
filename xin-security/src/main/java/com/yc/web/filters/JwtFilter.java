package com.yc.web.filters;


import com.yc.bean.Resuser;
import com.yc.service.ResuserBiz;
import com.yc.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//此过滤器作用: 1. 是否有token  2. token是否有效 3.验证用户名和密码是否匹配
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtUtils;

    @Autowired
    private ResuserBiz resuserBiz;

    //Spring Security 过滤器的实现，用于处理 JWT（JSON Web Token）的验证
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //每次请求头中都要携带token  验证token是否有效
        String jwtTaken = request.getHeader("token"); // 从请求头中获取token
        if(jwtTaken != null && !jwtTaken.isEmpty() && !jwtUtils.isTokenExpired(jwtTaken)){
            try{  // token 有效
                //获取载荷  验证用户名和密码是否匹配
                //就是一个键值对的map  载荷中包含了用户名和密码、过期时间、签名等信息
                Claims claims = jwtUtils.decodeJWTWithkey(jwtTaken);
                String email = (String) claims.get("email"); // 从token中获取用户名
                //从数据库中获取用户信息
                UserDetails user = ((UserDetailsService)resuserBiz).loadUserByUsername(email);
                if ( user != null){
                    //验证密码与权限是否正确
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            user , null , user.getAuthorities()
                    );
                    Resuser resuser = (Resuser) user;
                    resuser.setPassword(""); // 密码设置为""
                    auth.setDetails(resuser);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }else {
            log.warn("token为空或已过期或超时，可能未登录");
        }
        filterChain.doFilter(request,response); //继续执行过滤器链
    }
}
