package com.dog.controller;

import com.dog.common.Result;
import com.dog.entity.User;
import com.dog.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

/**
 * 诊断控制器
 */
@RestController
public class HealthController {

    private final UserMapper userMapper;

    public HealthController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /** 直接查数据库 */
    @GetMapping("/api/health")
    public Result<String> health() {
        User user = userMapper.selectByUsername("admin");
        if (user != null) {
            return Result.success("找到用户: " + user.getUsername() + ", 密码: " + user.getPassword() + ", ID: " + user.getId());
        }
        long count = userMapper.countTotal();
        return Result.success("未找到admin用户。总用户数: " + count);
    }
}
