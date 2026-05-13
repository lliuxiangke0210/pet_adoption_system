package com.dog.dto;

import lombok.Data;

/**
 * 分页请求参数
 */
@Data
public class PageRequest {

    /** 当前页码 */
    private Integer pageNum = 1;

    /** 每页大小 */
    private Integer pageSize = 10;
}
