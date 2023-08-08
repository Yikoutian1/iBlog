package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.dto.UserInfoDTO;
import com.hang.entity.User;
import com.hang.result.ResponseResult;
import com.hang.vo.UserInfoVo;


/**
 * 用户表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2023-08-07 11:02:47
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult regiseter(User user);
}

