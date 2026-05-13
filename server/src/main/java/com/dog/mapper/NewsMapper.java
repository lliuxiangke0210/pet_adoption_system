package com.dog.mapper;

import com.dog.entity.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 宠物资讯数据访问层
 */
@Mapper
public interface NewsMapper {

    /** 根据ID查询 */
    News selectById(@Param("id") Long id);

    /** 分页查询资讯列表 */
    List<News> selectList(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    /** 统计资讯总数 */
    long countList(@Param("keyword") String keyword);

    /** 插入资讯 */
    int insert(News news);

    /** 更新资讯 */
    int update(News news);

    /** 删除资讯 */
    int deleteById(@Param("id") Long id);

    /** 增加浏览次数 */
    int incrementViewCount(@Param("id") Long id);
}
