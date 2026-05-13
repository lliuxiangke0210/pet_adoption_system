package com.dog.mapper;

import com.dog.entity.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 轮播图数据访问层
 */
@Mapper
public interface BannerMapper {

    /** 根据ID查询 */
    Banner selectById(@Param("id") Long id);

    /** 查询所有启用的轮播图（按排序） */
    List<Banner> selectActive();

    /** 查询所有轮播图（管理端） */
    List<Banner> selectAll();

    /** 插入轮播图 */
    int insert(Banner banner);

    /** 更新轮播图 */
    int update(Banner banner);

    /** 删除轮播图 */
    int deleteById(@Param("id") Long id);
}
