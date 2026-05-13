package com.dog.service.impl;

import com.dog.common.BusinessException;
import com.dog.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 文件上传服务实现
 */
@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public String upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }

        try {
            // 确保上传目录存在
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFilename = UUID.randomUUID().toString() + extension;

            // 保存文件
            Path targetPath = uploadDir.resolve(newFilename);
            file.transferTo(targetPath.toFile());

            // 返回访问URL
            return "/uploads/" + newFilename;
        } catch (IOException e) {
            throw new BusinessException("文件上传失败: " + e.getMessage());
        }
    }
}
