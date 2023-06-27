package com.employment.employments.service;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
@Repository
public interface OssService {
    /**
     * 上传头像到oss
     * @param file 文件对象
     * @return
     */
    String uploadFileAvatar(MultipartFile file);
}
