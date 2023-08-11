package com.hang.service;

import com.hang.entity.User;
import com.hang.result.ResponseResult;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
