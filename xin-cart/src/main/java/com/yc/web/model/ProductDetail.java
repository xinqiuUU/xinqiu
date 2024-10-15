package com.yc.web.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Data
//商品详情
public class ProductDetail implements Serializable {
    private Product product;

    //商品图片地址  oss
    @TableField(exist = false)
    private List<String> urls;

    @TableField(exist = false)
    //购物车中的数量
    private Integer count;

    @TableField(exist = false)
    //商品的评论
    private Double smallCount;

    @TableField(exist = false)
    //商品的平均评分
    private Double rating;

    // 购物车中的小计
//    public Double getSmallCount() {
//        if (product.getRealprice()!= null){
//            smallCount = product.getRealprice() * count;
//        }
//        return smallCount;
//    }
    public Double getSmallCount() {
        if (product.getRealprice() != null) {
            // 使用 BigDecimal 进行精确计算
            BigDecimal realPrice = new BigDecimal(product.getRealprice().toString());
            BigDecimal countBigDecimal = new BigDecimal(count);

            // 计算结果并保留两位小数
            BigDecimal smallCountDecimal = realPrice.multiply(countBigDecimal).setScale(2, RoundingMode.HALF_UP);

            // 转换为 Double 返回
            smallCount = smallCountDecimal.doubleValue();
        }
        return smallCount;
    }

    public ProductDetail(Product product, Integer count) {
        this.product = product;
        this.count = count;
    }
}
