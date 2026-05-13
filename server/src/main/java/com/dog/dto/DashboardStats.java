package com.dog.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 仪表盘统计数据
 */
@Data
public class DashboardStats {

    /** 总用户数 */
    private Long totalUsers;

    /** 总宠物数 */
    private Long totalPets;

    /** 待审批领养数 */
    private Long pendingAdoptions;

    /** 总领养数 */
    private Long totalAdoptions;

    /** 每月新增用户数（月份 -> 数量） */
    private List<Map<String, Object>> userRegistrationsByMonth;

    /** 每月领养数（月份 -> 数量） */
    private List<Map<String, Object>> adoptionsByMonth;

    /** 宠物分类分布（分类名 -> 数量） */
    private List<Map<String, Object>> petsByCategory;
}
