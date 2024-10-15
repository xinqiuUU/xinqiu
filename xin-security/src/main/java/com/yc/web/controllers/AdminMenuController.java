package com.yc.web.controllers;

import com.yc.bean.Menu;
import com.yc.dao.MenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/security/admin")
public class AdminMenuController {

    @Autowired
    private MenuMapper menuMapper;
    //获取菜单栏 menu
    @GetMapping("/getMenuInfo")
    public List<Menu> getMenuInfo(){
        // 查询角色对应的菜单
        // 查询角色对应的菜单
        List<Menu> list = menuMapper.selectMenusByRole(String.valueOf(0));

        // 构建父子菜单关系映射
        Map<Integer, List<Menu>> parentChildMap = new HashMap<>();
        for (Menu menu : list) {
            parentChildMap.computeIfAbsent(menu.getParent_id(), k -> new ArrayList<>()).add(menu);
        }

        // 将子菜单添加到对应的父菜单中
        for (Menu parent : list) {
            if (parentChildMap.containsKey(parent.getMenu_id())) {
                parent.setChildren(parentChildMap.get(parent.getMenu_id()));
            }
        }

        // 移除多余的子菜单
        Iterator<Menu> iterator = list.iterator();
        while (iterator.hasNext()) {
            Menu menu = iterator.next();
            if (menu.getParent_id() != 0) {
                iterator.remove();
            }
        }

        // 删除menu中多余的menu_id和parent_id属性
        if (list != null) {
            for (Menu menu : list) {
                Menu.removeIds(menu);
            }
        }
        return list;
    }

}
