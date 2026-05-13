package com.dog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录响应
 */
@Data
@AllArgsConstructor
public class LoginResponse {

    /** JWT令牌 */
    private String token;

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String username;

    /** 角色 */
    private String role;
}
