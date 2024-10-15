package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.BottomPanel;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface BottomPanelMapper extends BaseMapper<BottomPanel> {

    // 查看所有的底部导航栏
    @Select("select * from bottompanel where status = 1")
    List<Map<String,Object>> selectAll();
}
