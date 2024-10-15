package com.yc.bean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    @TableId(type = IdType.AUTO)
    public Integer menu_id; //菜单项ID
    public String title;   // 菜单名
    public String href;    // 相对地址
    public String fontFamily;  // 字体
    public String icon;        //菜单图标
    public Boolean spread;   // 能下拉
    @TableField(exist = false)
    public Boolean isCheck;
    @TableField(exist = false)
    public List<Menu> children;
    public Integer parent_id ;

    public static void removeIds(Menu menu) {
        menu.menu_id = null;    // Assuming menu_id is Integer or int, set to null or 0 as appropriate
        menu.parent_id = null;  // Assuming parent_id is Integer or int, set to null or 0 as appropriate

        if (menu.children != null) {
            for (Menu child : menu.children) {
                removeIds(child); // Recursively remove ids from children
            }
        }
    }


    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getFontfamily() {
        return fontFamily;
    }

    public void setFontfamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menu_id=" + menu_id +
                ", title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", fontFamily='" + fontFamily + '\'' +
                ", icon='" + icon + '\'' +
                ", spread=" + spread +
                ", isCheck=" + isCheck +
                ", children=" + children +
                ", parent_id=" + parent_id +
                '}';
    }
}
