package com.dog.service;

import com.dog.entity.PetCategory;
import java.util.List;

/**
 * 宠物分类服务接口
 */
public interface PetCategoryService {

    /** 查询所有分类 */
    List<PetCategory> listAll();

    /** 根据ID查询 */
    PetCategory getById(Long id);

    /** 添加分类 */
    void save(PetCategory category);

    /** 更新分类 */
    void update(PetCategory category);

    /** 删除分类 */
    void delete(Long id);
}
