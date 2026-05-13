package com.dog.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评论实体类
 */
@Data
public class Comment {

    /** 评论ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 宠物ID（可为空） */
    private Long petId;

    /** 资讯ID（可为空） */
    private Long newsId;

    /** 评论内容 */
    private String content;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 用户名（关联查询时填充） */
    private String username;
}
