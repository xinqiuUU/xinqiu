package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.web.model.ProductPic;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ProductPicMapper extends BaseMapper<ProductPic> {

    //查询商品的图片地址
    @Select("SELECT url FROM product_pic WHERE pid = #{pid}")
    List<String> selectUrlByPid(Integer pid);

    //查询商品的图片地址
    @Select("SELECT url FROM product_pic WHERE pid = #{pid}")
    List<Map<String,Object>> selectUrlByPidMap(Integer pid);

}
