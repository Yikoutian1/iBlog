package com.hang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hang.entity.LoginUser;
import com.hang.entity.SysUser;
import com.hang.mapper.SysUserMapper;
import com.hang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    private SysUserMapper sysUserMapper;
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
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName,username);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        // 判断是否查询到用户 如果没查到 抛出异常(sysUser == null)
        if(Objects.isNull(sysUser)){
            throw new UsernameNotFoundException("不存在此用户");
        }
        // 返回用户信息
        // TODO 查询权限信息封装
        return new LoginUser(sysUser);
    }
}
