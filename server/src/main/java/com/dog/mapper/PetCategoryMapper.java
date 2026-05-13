package com.dog.mapper;

import com.dog.entity.PetCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 宠物分类数据访问层
 */
@Mapper
public interface PetCategoryMapper {

    /** 根据ID查询 */
    PetCategory selectById(@Param("id") Long id);

    /** 查询所有分类 */
    List<PetCategory> selectAll();

    /** 插入分类 */
    int insert(PetCategory category);

    /** 更新分类 */
    int update(PetCategory category);

    /** 删除分类 */
    int deleteById(@Param("id") Long id);
}
