package com.dog.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
public class User {

    /** 用户ID */
    private Long id;

    /** 用户名 */
    private String username;

    /** 密码（明文） */
    private String password;

    /** 真实姓名 */
    private String realName;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 头像URL */
    private String avatar;

    /** 角色：admin/user */
    private String role;

    /** 状态：0禁用 1启用 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;
}
