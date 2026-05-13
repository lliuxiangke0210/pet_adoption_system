package com.dog.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户反馈实体类
 */
@Data
public class Feedback {

    /** 反馈ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 反馈内容 */
    private String content;

    /** 管理员回复 */
    private String reply;

    /** 状态：pending/replied */
    private String status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 用户名（关联查询时填充） */
    private String username;
}
