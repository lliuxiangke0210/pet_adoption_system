package com.dog.service;

import com.dog.entity.Comment;
import java.util.List;

/**
 * 评论服务接口
 */
public interface CommentService {

    /** 宠物评论列表 */
    List<Comment> listByPetId(Long petId);

    /** 资讯评论列表 */
    List<Comment> listByNewsId(Long newsId);

    /** 分页查询所有评论（管理端） */
    List<Comment> listAll(int pageNum, int pageSize);

    /** 统计评论总数 */
    long countAll();

    /** 添加评论 */
    void save(Comment comment);

    /** 删除评论 */
    void delete(Long id);
}
