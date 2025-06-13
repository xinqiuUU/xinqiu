package com.yc.web.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * 评论实体类
 */
//评论实体类
@Data
@Document(collection = "comment") // 映射到 MongoDB 中的 "comment" 集合
public class Comment {
    @Id
    private String id;       // 评论 ID 主键字段
    private String content;  // 评论内容
    private String orderId;  // 订单order_id
    private String pid;      // 商品pid
    private int rating;       // 评分

    private String uid;      // 用户id 即uid
    private String uname;    // 用户姓名
    private String head_photo; // 用户头像
    private String createTime; // 评论时间

    private Integer status=1;  // -1 删除评价, 0 未审核, 1 审核通过, 2 审核不通过
    private Integer reply_status=0 ;       //#商家回复状态 0 未回复, 1 已回复

    private List<Reply> replies; // 回复内容

    // 获取当前时间的静态方法
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
