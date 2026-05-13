package com.dog.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 领养记录实体类
 */
@Data
public class Adoption {

    /** 领养ID */
    private Long id;

    /** 申请人ID */
    private Long userId;

    /** 宠物ID */
    private Long petId;

    /** 领养理由 */
    private String reason;

    /** 状态：pending/approved/rejected */
    private String status;

    /** 申请时间 */
    private LocalDateTime applyTime;

    /** 审批时间 */
    private LocalDateTime approveTime;

    // ======== 以下字段为关联查询时使用 ========

    /** 申请人用户名 */
    private String username;

    /** 申请人真实姓名 */
    private String realName;

    /** 宠物名称 */
    private String petName;

    /** 宠物图片 */
    private String petImage;

    /** 宠物品种 */
    private String petBreed;
}
