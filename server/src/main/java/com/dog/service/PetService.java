package com.dog.service;

import com.dog.entity.Pet;
import java.util.List;

/**
 * 宠物服务接口
 */
public interface PetService {

    /** 根据ID查询宠物详情 */
    Pet getById(Long id);

    /** 分页查询宠物列表 */
    List<Pet> list(String keyword, Long categoryId, String status, int pageNum, int pageSize);

    /** 统计宠物总数 */
    long count(String keyword, Long categoryId, String status);

    /** 添加宠物 */
    void save(Pet pet);

    /** 更新宠物 */
    void update(Pet pet);

    /** 删除宠物 */
    void delete(Long id);

    /** 统计总宠物数 */
    long countTotal();
}
