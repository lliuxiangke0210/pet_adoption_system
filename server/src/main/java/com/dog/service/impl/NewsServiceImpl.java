package com.dog.service.impl;

import com.dog.common.BusinessException;
import com.dog.common.ResultCode;
import com.dog.entity.News;
import com.dog.mapper.NewsMapper;
import com.dog.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 宠物资讯服务实现
 */
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;

    public NewsServiceImpl(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }

    @Override
    public News getById(Long id) {
        News news = newsMapper.selectById(id);
        if (news == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "资讯不存在");
        }
        newsMapper.incrementViewCount(id);
        news.setViewCount(news.getViewCount() + 1);
        return news;
    }

    @Override
    public List<News> list(String keyword, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return newsMapper.selectList(keyword, offset, pageSize);
    }

    @Override
    public long count(String keyword) {
        return newsMapper.countList(keyword);
    }

    @Override
    public void save(News news) {
        if (news.getViewCount() == null) {
            news.setViewCount(0);
        }
        newsMapper.insert(news);
    }

    @Override
    public void update(News news) {
        News exist = newsMapper.selectById(news.getId());
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "资讯不存在");
        }
        newsMapper.update(news);
    }

    @Override
    public void delete(Long id) {
        News exist = newsMapper.selectById(id);
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "资讯不存在");
        }
        newsMapper.deleteById(id);
    }
}
