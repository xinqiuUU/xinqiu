package com.yc.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.web.model.MyPageBean;
import com.yc.web.model.Product;
import com.yc.web.model.ProductDetail;

import java.util.List;

public interface ProductBiz {

    //获取最新产品
    Product selectNewProducts();

    //根据pid获取商品详细信息
    Product getProductDetails(Integer pid);

    //获取相关商品
    List<Product> getRelatedProducts(String type);

    //获取热门产品信息
    List<Product> getHotProducts();

    //获取所有产品信息   分页 排序
    MyPageBean<Product> getAllProducts(Product product, int pageno, int pageSize, String sortby, String sort);
}
