package com.dog.controller;

import com.dog.common.Result;
import com.dog.entity.PetCategory;
import com.dog.service.PetCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 宠物分类控制器
 */
@RestController
public class PetCategoryController {

    private final PetCategoryService categoryService;

    public PetCategoryController(PetCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 查询所有分类（公开接口）
     */
    @GetMapping("/api/category/list")
    public Result<List<PetCategory>> list() {
        return Result.success(categoryService.listAll());
    }

    /**
     * 添加分类（管理端）
     */
    @PostMapping("/api/admin/category")
    public Result<Void> add(@RequestBody PetCategory category) {
        categoryService.save(category);
        return Result.success("添加成功", null);
    }

    /**
     * 更新分类（管理端）
     */
    @PutMapping("/api/admin/category")
    public Result<Void> update(@RequestBody PetCategory category) {
        categoryService.update(category);
        return Result.success("更新成功", null);
    }

    /**
     * 删除分类（管理端）
     */
    @DeleteMapping("/api/admin/category/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return Result.success("删除成功", null);
    }
}
