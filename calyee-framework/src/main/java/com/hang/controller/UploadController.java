package com.hang.controller;

import com.hang.result.ResponseResult;
import com.hang.service.UploadService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UploadService uploadService;
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
