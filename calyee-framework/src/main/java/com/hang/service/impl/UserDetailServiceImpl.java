package com.hang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hang.constants.SystemConstants;
import com.hang.entity.LoginUser;
import com.hang.entity.User;
import com.hang.mapper.MenuMapper;
import com.hang.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName UserDetailServiceImpl
 * @Description 让Spring Security
 *                 自定义UserDetailsService
 *                 在这个实现类中去查询数据库
 * @Author QiuLiHang
 * @DATE 2023/8/6 11:34
 * @Version 1.0
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    /**
     * 用户提交的用户名
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 传进来用户名
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        // 判断是否查询到用户 如果没查到 抛出异常(sysUser == null)
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("不存在此用户");
        }
        // 返回用户信息
        // TODO 查询权限信息封装
        // TODO 如果是后台用户才需要查询权限封装
        if(SystemConstants.ADMIN.equals(user.getType())){
            List<String> perms = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,perms);
        }
        return new LoginUser(user,null);
    }
}
