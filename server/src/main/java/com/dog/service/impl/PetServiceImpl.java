package com.dog.service.impl;

import com.dog.common.BusinessException;
import com.dog.common.ResultCode;
import com.dog.entity.Pet;
import com.dog.mapper.PetMapper;
import com.dog.service.PetService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 宠物服务实现
 */
@Service
public class PetServiceImpl implements PetService {

    private final PetMapper petMapper;

    public PetServiceImpl(PetMapper petMapper) {
        this.petMapper = petMapper;
    }

    @Override
    public Pet getById(Long id) {
        Pet pet = petMapper.selectById(id);
        if (pet == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "宠物不存在");
        }
        return pet;
    }

    @Override
    public List<Pet> list(String keyword, Long categoryId, String status, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return petMapper.selectList(keyword, categoryId, status, offset, pageSize);
    }

    @Override
    public long count(String keyword, Long categoryId, String status) {
        return petMapper.countList(keyword, categoryId, status);
    }

    @Override
    public void save(Pet pet) {
        if (pet.getStatus() == null) {
            pet.setStatus("available");
        }
        petMapper.insert(pet);
    }

    @Override
    public void update(Pet pet) {
        getById(pet.getId());
        petMapper.update(pet);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        petMapper.deleteById(id);
    }

    @Override
    public long countTotal() {
        return petMapper.countTotal();
    }
}
