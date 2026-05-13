package com.dog.mapper;

import com.dog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 评论数据访问层
 */
@Mapper
public interface CommentMapper {

    /** 根据ID查询 */
    Comment selectById(@Param("id") Long id);

    /** 查询宠物的评论列表 */
    List<Comment> selectByPetId(@Param("petId") Long petId);

    /** 查询资讯的评论列表 */
    List<Comment> selectByNewsId(@Param("newsId") Long newsId);

    /** 分页查询所有评论（管理端） */
    List<Comment> selectAll(@Param("offset") int offset, @Param("limit") int limit);

    /** 统计评论总数 */
    long countAll();

    /** 插入评论 */
    int insert(Comment comment);

    /** 删除评论 */
    int deleteById(@Param("id") Long id);
}
