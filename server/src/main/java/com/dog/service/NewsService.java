package com.dog.service;

import com.dog.entity.News;
import java.util.List;

/**
 * 宠物资讯服务接口
 */
public interface NewsService {

    /** 根据ID查询详情（并增加浏览次数） */
    News getById(Long id);

    /** 分页查询资讯列表 */
    List<News> list(String keyword, int pageNum, int pageSize);

    /** 统计资讯总数 */
    long count(String keyword);

    /** 添加资讯 */
    void save(News news);

    /** 更新资讯 */
    void update(News news);

    /** 删除资讯 */
    void delete(Long id);
}
