package com.yc.utils;

import com.yc.dao.ProductPicMapper;
import com.yc.web.model.Product;
import com.yc.web.model.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
    商品图片  工具类
 */
@Component
public class ProductPicUtil {

    private ProductPicMapper productPicMapper;//商品图片dao
    @Autowired
    public void getProductPicMapper(ProductPicMapper productPicMapper){
        this.productPicMapper = productPicMapper;
    }
    //根据商品id查询商品图片
    public void getProductPic(Product p , Integer pid) {
        List<String> list = productPicMapper.selectUrlByPid(pid);
        List<String> urls = new ArrayList<>();
        for (String m : list){
            urls.add(   String.valueOf(m)   );
        }
        if (p == null){
            return;
        }
        if ( urls!=null && urls.size()>0 ){
            p.setUrls(urls);
        }
    }
}
