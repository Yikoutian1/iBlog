package com.hang.controller;

import com.hang.result.ResponseResult;
import com.hang.service.UploadService;
import com.hang.service.impl.UploadPhotoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName UploadController
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/7 19:10
 * @Version 1.0
 */
@RestController
public class UploadController {

    @Autowired
    @Qualifier("ossUploadServiceImpl")
    private UploadService uploadService;
    @Autowired
    @Qualifier("uploadPhotoService")
    private UploadPhotoService uploadPhotoService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile) {
        try {
            return uploadPhotoService.uploadImg(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传上传失败");
        }
    }
}
