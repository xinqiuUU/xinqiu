package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.Menu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    @Select("SELECT m.* FROM menu m " +
            "JOIN admin_limits al ON m.menu_id = al.menu_id " +
            "WHERE al.role = #{role}")
    List<Menu> selectMenusByRole(String role);
}
