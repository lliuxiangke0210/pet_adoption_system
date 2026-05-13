package com.dog.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 宠物分类实体类
 */
@Data
public class PetCategory {

    /** 分类ID */
    private Long id;

    /** 分类名称 */
    private String name;

    /** 分类描述 */
    private String description;

    /** 创建时间 */
    private LocalDateTime createTime;
}
