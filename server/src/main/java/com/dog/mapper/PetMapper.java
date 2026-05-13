package com.dog.mapper;

import com.dog.entity.Pet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 宠物数据访问层
 */
@Mapper
public interface PetMapper {

    /** 根据ID查询 */
    Pet selectById(@Param("id") Long id);

    /** 分页查询宠物列表 */
    List<Pet> selectList(@Param("keyword") String keyword, @Param("categoryId") Long categoryId,
                         @Param("status") String status, @Param("offset") int offset, @Param("limit") int limit);

    /** 统计宠物总数 */
    long countList(@Param("keyword") String keyword, @Param("categoryId") Long categoryId,
                   @Param("status") String status);

    /** 插入宠物 */
    int insert(Pet pet);

    /** 更新宠物 */
    int update(Pet pet);

    /** 删除宠物 */
    int deleteById(@Param("id") Long id);

    /** 按分类统计宠物数量 */
    List<Map<String, Object>> countByCategory();

    /** 统计总宠物数 */
    long countTotal();
}
