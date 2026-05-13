package com.dog.service.impl;

import com.dog.common.BusinessException;
import com.dog.common.ResultCode;
import com.dog.entity.Comment;
import com.dog.mapper.CommentMapper;
import com.dog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论服务实现
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public List<Comment> listByPetId(Long petId) {
        return commentMapper.selectByPetId(petId);
    }

    @Override
    public List<Comment> listByNewsId(Long newsId) {
        return commentMapper.selectByNewsId(newsId);
    }

    @Override
    public List<Comment> listAll(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return commentMapper.selectAll(offset, pageSize);
    }

    @Override
    public long countAll() {
        return commentMapper.countAll();
    }

    @Override
    public void save(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public void delete(Long id) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "评论不存在");
        }
        commentMapper.deleteById(id);
    }
}
