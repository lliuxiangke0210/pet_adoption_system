package com.dog.mapper;

import com.dog.entity.Adoption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 领养记录数据访问层
 */
@Mapper
public interface AdoptionMapper {

    /** 根据ID查询 */
    Adoption selectById(@Param("id") Long id);

    /** 分页查询领养记录（管理端） */
    List<Adoption> selectList(@Param("keyword") String keyword, @Param("status") String status,
                              @Param("offset") int offset, @Param("limit") int limit);

    /** 统计领养记录数 */
    long countList(@Param("keyword") String keyword, @Param("status") String status);

    /** 查询用户的领养记录 */
    List<Adoption> selectByUserId(@Param("userId") Long userId);

    /** 插入领养记录 */
    int insert(Adoption adoption);

    /** 更新领养记录（审批） */
    int update(Adoption adoption);

    /** 按月统计领养数 */
    List<Map<String, Object>> countByMonth(@Param("year") int year);

    /** 统计待审批数 */
    long countPending();

    /** 统计领养总数 */
    long countTotal();
}
