package com.hang.controller;



import com.hang.annotation.SystemLog;
import com.hang.dto.UserInfoDTO;
import com.hang.entity.User;
import com.hang.result.ResponseResult;
import com.hang.service.UserService;
import com.hang.vo.UserInfoVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户表(SysUser)表控制层
 *
 * @author makejava
 * @since 2023-08-07 11:02:47
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController{
    @Autowired
    private UserService userService;
    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }
    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        return userService.regiseter(user);
    }
}

