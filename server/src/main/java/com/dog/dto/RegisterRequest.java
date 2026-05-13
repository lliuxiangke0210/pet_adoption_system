package com.dog.dto;

import lombok.Data;

/**
 * 注册请求参数
 */
@Data
public class RegisterRequest {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 真实姓名 */
    private String realName;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;
}
