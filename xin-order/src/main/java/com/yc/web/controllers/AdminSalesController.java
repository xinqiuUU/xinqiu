package com.yc.web.controllers;

import com.yc.bean.AdminJsonModel;
import com.yc.service.SalesBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/admin")
public class AdminSalesController {

    @Autowired
    private SalesBiz salesBiz;

    // 按季度统计销售情况
    @PostMapping("/getDataOfQuarter")
    public AdminJsonModel getDataOfQuarter() {
        AdminJsonModel jm = new AdminJsonModel();

        List<int[]> list = salesBiz.getQuarterSales();
        if (list == null || list.size() <= 0) {
            jm.setCode(1);
            jm.setMsg("季度销量查询失败");
            return jm;
        }

        jm.setCode(0);
        jm.setMsg("季度销量查询成功");
        jm.setData(list);
        return jm;
    }

    /**
     * 获取年报表的表单数据
     */
    @PostMapping("/getDataOfYear")
    public AdminJsonModel getDataOfYear(){
        AdminJsonModel jm = new AdminJsonModel();

        int[] yearDateList = salesBiz.getYearDate(); // 获取年度销量数据

        jm.setCode(0);
        jm.setData(yearDateList); // 设置数据
        return jm;
    }

    /**
     * 获取月销售量报表的表单数据
     */
    @PostMapping("/getDataOfMonth")
    public AdminJsonModel getDataOfMonth(@RequestParam int year) {
        AdminJsonModel jm = new AdminJsonModel();

        int[] monthDate = salesBiz.getMonthDate(year); // 获取指定年月份的销量数据

        jm.setCode(0);
        jm.setData(monthDate); // 设置数据
        return jm;
    }

}
