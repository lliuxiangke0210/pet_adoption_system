package com.dog.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 宠物实体类
 */
@Data
public class Pet {

    /** 宠物ID */
    private Long id;

    /** 宠物名称 */
    private String name;

    /** 分类ID */
    private Long categoryId;

    /** 品种 */
    private String breed;

    /** 年龄（月） */
    private Integer age;

    /** 性别：公/母 */
    private String gender;

    /** 毛色 */
    private String color;

    /** 体型：小型/中型/大型 */
    private String size;

    /** 描述信息 */
    private String description;

    /** 图片URL */
    private String image;

    /** 状态：available/pending/adopted */
    private String status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 分类名称（非数据库字段，查询时填充） */
    private String categoryName;
}
