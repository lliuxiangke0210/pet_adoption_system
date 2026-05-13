package com.dog.controller;

import com.dog.common.Result;
import com.dog.entity.Banner;
import com.dog.service.BannerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图管理控制器
 */
@RestController
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    /**
     * 查询启用的轮播图（公开接口，小程序首页使用）
     */
    @GetMapping("/api/banner/list")
    public Result<List<Banner>> listActive() {
        return Result.success(bannerService.listActive());
    }

    /**
     * 查询所有轮播图（管理端）
     */
    @GetMapping("/api/admin/banner")
    public Result<List<Banner>> listAll() {
        return Result.success(bannerService.listAll());
    }

    /**
     * 添加轮播图（管理端）
     */
    @PostMapping("/api/admin/banner")
    public Result<Void> add(@RequestBody Banner banner) {
        bannerService.save(banner);
        return Result.success("添加成功", null);
    }

    /**
     * 更新轮播图（管理端）
     */
    @PutMapping("/api/admin/banner")
    public Result<Void> update(@RequestBody Banner banner) {
        bannerService.update(banner);
        return Result.success("更新成功", null);
    }

    /**
     * 删除轮播图（管理端）
     */
    @DeleteMapping("/api/admin/banner/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        bannerService.delete(id);
        return Result.success("删除成功", null);
    }
}
