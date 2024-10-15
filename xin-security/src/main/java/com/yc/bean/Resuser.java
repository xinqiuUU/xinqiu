package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

//PO类 ： 与数据表相关
@TableName("user")
@Data
public class Resuser extends User  implements Serializable , UserDetails {

    @TableId(type = IdType.AUTO)
    private Integer uid;  //主键  userId 会转换为 user_id
    @TableField("uname")
    private String username;
    @TableField("pwd")
    private String password;  //**** UserDetail接口要求密码必须是 password  而不是 pwd
    @TableField("email")
    private String email;
    @TableField(exist = false)
    private String role="user";  //角色  前端传过来的 角色  是 字符串 ， 而数据库中 是 数字 ， 所以 要 转换 一下

    @Override  //用户所拥有的权限  返回的列表中至少得有一个值  否则这个用户啥权限都没有
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override  //返回用户的密码
    public String getPassword() {
        return this.password;
    }
    @Override  //返回用户的用户名
    public String getUsername() {
        return this.username;
    }

    @Override  //账户是否未过期  返回 true 账户未过期
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override  // 返回 true  账户未被锁定
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override  // 密码是否未过期   返回 true 密码未过期
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override //账户是否可用    返回 true 账户可用
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
