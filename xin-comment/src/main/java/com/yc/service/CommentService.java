package com.yc.service;

import com.yc.web.model.Comment;
import com.yc.web.model.Reply;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/*
   评价及评论的服务类
 */
@Service
public class CommentService {

    @Autowired
    private MongoTemplate mongoTemplate; // 注入 MongoTemplate 实例

    //判断用户是否对该商品进行评价
    public boolean isCommented(String orderId, String pid) {
        // 根据 uid 和 pid 查询评论
        Query query = new Query(Criteria.where("orderId").is(orderId).and("pid").is(pid));
        Comment comment = mongoTemplate.findOne(query, Comment.class);
        return comment != null;//如果不为空，说明已经评论过了
    }
    // 根据商品pid获取评论
    public Page<Comment> getCommentsByPid(String pid, int page, int size) {
        // 创建分页请求
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createTime"))); // 根据时间降序排序
        // 根据 pid 查询评论   状态为1 表示审核通过的评论
        Query query = new Query(Criteria.where("pid").is(pid).and("status").is(1));
        // 计算总数
        long total = mongoTemplate.count(query, Comment.class);
        // 添加分页参数
        query.with(pageable);
        // 查询评论
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        // 对每个评论的回复进行排序
        for (Comment comment : comments) {
            if (comment.getReplies() != null) {
                comment.setReplies(comment.getReplies().stream()
                        .sorted(Comparator.comparing(Reply::getReplyDate)) // 根据回复时间升序排序
                        .collect(Collectors.toList()));
            }
        }
        // 返回分页结果
        return new PageImpl<>(comments, pageable, total);
    }
    //提交评价
    public void submitComment(Comment comment , HttpServletRequest request) {
        // 从请求中获取用户信息
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        // 设置评论的用户信息
        comment.setUid((String) userClaims.get("uid"));
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        comment.setCreateTime(sdf.format(date));
        // 保存评论到数据库
        mongoTemplate.save(comment);
    }
    // 回复评论
    public Comment replyToComment(String id, Reply reply) {
        // 1. 查询出评论对象
        Query query = new Query(Criteria.where("_id").is(id));
        Comment comment = mongoTemplate.findOne(query, Comment.class);
        //2. 获取回复列表
        List<Reply> replies = comment.getReplies();
        //3. 如果回复列表为空，创建一个新的列表
        if (replies== null) {
            replies = new ArrayList<>();
        }
        //4. 添加回复到列表中
        reply.setReplyDate(Reply.getCurrentTime());
        replies.add(reply);
        //5. 更新评论对象的回复列表
        comment.setReplies(replies);
        //6. 保存更新后的评论对象
        return  mongoTemplate.save(comment);
    }

}
