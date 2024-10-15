package com.yc.web.controllers;

import com.yc.bean.AdminJsonModel;
import com.yc.dao.ReplyModuleMapper;
import com.yc.web.model.Comment;
import com.yc.web.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    评论控制器 管理员
 */
@RestController
@RequestMapping("/comment/admin")
public class AdminCommentController {

    @Autowired
    private MongoTemplate mongoTemplate; // 注入 MongoTemplate 实例
    @Autowired
    private ReplyModuleMapper replyModuleMapper;

    // 分页查询所有评论
    @GetMapping("/getAllAppraise")
    public AdminJsonModel getAllAppraise(@RequestParam int page,
                                         @RequestParam int limit,
                                         @RequestParam(required = false) String pid,
                                         @RequestParam(required = false) String uid,
                                         @RequestParam(required = false) Integer rating,
                                         @RequestParam(required = false) Integer status) {
        AdminJsonModel jm = new AdminJsonModel();

        // 构建查询条件
        Query query = new Query();

        if (pid != null && !pid.trim().isEmpty()) {
            query.addCriteria(Criteria.where("pid").is(pid));
        }

        if (uid != null && !uid.trim().isEmpty()) {
            query.addCriteria(Criteria.where("uid").is(uid));
        }

        if (rating != null && rating != -100) {
            query.addCriteria(Criteria.where("rating").is(rating));
        }

        if (status != null && status != -100) {
            query.addCriteria(Criteria.where("status").is(status));
        }

        // 统计总条数
        long count = mongoTemplate.count(query, Comment.class);

        // 分页查询
        query.skip((page - 1) * limit).limit(limit);
        // 按时间降序排序
        query.with(Sort.by(Sort.Direction.DESC, "createTime"));
        List<Comment> comments = mongoTemplate.find(query, Comment.class);

        // 处理返回结果
        if (comments == null || comments.isEmpty()) {
            jm.setCode(1);
            jm.setMsg("没有找到评论数据");
            return jm;
        }

        jm.setCode(0);
        jm.setMsg("查询用户商品评论成功");
        jm.setData(comments);
        jm.setCount(count);

        return jm;
    }

    // 初始化回复模块数据
    @PostMapping("/initReplyModule")
    public AdminJsonModel initReplyModule() {
        AdminJsonModel jm = new AdminJsonModel();
        List<Map<String, Object>> replyModules = replyModuleMapper.selectAllReplyModules();
        jm.setData(replyModules);
        jm.setCode(0);  // 表示成功
        jm.setMsg("查询成功");
        return jm;
    }

    // 回复评论
    @PostMapping("/reply")
    public AdminJsonModel reply(@RequestParam String appraise_id, // 评论id
                                @RequestParam String admin_reply, // 回复内容 管理员回复内容
                                @RequestParam String uid, // 被回复者的uid
                                @RequestParam String user_rating, // 评分
                                @RequestParam Integer pid) { // 商品pid
        AdminJsonModel jm = new AdminJsonModel();
        // 1. 查询出评论对象
        Query query = new Query(Criteria.where("_id").is(appraise_id));
        Comment comment = mongoTemplate.findOne(query, Comment.class);
        //2. 获取回复列表
        List<Reply> replies = comment.getReplies();
        //3. 如果回复列表为空，创建一个新的列表
        if (replies== null) {
            replies = new ArrayList<>();
        }
        //4. 添加回复到列表中
        Reply reply = new Reply();
        reply.setReplierName("管理员");// 管理员回复者姓名
        reply.setMessage(admin_reply);// 管理员回复内容
        reply.setReplyDate(Reply.getCurrentTime());// 回复时间
        replies.add(reply);// 添加回复到列表中
        //5. 更新评论对象的回复列表
        comment.setReplies(replies);// 更新评论对象的回复列表
        comment.setReply_status(1);// 回复状态为已回复
        //6. 保存更新后的评论对象
        mongoTemplate.save(comment);
        jm.setCode(0);
        jm.setMsg("评论回复成功");
        return  jm;
    }

    /**
     * 审核单个商品
     */
    @PostMapping("/examine")
    public AdminJsonModel examine(@RequestParam("idsStr") String  commentId,
                                  @RequestParam("tostastus") Integer  status) {
        AdminJsonModel jm = new AdminJsonModel();
        Query query = new Query(Criteria.where("_id").is(commentId)); // 根据评论的 ID 查找
        Update update = new Update().set("status", status);           // 设置 status 字段

        // 执行更新操作
        boolean result =mongoTemplate.updateFirst(query, update, Comment.class).getModifiedCount()>0;
        if (result) {
            jm.setCode(0);
            jm.setMsg("审核成功");
        } else {
            jm.setCode(1);
            jm.setMsg("审核失败");
        }
        return jm;
    }

    /**
     * 一键回复五星评论
     */
    @PostMapping("/reply_five")
    public AdminJsonModel replyFiveStar() {
        AdminJsonModel jm = new AdminJsonModel();

        // 1. 查询所有五星评论
        Query query = new Query(Criteria.where("rating").is(5)); // 查询评分为5的评论
        List<Comment> comments = mongoTemplate.find(query, Comment.class); // 获取五星评论列表

        // 2. 遍历评论，添加管理员回复
        for (Comment comment : comments) {
            //判断是否已经回复 1 已回复 0 未回复
            if (comment.getReply_status() == 1){
                continue;
            }
            // 3. 获取回复列表
            List<Reply> replies = comment.getReplies();

            // 4. 如果回复列表为空，创建一个新的列表
            if (replies == null) {
                replies = new ArrayList<>();
            }
            // 5. 创建管理员回复
            Reply reply = new Reply();
            reply.setReplierName("管理员"); // 管理员回复者姓名
            reply.setMessage("亲，感谢您的好评"); // 管理员回复内容
            reply.setReplyDate(Reply.getCurrentTime()); // 回复时间
            reply.setHead_photo("https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/admin.png");
            replies.add(reply); // 添加回复到列表中

            // 6. 更新评论对象的回复列表
            comment.setReplies(replies); // 更新评论对象的回复列表
            comment.setReply_status(1); // 回复状态为已回复

            // 7. 保存更新后的评论对象
            mongoTemplate.save(comment); // 保存评论对象
        }

        jm.setCode(0);
        jm.setMsg("批量回复成功");
        return jm;
    }

}
