package com.hang.service;

import com.hang.entity.SysUser;
import com.hang.result.ResponseResult;

/**
 * @ClassName BlogLoginService
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/6 11:22
 * @Version 1.0
 */

public interface BlogLoginService {
    public ResponseResult login(SysUser sysUser);
}
