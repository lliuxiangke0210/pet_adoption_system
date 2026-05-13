package com.dog.service;

import com.dog.entity.Feedback;
import java.util.List;

/**
 * 反馈服务接口
 */
public interface FeedbackService {

    /** 提交反馈 */
    void submit(Feedback feedback);

    /** 管理员回复反馈 */
    void reply(Long id, String reply);

    /** 分页查询所有反馈（管理端） */
    List<Feedback> listAll(int pageNum, int pageSize);

    /** 统计反馈总数 */
    long countAll();

    /** 查询用户的反馈 */
    List<Feedback> listByUserId(Long userId);

    /** 根据ID查询 */
    Feedback getById(Long id);
}
