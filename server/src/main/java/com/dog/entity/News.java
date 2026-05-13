package com.dog.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 宠物资讯实体类
 */
@Data
public class News {

    /** 资讯ID */
    private Long id;

    /** 标题 */
    private String title;

    /** 内容（HTML富文本） */
    private String content;

    /** 封面图URL */
    private String coverImage;

    /** 作者 */
    private String author;

    /** 浏览次数 */
    private Integer viewCount;

    /** 创建时间 */
    private LocalDateTime createTime;
}
