package com.yc.web.interceptors;

import com.yc.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //这个方法在请求处理之前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //      是否为空                                       是否过期
        if ( token != null && !token.isEmpty() && !jwtTokenUtil.isTokenExpired(token)){
            //验证token是否过期
            try{
                //取出 token中的负载部分
                Map<String,Object> claims = jwtTokenUtil.decodeJWTWithkey( token );
                //将 负载部分放入 request中 供后续controller使用
                request.setAttribute("userClaims",claims);
                return true;
            }catch (Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED ); // 401 未授权
                return false;
            }
        }
        response.setStatus( HttpServletResponse.SC_UNAUTHORIZED ); // 401 未授权
        return false;
    }


}
