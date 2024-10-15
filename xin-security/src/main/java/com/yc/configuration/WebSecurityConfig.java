package com.yc.configuration;

import com.yc.service.ResuserBizImpl;
import com.yc.web.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 密码加密器
    }

    //ResuserBizImpl 是实现了 UserDetailsService 接口的类 是spring security 提供的一个接口 用于从数据库中获取用户信息
    private ResuserBizImpl resuserBizImpl;
    @Autowired
    public void setResuserBizImpl(ResuserBizImpl resuserBizImpl) {
        this.resuserBizImpl = resuserBizImpl;
    }

    @Bean  // 认证服务提供器
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService( resuserBizImpl ); // 组装一个 认证服务 实现了 UserDetailsService 接口的类
        provider.setPasswordEncoder( passwordEncoder() ); // 密码加密器
        return provider;
    }
    @Bean  // 认证管理器
    public AuthenticationManager authenticationManager( AuthenticationConfiguration configuration ) throws Exception {
        return configuration.getAuthenticationManager();
    }
    //security 的配置  通过过滤器实现 组件的配置
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity ) throws Exception {
        httpSecurity.formLogin( AbstractHttpConfigurer::disable)  //取消默认登录页面的使用  因为前后端分离的项目
                .logout(AbstractHttpConfigurer::disable)  // 取消默认登出页面的使用
                .authenticationProvider( authenticationProvider() ) //将自己配置的PasswordEncoder 注入到spring security中
                .csrf(AbstractHttpConfigurer::disable) // 禁用CSRF 跨站请求伪造 因为前后端分离的项目 不需要CSRF
                .sessionManagement( session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) ) // 禁用session 因为我们已经使用了jwt 所以不需要session
                .httpBasic(AbstractHttpConfigurer::disable) // 禁用httpBasic 因为我们传输数据用的是post 而且请求体是JSON
                .authorizeHttpRequests( request ->
                        request
                                .requestMatchers(HttpMethod.POST,"/security/login","/security/register" ,"/security/logout" ,"/security/emailLogin","/security/yzm","/security/admin/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/security/captcha","/security/getUser","/security/admin/**").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class); // 其他请求都需要认证  登录后才能访问
        // 将用户授权时用到的JWT校验过滤器添加进SecurityFilterChain中  UsernamePasswordAuthenticationFilter 过滤器之前
        return httpSecurity.build();
    }


}
