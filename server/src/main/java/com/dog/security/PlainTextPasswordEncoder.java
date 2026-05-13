package com.dog.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 明文密码编码器 - 不做任何加密处理
 * 用户密码直接存储和比较
 */
public class PlainTextPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
