package com.dog.mapper;

import com.dog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 用户数据访问层
 */
@Mapper
public interface UserMapper {

    /** 根据用户名查询用户 */
    User selectByUsername(@Param("username") String username);

    /** 根据ID查询用户 */
    User selectById(@Param("id") Long id);

    /** 分页查询用户列表 */
    List<User> selectList(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    /** 统计用户总数 */
    long countList(@Param("keyword") String keyword);

    /** 插入用户 */
    int insert(User user);

    /** 更新用户 */
    int update(User user);

    /** 删除用户 */
    int deleteById(@Param("id") Long id);

    /** 按月统计新增用户数 */
    List<Map<String, Object>> countByMonth(@Param("year") int year);

    /** 统计总用户数 */
    long countTotal();
}
