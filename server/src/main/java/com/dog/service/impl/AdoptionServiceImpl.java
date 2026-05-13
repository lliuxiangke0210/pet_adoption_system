package com.dog.service.impl;

import com.dog.common.BusinessException;
import com.dog.common.ResultCode;
import com.dog.entity.Adoption;
import com.dog.entity.Pet;
import com.dog.mapper.AdoptionMapper;
import com.dog.mapper.PetMapper;
import com.dog.service.AdoptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 领养服务实现
 */
@Service
public class AdoptionServiceImpl implements AdoptionService {

    private final AdoptionMapper adoptionMapper;
    private final PetMapper petMapper;

    public AdoptionServiceImpl(AdoptionMapper adoptionMapper, PetMapper petMapper) {
        this.adoptionMapper = adoptionMapper;
        this.petMapper = petMapper;
    }

    @Override
    @Transactional
    public void apply(Adoption adoption) {
        Pet pet = petMapper.selectById(adoption.getPetId());
        if (pet == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "宠物不存在");
        }
        if (!"available".equals(pet.getStatus())) {
            throw new BusinessException("该宠物已被领养或正在处理中");
        }
        adoption.setStatus("pending");
        adoptionMapper.insert(adoption);
        // 将宠物状态改为待审核
        pet.setStatus("pending");
        petMapper.update(pet);
    }

    @Override
    @Transactional
    public void approve(Long id, String status) {
        Adoption adoption = adoptionMapper.selectById(id);
        if (adoption == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "领养记录不存在");
        }
        adoption.setStatus(status);
        adoption.setApproveTime(LocalDateTime.now());
        adoptionMapper.update(adoption);

        // 同步更新宠物状态
        Pet pet = petMapper.selectById(adoption.getPetId());
        if (pet != null) {
            if ("approved".equals(status)) {
                pet.setStatus("adopted");
            } else if ("rejected".equals(status)) {
                pet.setStatus("available");
            }
            petMapper.update(pet);
        }
    }

    @Override
    public List<Adoption> list(String keyword, String status, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return adoptionMapper.selectList(keyword, status, offset, pageSize);
    }

    @Override
    public long count(String keyword, String status) {
        return adoptionMapper.countList(keyword, status);
    }

    @Override
    public List<Adoption> listByUserId(Long userId) {
        return adoptionMapper.selectByUserId(userId);
    }

    @Override
    public long countPending() {
        return adoptionMapper.countPending();
    }

    @Override
    public long countTotal() {
        return adoptionMapper.countTotal();
    }
}
