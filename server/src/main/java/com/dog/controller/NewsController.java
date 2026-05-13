package com.dog.controller;

import com.dog.common.Result;
import com.dog.entity.News;
import com.dog.service.NewsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 宠物资讯控制器
 */
@RestController
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * 分页查询资讯列表（公开接口）
     */
    @GetMapping("/api/news/list")
    public Result<Map<String, Object>> list(@RequestParam(defaultValue = "") String keyword,
                                            @RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "10") int pageSize) {
        List<News> list = newsService.list(keyword, pageNum, pageSize);
        long total = newsService.count(keyword);
        Map<String, Object> result = new HashMap<>();
        result.put("records", list);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }

    /**
     * 查询资讯详情（公开接口，会增加浏览次数）
     */
    @GetMapping("/api/news/{id}")
    public Result<News> getById(@PathVariable Long id) {
        return Result.success(newsService.getById(id));
    }

    /**
     * 添加资讯（管理端）
     */
    @PostMapping("/api/admin/news")
    public Result<Void> add(@RequestBody News news) {
        newsService.save(news);
        return Result.success("添加成功", null);
    }

    /**
     * 更新资讯（管理端）
     */
    @PutMapping("/api/admin/news")
    public Result<Void> update(@RequestBody News news) {
        newsService.update(news);
        return Result.success("更新成功", null);
    }

    /**
     * 删除资讯（管理端）
     */
    @DeleteMapping("/api/admin/news/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        newsService.delete(id);
        return Result.success("删除成功", null);
    }
}
