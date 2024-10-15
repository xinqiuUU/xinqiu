package com.yc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.dao.ProductDetailMapper;
import com.yc.dao.ProductMapper;
import com.yc.utils.ProductPicUtil;
import com.yc.web.model.MyPageBean;
import com.yc.web.model.Product;
import com.yc.web.model.ProductDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)  //开启事务
@Slf4j
public class ProductBizImpl implements ProductBiz {

    @Autowired
    private ProductMapper productMapper;//商品dao

    private ProductPicUtil productPicUtil;
    @Autowired
    public void setProductPicUtil(ProductPicUtil productPicUtil){
        this.productPicUtil = productPicUtil;
    }

    private ProductDetailMapper productDetailMapper;
    @Autowired
    public void setProductDetailMapper(ProductDetailMapper productDetailMapper){
        this.productDetailMapper = productDetailMapper;
    }

    // 查看最新商品
    @Cacheable(cacheNames = "newProductsCache" , key = "'newProduct'")
    @Override
    public Product selectNewProducts() {
        // 调用 Mapper 查询最新的商品信息
         Product product = productMapper.selectNewProducts();
         productPicUtil.getProductPic( product, product.getPid() );
         return product;
    }

    //获取商品详细信息
    @Cacheable(cacheNames = "productDetail")
    @Override
    public Product getProductDetails(Integer pid) {
        Product product =  productMapper.getProductDetails(pid);
        productPicUtil.getProductPic( product , pid );
        return product;
    }

    //获取相关商品
    @Cacheable(cacheNames = "getRelatedProducts")
    @Override
    public List<Product> getRelatedProducts(String type) {
        List<Product> productDetails = productMapper.DetailList(type);
        for (Product p : productDetails) {
            productPicUtil.getProductPic( p , p.getPid() );
        }
        Collections.shuffle(productDetails); // 随机打乱列表
        int numberOfProductsToReturn = Math.min(4, productDetails.size()); // 计算返回的产品数量
        return new ArrayList<>(productDetails.subList(0, numberOfProductsToReturn)); // 转换为 ArrayList 并返回结果
    }
    //获取热门产品信息
    @Cacheable(cacheNames = "getHotProducts")
    @Override
    public List<Product> getHotProducts() {
        List<Product> productDetails = productMapper.getHotProducts();
        for (Product p : productDetails) {
            productPicUtil.getProductPic( p , p.getPid() );
        }
        return productDetails;
    }

    //获取所有商品信息
    @Override
    public MyPageBean<Product> getAllProducts(Product product, int pageno, int size, String sortby, String sort) {
        QueryWrapper wrapper = new QueryWrapper();
        if (product != null) {
            if (product.getType() != null && !"".equals(product.getType())) {
                wrapper.like("type", product.getType());
            }
        }
        if ("asc".equalsIgnoreCase(sort)) {
            wrapper.orderByAsc(sortby);
        } else {
            wrapper.orderByDesc(sortby);
        }
        wrapper.eq("status", 1);  // 默认只获取上架商品
        Page<Product> page = new Page<>(pageno, size);
        Page<Product> pageList = productMapper.selectPage(page, wrapper);

        for (Product p : pageList.getRecords()) {
            productPicUtil.getProductPic( p, p.getPid() );
        }
        MyPageBean<Product> myPageBean = new MyPageBean<>();
        myPageBean.setPageno(pageno);
        myPageBean.setSize(size);
        myPageBean.setSortby(sortby);
        myPageBean.setSort(sort);
        myPageBean.setRecords(pageList.getRecords());
        myPageBean.setTotal(pageList.getTotal());
        myPageBean.setProduct(product);
        myPageBean.calculate();
        return myPageBean;
    }



}

