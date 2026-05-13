package com.dog.controller;

import com.dog.common.Result;
import com.dog.entity.User;
import com.dog.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/api/admin/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 分页查询用户列表
     */
    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestParam(defaultValue = "") String keyword,
                                            @RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "10") int pageSize) {
        List<User> list = userService.list(keyword, pageNum, pageSize);
        long total = userService.count(keyword);
        Map<String, Object> result = new HashMap<>();
        result.put("records", list);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }

    /**
     * 根据ID查询用户详情
     */
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        user.setPassword(null);
        return Result.success(user);
    }

    /**
     * 添加用户
     */
    @PostMapping
    public Result<Void> add(@RequestBody User user) {
        userService.register(user);
        return Result.success("添加成功", null);
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    public Result<Void> update(@RequestBody User user) {
        userService.update(user);
        return Result.success("更新成功", null);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return Result.success("删除成功", null);
    }
}
