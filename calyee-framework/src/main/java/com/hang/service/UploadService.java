package com.hang.service;

import com.hang.result.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadService
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/7 19:12
 * @Version 1.0
 */

public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
