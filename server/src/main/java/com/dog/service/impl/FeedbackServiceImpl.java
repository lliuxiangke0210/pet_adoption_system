package com.dog.service.impl;

import com.dog.common.BusinessException;
import com.dog.common.ResultCode;
import com.dog.entity.Feedback;
import com.dog.mapper.FeedbackMapper;
import com.dog.service.FeedbackService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 反馈服务实现
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackMapper feedbackMapper;

    public FeedbackServiceImpl(FeedbackMapper feedbackMapper) {
        this.feedbackMapper = feedbackMapper;
    }

    @Override
    public void submit(Feedback feedback) {
        feedback.setStatus("pending");
        feedbackMapper.insert(feedback);
    }

    @Override
    public void reply(Long id, String reply) {
        Feedback feedback = getById(id);
        feedback.setReply(reply);
        feedback.setStatus("replied");
        feedbackMapper.update(feedback);
    }

    @Override
    public List<Feedback> listAll(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return feedbackMapper.selectAll(offset, pageSize);
    }

    @Override
    public long countAll() {
        return feedbackMapper.countAll();
    }

    @Override
    public List<Feedback> listByUserId(Long userId) {
        return feedbackMapper.selectByUserId(userId);
    }

    @Override
    public Feedback getById(Long id) {
        Feedback feedback = feedbackMapper.selectById(id);
        if (feedback == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "反馈不存在");
        }
        return feedback;
    }
}
