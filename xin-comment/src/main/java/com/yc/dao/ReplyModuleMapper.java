package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

// 回复模块 持久层接口
public interface ReplyModuleMapper extends BaseMapper<Map<String,Object>> {

    @Select("SELECT * FROM reply_module")
    List<Map<String, Object>> selectAllReplyModules();
}
