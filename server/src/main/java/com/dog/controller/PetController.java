package com.dog.controller;

import com.dog.common.Result;
import com.dog.entity.Pet;
import com.dog.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 宠物管理控制器
 */
@RestController
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    /**
     * 分页查询宠物列表（公开接口）
     */
    @GetMapping("/api/pet/list")
    public Result<Map<String, Object>> list(@RequestParam(defaultValue = "") String keyword,
                                            @RequestParam(required = false) Long categoryId,
                                            @RequestParam(required = false) String status,
                                            @RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "10") int pageSize) {
        List<Pet> list = petService.list(keyword, categoryId, status, pageNum, pageSize);
        long total = petService.count(keyword, categoryId, status);
        Map<String, Object> result = new HashMap<>();
        result.put("records", list);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }

    /**
     * 查询宠物详情（公开接口）
     */
    @GetMapping("/api/pet/{id}")
    public Result<Pet> getById(@PathVariable Long id) {
        return Result.success(petService.getById(id));
    }

    /**
     * 添加宠物（管理端）
     */
    @PostMapping("/api/admin/pet")
    public Result<Void> add(@RequestBody Pet pet) {
        petService.save(pet);
        return Result.success("添加成功", null);
    }

    /**
     * 更新宠物（管理端）
     */
    @PutMapping("/api/admin/pet")
    public Result<Void> update(@RequestBody Pet pet) {
        petService.update(pet);
        return Result.success("更新成功", null);
    }

    /**
     * 删除宠物（管理端）
     */
    @DeleteMapping("/api/admin/pet/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        petService.delete(id);
        return Result.success("删除成功", null);
    }
}
