package com.yc.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class JwtTokenUtil {

    private String key = "abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcacab";

    public static final long EXPIRE = 1000 * 60 * 60 * 24 ;// 测试2分钟  24小时 1000 * 60 * 60 * 24

    // 生成token
    public String encodeJWT(Map paloadMap){
        //1. 定义header部分内容
        Map headerMap = new HashMap();
        headerMap.put("alg", SignatureAlgorithm.HS256.getValue());
        headerMap.put("typ", "JWT");

        Date edate = new Date( System.currentTimeMillis() + EXPIRE); // 过期时间
        paloadMap.put("expiration", edate); // 将过期时间放入载荷中

        //3. 生成token
        String jwtToken = Jwts.builder()
                .setHeaderParams(headerMap)  // 头部
                .setClaims(paloadMap)  // 载荷
                .setIssuedAt(new Date())  // token签发时间
                .setExpiration( edate )   // 过期时间
                .signWith(SignatureAlgorithm.HS256, key)
                .compact(); // 拼接header + payload
        return jwtToken;
    }

    // 解密token 解密JWT（JSON Web Token），并从中提取声明（Claims
    public Claims decodeJWTWithkey(String jwtToken){
        Claims claims = Jwts.parser()  // 解析JWT
               .setSigningKey(key)  // 指定盐
               .parseClaimsJws(jwtToken)
               .getBody();  // 提取载荷
        return claims;
    }

    // 生成token 加密
    //其中key是盐  jsonwebtoken的jar包规定，key必须字节数要大于你所用的加密算法的最小字节数
    // 比如HS256算法的最小字节数是256字节，所以key的字节数要大于256字节
    public static String encodeJWT(String key){
        //1. 定义header部分内容
        Map headerMap = new HashMap();
        headerMap.put("alg", SignatureAlgorithm.HS256.getValue());
        headerMap.put("typ", "JWT");
        //2. 定义payload部分内容
        Map paloadMap = new HashMap();
        paloadMap.put("sub", "测试jwt生成token");
        paloadMap.put("iat", UUID.randomUUID());
        //失效时间  1毫秒   1000 * 60 * 60 * 24过期时间24小时
        paloadMap.put("exp", System.currentTimeMillis() + 1);
        paloadMap.put("name", "张三");
        paloadMap.put("role", "user");

        //3. 生成token
        String jwtToken = Jwts.builder()
               .setHeaderParams(headerMap)
                .setClaims(paloadMap)
               .signWith(SignatureAlgorithm.HS256, key)  //指定加密算法和盐
               .compact(); // 拼接header + payload
        return jwtToken;
    }

    // 解密token
    public  static  void decodeJWT(String jwtToken, String key){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwtToken)
                    .getBody();
            Object sub = claims.get("sub");
            Object name = claims.get("name");
            Object role = claims.get("role");
            Object exp = claims.get("exp");
            Object iat = claims.get("iat");
            System.out.println("sub:"+ sub + " \nname:"+ name + "\nrole:"+ role + "\nexp:"+ exp
                    + "\niat:"+ iat
                    + "\n失效了没？" + (System.currentTimeMillis() > (Long)exp) + "");
        }catch ( Exception e){
            e.printStackTrace();
            System.out.println("token失效了");
        }
    }

    // 验证token是否过期
    public boolean isTokenExpired(String jwtToken) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            log.error("Jwt is empty");
            return true;
        }
        try {
            //提取过期时间 从 JWT（JSON Web Token）中提取过期时间，并将其转换为 Date 对象
            Date time = new Date( Long.parseLong(decodeJWTWithkey(jwtToken).get("expiration").toString()) );
            // 过期时间是否在当前时间之前 是返回true(过期) 否返回false(未过期)
            log.info("过期时间:" + time);
            log.info("当前时间:" + new Date());
            return time.before(new Date());
        } catch (Exception e) {
            log.error(e.getMessage());
            return true;
        }
    }
}
