package com.dog.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传服务接口
 */
public interface FileService {

    /**
     * 上传文件
     * @param file 上传的文件
     * @return 文件访问URL路径
     */
    String upload(MultipartFile file);
}
