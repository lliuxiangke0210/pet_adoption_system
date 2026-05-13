package com.dog.config;

import com.dog.security.JwtAuthenticationFilter;
import com.dog.security.JwtTokenProvider;
import com.dog.security.PlainTextPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 安全配置
 * <p>
 * 采用 JWT 无状态认证，关闭 CSRF 和 Session。
 * 公开接口无需认证，管理接口需 ADMIN 角色，其他接口需登录。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /** 无需认证的公开查询接口 */
    private static final String[] PIC_PATHS = {
            "/api/auth/**",              // 登录/注册/管理员联系方式
            "/api/health",               // 健康检查
            "/uploads/**",               // 文件静态资源
            "/api/pet/list",             // 宠物列表
            "/api/pet/*",                // 宠物详情
            "/api/category/list",        // 分类列表
            "/api/banner/list",          // 轮播图
            "/api/news/list",            // 资讯列表
            "/api/news/*",               // 资讯详情
            "/api/comment/pet/*",        // 宠物评论（查看）
            "/api/comment/news/*",       // 资讯评论（查看）
    };

    /** 需 ADMIN 角色的管理接口 */
    private static final String[] ADMIN_PATHS = {
            "/api/admin/**",
    };

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider);
    }

    /**
     * 安全过滤链：CSRF禁用 → 无状态会话 → 路径权限 → JWT过滤器
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 关闭 CSRF（RESTful API 不需要）
            .csrf(AbstractHttpConfigurer::disable)
            // 无状态会话（JWT 认证）
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // URL 权限控制
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PIC_PATHS).permitAll()
                .requestMatchers(ADMIN_PATHS).hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            // JWT 过滤器放在 UsernamePasswordAuthenticationFilter 之前
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 明文密码编码器（不使用加密）
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PlainTextPasswordEncoder();
    }

    /**
     * 认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
