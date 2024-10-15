package com.yc.web.controllers;

import com.yc.bean.model.JsonModel;
import com.yc.service.CommentService;
import com.yc.web.model.Comment;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


/*
    评论控制器
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 判断用户是否对该商品进行评价
    @GetMapping("/isCommented")
    public JsonModel isCommented(@RequestParam String orderId, @RequestParam String pid) {
        boolean commented = commentService.isCommented(orderId, pid);
        JsonModel jm = new JsonModel();
        jm.setCode(commented ? 0 : 1); // 如果已评论，code 为 0；否则为 1
        return jm;
    }

    // 根据商品 pid 获取评论（支持分页）
    @GetMapping("/getComments/{pid}")
    public JsonModel getCommentsByPid(@PathVariable String pid, // 商品 pid
                                      @RequestParam(defaultValue = "0") int page, // 页码，默认为 0
                                      @RequestParam(defaultValue = "5") int size) { // 每页大小，默认为 5
        Page<Comment> comments = commentService.getCommentsByPid(pid, page, size);
        JsonModel jm = new JsonModel();
        jm.setCode(1);
        jm.setObj(comments);// 评论列表
        return jm;
    }

    // 提交评价
    @PostMapping("/submit")
    public JsonModel submitComment(@RequestBody Comment comment , HttpServletRequest request) {
        commentService.submitComment(comment , request);
        JsonModel jm = new JsonModel();
        jm.setCode(1); // 提交成功，code 为 1
        return jm;
    }

    // 回复评论
    @PostMapping("/replies")
    public JsonModel replyToComment(@RequestBody Comment comment) {
        Comment c = commentService.replyToComment(comment.getId(), comment.getReplies().get(0));
        JsonModel jm = new JsonModel();
        jm.setCode(1); // 回复成功，code 为 1
        jm.setObj(c); // 返回回复后的评论对象
        return jm;
    }

//    // 根据作者获取评论
//    @GetMapping("/author/{author}")
//    public List<Comment> getCommentsByAuthor(@PathVariable String uid) {
//
//        return resourceCommentRepository.findByUid(uid);
//    }
//
//    // 保存新评论
//    @PostMapping
//    public void saveComment(@RequestBody Comment comment) {
//        resourceCommentRepository.save(comment);
//    }


}
