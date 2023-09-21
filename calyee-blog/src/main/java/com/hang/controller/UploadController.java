package com.hang.controller;

import com.hang.result.ResponseResult;
import com.hang.service.UploadService;
import com.hang.service.impl.UploadPhotoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadController
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/7 19:10
 * @Version 1.0
 */
@RestController
@Api(tags = "上传文件相关接口")
public class UploadController {
    @Autowired
    @Qualifier("ossUploadServiceImpl")
    private UploadService uploadService;
    @Autowired
    @Qualifier("uploadPhotoService")
    private UploadPhotoService uploadPhotoService;

//    @PostMapping("/upload")
//    public ResponseResult uploadImg(MultipartFile img) {
//        return uploadService.uploadImg(img);
//    }
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img) {
        return uploadPhotoService.uploadImg(img);
    }
}
