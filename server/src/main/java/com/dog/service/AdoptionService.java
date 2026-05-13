package com.dog.service;

import com.dog.entity.Adoption;
import java.util.List;

/**
 * 领养服务接口
 */
public interface AdoptionService {

    /** 提交领养申请 */
    void apply(Adoption adoption);

    /** 审批领养 */
    void approve(Long id, String status);

    /** 分页查询领养记录 */
    List<Adoption> list(String keyword, String status, int pageNum, int pageSize);

    /** 统计领养记录数 */
    long count(String keyword, String status);

    /** 查询用户的领养记录 */
    List<Adoption> listByUserId(Long userId);

    /** 统计待审批数 */
    long countPending();

    /** 统计总领养数 */
    long countTotal();
}
