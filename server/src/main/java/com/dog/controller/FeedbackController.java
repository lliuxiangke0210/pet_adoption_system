package com.dog.controller;

import com.dog.common.Result;
import com.dog.entity.Feedback;
import com.dog.security.JwtTokenProvider;
import com.dog.service.FeedbackService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户反馈控制器
 */
@RestController
@RequestMapping("/api")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final JwtTokenProvider jwtTokenProvider;

    public FeedbackController(FeedbackService feedbackService, JwtTokenProvider jwtTokenProvider) {
        this.feedbackService = feedbackService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 提交反馈（认证用户）
     */
    @PostMapping("/feedback")
    public Result<Void> submit(@RequestBody Feedback feedback,
                               @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        feedback.setUserId(userId);
        feedbackService.submit(feedback);
        return Result.success("反馈已提交", null);
    }

    /**
     * 查询我的反馈（认证用户）
     */
    @GetMapping("/feedback/my")
    public Result<List<Feedback>> myFeedback(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        return Result.success(feedbackService.listByUserId(userId));
    }

    /**
     * 分页查询所有反馈（管理端）
     */
    @GetMapping("/admin/feedback/list")
    public Result<Map<String, Object>> listAll(@RequestParam(defaultValue = "1") int pageNum,
                                                @RequestParam(defaultValue = "10") int pageSize) {
        List<Feedback> list = feedbackService.listAll(pageNum, pageSize);
        long total = feedbackService.countAll();
        Map<String, Object> result = new HashMap<>();
        result.put("records", list);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }

    /**
     * 管理员回复反馈
     */
    @PutMapping("/admin/feedback/{id}")
    public Result<Void> reply(@PathVariable Long id, @RequestParam String reply) {
        feedbackService.reply(id, reply);
        return Result.success("回复成功", null);
    }
}
