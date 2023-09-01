package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.dto.ChangeRoleStatusDto;
import com.hang.entity.Role;
import com.hang.result.ResponseResult;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-08-10 14:29:52
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult listByPageInfo(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(ChangeRoleStatusDto roleStatusDto);

    //新增角色
    void insertRole(Role role);

    //修改角色-保存修改好的角色信息
    void updateRole(Role role);

    //新增用户-①查询角色列表接口
    List<Role> selectRoleAll();

    //修改用户-①根据id查询用户信息
    List<Long> selectRoleIdByUserId(Long userId);
}

