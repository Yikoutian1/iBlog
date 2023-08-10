package com.hang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hang.constants.SystemConstants;
import com.hang.entity.Role;
import com.hang.mapper.RoleMapper;
import com.hang.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-08-10 14:29:52
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    /**
     * 根据用户id查询 角色信息
     * @param id    用户id
     * @return      角色信息
     */
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 判断是否是管理员 如果是返回集合中只需要有admin
        if(Objects.equals(id, SystemConstants.ADMIN)){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}

