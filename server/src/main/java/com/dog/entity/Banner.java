package com.dog.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 轮播图实体类
 */
@Data
public class Banner {

    /** 轮播图ID */
    private Long id;

    /** 标题 */
    private String title;

    /** 图片URL */
    private String image;

    /** 跳转链接 */
    private String link;

    /** 排序 */
    private Integer sortOrder;

    /** 状态：0禁用 1启用 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;
}
