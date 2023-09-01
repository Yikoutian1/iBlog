package com.hang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hang.constants.SystemConstants;
import com.hang.dto.ChangeRoleStatusDto;
import com.hang.entity.Role;
import com.hang.entity.RoleMenu;
import com.hang.mapper.RoleMapper;
import com.hang.result.ResponseResult;
import com.hang.service.RoleMenuService;
import com.hang.service.RoleService;
import com.hang.utils.BeanCopyUtils;
import com.hang.vo.PageVo;
import com.hang.vo.RoleVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-08-10 14:29:52
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 根据用户id查询 角色信息
     *
     * @param id 用户id
     * @return 角色信息
     */
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 判断是否是管理员 如果是返回集合中只需要有admin
        if (Objects.equals(id, SystemConstants.ADMIN)) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult listByPageInfo(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleName), Role::getRoleName, roleName)
                .eq(StringUtils.hasText(status), Role::getStatus, status);
        queryWrapper.orderByAsc(Role::getRoleSort);
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(page.getRecords(), RoleVo.class);
        return ResponseResult.okResult(new PageVo(roleVos, page.getTotal()));
    }

    @Override
    public ResponseResult changeStatus(ChangeRoleStatusDto roleStatusDto) {
        Role role = new Role();
        role.setStatus(roleStatusDto.getStatus());
        role.setId(roleStatusDto.getRoleId());
        baseMapper.updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public void insertRole(Role role) {
        save(role);
//        System.out.println(role.getId());
        if (role.getMenuIds() != null && role.getMenuIds().length > 0) {
            insertRoleMenu(role);
        }
    }

    //-----------------------修改角色-保存修改好的角色信息----------------------------

    @Override
    public void updateRole(Role role) {
        updateById(role);
        roleMenuService.deleteRoleMenuByRoleId(role.getId());
        insertRoleMenu(role);
    }

    private void insertRoleMenu(Role role) {
        // 收集当前角色的所有菜单id,然后new成集合,批量插入
        List<RoleMenu> roleMenuList = Arrays.stream(role.getMenuIds())
                .map(memuId -> new RoleMenu(role.getId(), memuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
    }
}

