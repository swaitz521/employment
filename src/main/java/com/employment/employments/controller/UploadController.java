package com.employment.employments.controller;

import com.employment.employments.service.OssService;
import com.employment.employments.util.QiniuUtils;
import com.employment.employments.util.Result;
import net.bytebuddy.implementation.bind.annotation.Origin;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController

@RequestMapping("upload")
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;
    @Autowired
    private OssService ossService;
    @PostMapping
    public Result upload(@RequestParam("file") MultipartFile file){

        if(file.isEmpty()){
            return Result.error().msg("上传失败");
        }
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //唯一文件名
        String fileName= UUID.randomUUID().toString()+"."+ StringUtils.substringAfterLast(originalFilename,".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if(upload){
            return Result.success(QiniuUtils.url+fileName);
        }
        return Result.error().msg("照片上传失败");
    }
    @PostMapping("oss")
    public Result uploadFile(MultipartFile file){
        String url=ossService.uploadFileAvatar(file);
        return Result.success(url);
    }
}
