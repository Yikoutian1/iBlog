package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-08-10 14:29:52
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}

