package com.dog.mapper;

import com.dog.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户反馈数据访问层
 */
@Mapper
public interface FeedbackMapper {

    /** 根据ID查询 */
    Feedback selectById(@Param("id") Long id);

    /** 分页查询所有反馈（管理端） */
    List<Feedback> selectAll(@Param("offset") int offset, @Param("limit") int limit);

    /** 统计反馈总数 */
    long countAll();

    /** 查询用户的反馈 */
    List<Feedback> selectByUserId(@Param("userId") Long userId);

    /** 插入反馈 */
    int insert(Feedback feedback);

    /** 更新反馈（回复） */
    int update(Feedback feedback);
}
