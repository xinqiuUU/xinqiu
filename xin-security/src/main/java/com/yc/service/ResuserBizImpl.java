package com.yc.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Resuser;
import com.yc.dao.ResuserMapper;
import com.yc.web.model.ResuserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Service
public class ResuserBizImpl implements ResuserBiz , UserDetailsService {

    private ResuserMapper resuserMapper;
    @Autowired   //利用set装配注入可以防止 ， 循环依赖问题
    public void setResuserBiz(ResuserMapper resuserMapper) {
        this.resuserMapper = resuserMapper;
    }

    // 自动注入  Spring Security中的PasswordEncoder接口来加密用户密码
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // 注册
    @Transactional
    @Override
    public String regUser(ResuserVO resuserVO) {
        //判断用户是否存在
        QueryWrapper<Resuser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", resuserVO.getEmail());
        List<Resuser> list = resuserMapper.selectList( queryWrapper );
        if(list.size() > 0){
            throw new RuntimeException("用户已存在");
        }
        Resuser resuser = new Resuser();
        resuser.setEmail( resuserVO.getEmail() );
        //对明文密码进行加密
        resuser.setPassword( passwordEncoder.encode( resuserVO.getPassword() ) );
        resuser.setEmail( resuserVO.getEmail() );
        resuserMapper.insert( resuser );
        return resuser.getEmail();
    }
    //用于从数据库中加载用户信息
    //loadUserByUsername(String username) 是 UserDetailsService 接口中的一个方法，Spring Security 在用户认证过程中会调用它
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LambdaQueryWrapper<Resuser> queryWrapper = new LambdaQueryWrapper<>();
        // queryWrapper.eq("email", resuserVO.getEmail());
        // 避免硬编码 Resuser::getEmail  ， 避免硬编码
        queryWrapper.eq( Resuser::getEmail, email );  //where email ='a'
        try {
            //这是一个userDetails对象
            // **** select * from resuser where username = ?  这里要确保 用户名唯一
            Resuser user = resuserMapper.selectOne( queryWrapper );
            return user;
        }catch (Exception e){
            log.error("email not found");
            return null;
        }

    }
}
