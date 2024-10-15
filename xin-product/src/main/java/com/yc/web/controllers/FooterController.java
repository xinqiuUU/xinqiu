package com.yc.web.controllers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.AdminJsonModel;
import com.yc.bean.BottomPanel;
import com.yc.bean.Footer;
import com.yc.bean.model.JsonModel;
import com.yc.dao.BottomPanelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
        获取底部信息
 */
//全部  Rest API 风格
@RestController
@RequestMapping("/product")   //请求路径的前缀  http://localhost:8080/product/ + xxx
@Slf4j
public class FooterController {

    @Autowired
    private BottomPanelMapper bottomPanelMapper;

    // 查看底部信息
    @PostMapping("/getFooterInformation")
    public JsonModel getFooterInformation() {
        JsonModel jm = new JsonModel();
        List<Map<String,Object>> list = bottomPanelMapper.selectAll();
        Footer footer = new Footer();
        List<String> customerService = new ArrayList<>() ;
        List<String> company = new ArrayList<>() ;
        List<String> socialMedia = new ArrayList<>() ;
        for (Map<String,Object> m:list){
            if ("客户服务".equals(m.get("title"))){
                customerService.add((String) m.get("content"));
            }
            if ("公司".equals(m.get("title"))){
                company.add((String) m.get("content"));
            }
            if ("社交媒体".equals(m.get("title"))){
                socialMedia.add((String) m.get("content"));
            }
        }
        footer.setCustomerService(customerService);
        footer.setCompany(company);
        footer.setSocialMedia(socialMedia);
        jm.setCode(1);
        jm.setObj(footer);
        return jm;
    }

    /**
     * 添加底部栏信息
     * @param title
     * @param content
     * @return AdminJsonModel
     */
    @PostMapping("/admin/addBottom")
    public AdminJsonModel addBottom(@RequestParam String title, @RequestParam String content) {
        AdminJsonModel jm = new AdminJsonModel();

        BottomPanel bottomPanel = new BottomPanel();
        bottomPanel.setTitle(title);
        bottomPanel.setContent(content);

        int result = bottomPanelMapper.insert(bottomPanel);

        if (result <= 0) {
            jm.setCode(1);
            jm.setMsg("导航栏内容更新失败");
            return jm;
        }
        jm.setCode(0);
        jm.setMsg("导航栏内容更新成功");
        return jm;
    }

    /**
     * 更新底部栏内容信息
     *
     * @param bottomPanel
     * @return AdminJsonModel
     */
    @PostMapping("/admin/updateBottom")
    public AdminJsonModel updateBottom(@RequestBody BottomPanel bottomPanel) {
        AdminJsonModel jm = new AdminJsonModel();

        int result = bottomPanelMapper.updateById(bottomPanel);

        if (result <= 0) {
            jm.setCode(1);
            jm.setMsg("底部栏更新失败/查询异常");
            return jm;
        }

        jm.setCode(0);
        jm.setMsg("底部栏更新成功");
        return jm;
    }

    /**
     * 获取所有底部栏信息
     *
     * @param page
     * @param limit
     * @return AdminJsonModel
     */
    @GetMapping("/admin/getAllBottomInfo")
    public AdminJsonModel getAllBottomInfo(@RequestParam int page, @RequestParam int limit) {
        AdminJsonModel jm = new AdminJsonModel();

        // 创建分页对象
        Page<BottomPanel> pageInfo = new Page<>(page, limit);
        List<BottomPanel> list = bottomPanelMapper.selectPage(pageInfo, null).getRecords();

        jm.setCode(0);
        jm.setMsg("底部栏信息查询成功");
        jm.setData(list);
        jm.setCount(pageInfo.getTotal()); // 总记录数

        return jm;
    }

    /**
     * 修改底部栏信息的状态
     *
     * @param idsStr
     * @param tostastus
     * @return AdminJsonModel
     */
    @PostMapping("/admin/examine")
    public AdminJsonModel examine(@RequestParam int idsStr, @RequestParam int tostastus) {
        AdminJsonModel jm = new AdminJsonModel();

        BottomPanel bottomPanel = new BottomPanel();
        bottomPanel.setBottom_id(idsStr);
        bottomPanel.setStatus(tostastus);

        int result = bottomPanelMapper.updateById(bottomPanel);
        if (result <= 0) {
            jm.setMsg("修改失败");
            jm.setCode(1);
            return jm;
        }

        jm.setCode(0);
        jm.setMsg("修改成功");
        return jm;
    }

}
