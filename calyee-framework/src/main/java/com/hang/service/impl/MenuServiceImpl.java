package com.hang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hang.constants.SystemConstants;
import com.hang.entity.Menu;
import com.hang.mapper.MenuMapper;
import com.hang.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-08-10 14:25:53
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    /**
     * 根据用户id查询菜单权限
     *
     * @param id 用户id
     * @return 权限集合
     */
    @Override
    public List<String> selectRermsByUserId(Long id) {
        // 如果是管理员 则返回所有的权限列表
        if (Objects.equals(id, SystemConstants.ADMIN)) {
            // 返回表中的字段menu_type为 C或F的 然后status为0的(正常的),未被删除的
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON)
                    .eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        // 否则返回具有的权限
        return baseMapper.selectPermsByUserId(id);
    }
}

