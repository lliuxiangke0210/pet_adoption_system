package com.dog.controller;

import com.dog.common.Result;
import com.dog.entity.Comment;
import com.dog.security.JwtTokenProvider;
import com.dog.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论管理控制器
 */
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;

    public CommentController(CommentService commentService, JwtTokenProvider jwtTokenProvider) {
        this.commentService = commentService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 获取宠物的评论列表
     */
    @GetMapping("/pet/{petId}/comment")
    public Result<List<Comment>> listByPet(@PathVariable Long petId) {
        return Result.success(commentService.listByPetId(petId));
    }

    /**
     * 获取资讯的评论列表
     */
    @GetMapping("/news/{newsId}/comment")
    public Result<List<Comment>> listByNews(@PathVariable Long newsId) {
        return Result.success(commentService.listByNewsId(newsId));
    }

    /**
     * 提交评论（认证用户）
     */
    @PostMapping("/comment")
    public Result<Void> add(@RequestBody Comment comment,
                            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        comment.setUserId(userId);
        commentService.save(comment);
        return Result.success("评论成功", null);
    }

    /**
     * 删除评论（管理端）
     */
    @DeleteMapping("/admin/comment/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return Result.success("删除成功", null);
    }

    /**
     * 分页查询所有评论（管理端）
     */
    @GetMapping("/admin/comment/list")
    public Result<Map<String, Object>> listAll(@RequestParam(defaultValue = "1") int pageNum,
                                                @RequestParam(defaultValue = "10") int pageSize) {
        List<Comment> list = commentService.listAll(pageNum, pageSize);
        long total = commentService.countAll();
        Map<String, Object> result = new HashMap<>();
        result.put("records", list);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }
}
