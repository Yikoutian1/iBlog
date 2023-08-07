package com.hang.controller;

import com.hang.entity.User;
import com.hang.enums.AppHttpCodeEnum;
import com.hang.exception.SystemException;
import com.hang.result.ResponseResult;
import com.hang.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BlogLoginController
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/6 11:19
 * @Version 1.0
 */
@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            // 提示必须传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    /**
     * 通过token退出登录
     * @return
     */
    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
