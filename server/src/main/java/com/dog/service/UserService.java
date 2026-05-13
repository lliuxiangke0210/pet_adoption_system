package com.dog.service;

import com.dog.entity.User;
import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /** 根据用户名查询用户 */
    User getByUsername(String username);

    /** 根据ID查询用户 */
    User getById(Long id);

    /** 分页查询用户列表 */
    List<User> list(String keyword, int pageNum, int pageSize);

    /** 统计用户总数 */
    long count(String keyword);

    /** 注册用户 */
    void register(User user);

    /** 更新用户 */
    void update(User user);

    /** 删除用户 */
    void delete(Long id);

    /** 统计总用户数 */
    long countTotal();
}
