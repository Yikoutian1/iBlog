package com.hang.service.impl;

import com.hang.entity.LoginUser;
import com.hang.entity.SysUser;
import com.hang.result.ResponseResult;
import com.hang.service.BlogLoginService;
import com.hang.utils.JwtUtil;
import com.hang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @ClassName BlogLoginServiceImpl
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/6 11:24
 * @Version 1.0
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    // 注入之前还没有到容器,需要配置
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(SysUser sysUser) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUserName(),sysUser.getPassword());
        // 默认在内存中查询 ,需要重写UserDetailService让其在数据库查
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        // 获取用户id,生成jwt token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getSysUser().getId().toString();
        JwtUtil.createJWT(userId);
        // 缓存到redis
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);
        // 把token和userinfo封装 返回

        return ResponseResult.okResult();
    }
}
