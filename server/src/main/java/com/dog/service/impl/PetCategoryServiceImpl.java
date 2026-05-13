package com.dog.service.impl;

import com.dog.common.BusinessException;
import com.dog.common.ResultCode;
import com.dog.entity.PetCategory;
import com.dog.mapper.PetCategoryMapper;
import com.dog.service.PetCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 宠物分类服务实现
 */
@Service
public class PetCategoryServiceImpl implements PetCategoryService {

    private final PetCategoryMapper categoryMapper;

    public PetCategoryServiceImpl(PetCategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<PetCategory> listAll() {
        return categoryMapper.selectAll();
    }

    @Override
    public PetCategory getById(Long id) {
        PetCategory category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "分类不存在");
        }
        return category;
    }

    @Override
    public void save(PetCategory category) {
        categoryMapper.insert(category);
    }

    @Override
    public void update(PetCategory category) {
        PetCategory exist = getById(category.getId());
        categoryMapper.update(category);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        categoryMapper.deleteById(id);
    }
}
