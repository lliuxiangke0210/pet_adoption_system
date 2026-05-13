package com.dog.controller;

import com.dog.common.Result;
import com.dog.dto.LoginRequest;
import com.dog.dto.LoginResponse;
import com.dog.dto.RegisterRequest;
import com.dog.entity.User;
import com.dog.security.JwtTokenProvider;
import com.dog.security.UserDetailsServiceImpl;
import com.dog.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器 - 处理登录、注册、获取用户信息
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userService.getByUsername(request.getUsername());
            String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername(), user.getRole());

            LoginResponse response = new LoginResponse(token, user.getId(), user.getUsername(), user.getRole());
            return Result.success("登录成功", response);
        } catch (org.springframework.security.core.AuthenticationException e) {
            return Result.error(401, "用户名或密码错误");
        }
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRealName(request.getRealName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        userService.register(user);
        return Result.success("注册成功", null);
    }

    /**
     * 获取管理员联系方式（公开接口）
     */
    @GetMapping("/admin-contact")
    public Result<Map<String, String>> getAdminContact() {
        User admin = userService.getByUsername("admin");
        Map<String, String> contact = new HashMap<>();
        contact.put("realName", admin.getRealName() != null ? admin.getRealName() : "管理员");
        contact.put("phone", admin.getPhone() != null ? admin.getPhone() : "暂无");
        contact.put("email", admin.getEmail() != null ? admin.getEmail() : "暂无");
        return Result.success(contact);
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/info")
    public Result<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getByUsername(username);
        if (user != null) {
            user.setPassword(null); // 不返回密码
        }
        return Result.success(user);
    }
}
