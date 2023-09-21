package com.hang.service.impl;

import com.alibaba.excel.converters.string.StringErrorConverter;
import com.hang.enums.AppHttpCodeEnum;
import com.hang.enums.OptionEnum;
import com.hang.exception.SystemException;
import com.hang.result.ResponseResult;
import com.hang.service.UploadService;
import com.hang.utils.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/**
 * @ClassName UploadPhoto
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/9/21 021 20:23
 * @Version 1.0
 */
@Service
@Slf4j
public class UploadPhotoService implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        // TODO 判断文件类型 或者 文件大小
        //判断文件类型
        // 获取文件名进行判断
        String originalFilename = img.getOriginalFilename();
        // 对原始文件名进行判断
        if (!originalFilename.endsWith(".png")
                && !originalFilename.endsWith(".jpg")) {
            throw new SystemException(AppHttpCodeEnum.FILENAME_ERROR);
        }
        // 判断通过 上传文件到本地
        String filePath = PathUtils.generateFilePath(originalFilename);
        // 现在是已经拿到了随机名 filePath
        String url = uploadLocal(img, filePath); // 2023/8/7/uuid.png
        return ResponseResult.okResult(url);
    }

    @Value("${file.localUrl}")
    private String localUrl;
    @Value("${file.linuxUrl}")
    private String linuxUrl;

    // 上传文件的方法
    private String uploadLocal(MultipartFile img, String filePath) {
//        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
//        // 获取到运行程序的目录
//        String pre = applicationHome.getDir().getParentFile().getAbsolutePath()
//                + "\\src\\main\\resources\\images\\";
        String os = System.getProperty("os.name").toLowerCase();
        log.info("上传接口执行:当前系统为{}", os);
        String url = null;
        // 默认是linux
        if (os.contains(OptionEnum.Linux.toString())) {// linux
            url = linuxUrl;
        } else if (os.contains(OptionEnum.Windows.toString())) { // windows
            url = localUrl;
        }else {// 不支持
            throw new SystemException(AppHttpCodeEnum.UPLOAD_ERROR);
        }
        // 合并路径
        String path = url + filePath;
        // E:/Ai/images/2023/09/21/59152836e9db4caaa9cf9ed15ea6c4ce.png
        log.info("上传的路径为 => {}", path);
        // TODO 上传图片
        File target = new File(path);
        // 如果不存在 则创建文件夹
        if (!target.exists()) {
            target.mkdirs();
        }
        try {
            // 写入文件
            img.transferTo(target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return target.getAbsolutePath();
    }
}
