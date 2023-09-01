package com.hang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hang.constants.SystemConstants;
import com.hang.entity.Menu;
import com.hang.mapper.MenuMapper;
import com.hang.result.ResponseResult;
import com.hang.service.MenuService;
import com.hang.utils.BeanCopyUtils;
import com.hang.utils.SystemConverter;
import com.hang.vo.MenuTreeVo;
import com.hang.vo.RoutersVo;
import com.hang.vo.SysMenuVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员 则返回所有的权限列表
        if (Objects.equals(id, SystemConstants.ADMIN)) {
            // 返回表中的字段menu_type为 C或F的 然后status为0的(正常的),未被删除的
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON)
                    .eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        // 否则返回具有的权限
        return baseMapper.selectPermsByUserId(id);
    }

    /**
     * 查询权限菜单
     * @param status
     * @param menuName
     * @return
     */
    @Override
    public ResponseResult listMenu(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status),Menu::getStatus,status)
                .like(StringUtils.hasText(menuName),Menu::getMenuName,menuName);
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        List<SysMenuVo> sysMenuVos = BeanCopyUtils.copyBeanList(menus, SysMenuVo.class);
        return ResponseResult.okResult(sysMenuVos);
    }

    @Override
    public ResponseResult edit(Menu menu) {
        // 自己选择自己
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        baseMapper.updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long id) {
        if(hasChild(id)){
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }
    //-------------------------------获取菜单树接口-------------------------------
    @Override
    public ResponseResult treeselect() {
        //复用之前的selectAllRouterMenu方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<Menu> menus = baseMapper.selectAllRouterMenu();
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        return getBaseMapper().selectMenuListByRoleId(roleId);
    }
    //-------------------------------删除菜单-是否存在子菜单-------------------------------

    private boolean hasChild(Long menuId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);
        return count(queryWrapper) != 0;
    }
    @Override
    public List<Menu> selectRouterMenuToTreeByUserId(Long userId) {
        List<Menu> menusList = null;
        // 判断是否是管理员
        if (Objects.equals(userId, SystemConstants.ADMIN)) {
            // 如果是 返回所有符合要求的Menu
            menusList = baseMapper.selectAllRouterMenu();

        } else {
            // 否则 当前用户具有的Menu
            menusList = baseMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 构建Tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menusList, 0L);
        return menuTree;
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                // 把第一层级的给筛选出来
                .filter(menu -> menu.getParentId().equals(parentId))
                // 拿到子children
                .map(menu -> menu.setChildren(getChildren(menu,menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                // 先过滤当前层级
                .filter(m -> m.getParentId().equals(menu.getId()))
                // 递归找子children
                .map(m -> m.setChildren(getChildren(m, menus)))
                // 收集起来
                .collect(Collectors.toList());
        return childrenList;
    }


    // 俩种方法
    /*之前的权限系统的*/
    /**
     * 构建树形结构
     * @param menusList
     * @return
     */
    /*
    private List<Menu> buildMenuTree(List<Menu> menusList) {
        // 创建集合封装最终数据
        List<Menu> trees =  new ArrayList<>();
        // 遍历所有菜单集合
        for (Menu menu:menusList) {
            // 找到递归入口,parent_id = 0,顶层管理员数据
            if(menu.getParentId().longValue() == 0){
                trees.add(findChildren(menu,menusList));
            }
        }
        return trees;
    }
    // 从根结点进行递归查询,查询子节点
    // 判断id 是否等于 parent_id , 如果相同就是子节点,那么进行封装
    public static Menu findChildren(Menu sysMenu,
                                       List<Menu> sysMenuList){
        // 孩子数据初始化
        sysMenu.setChildren(new ArrayList<>());
        // 遍历递归进行查找
        for(Menu it:sysMenuList){
            // 获取当前菜单id
            Long id = sysMenu.getId();
            // 获取所有菜单parent_id
            Long parent_id = it.getParentId();
            // 进行比对,左边id跟右边一列的parent_id进行一一比对
            if(id.equals(parent_id)){
                // 如果孩子节点为空,那么则创建孩子
                if(sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                // 递归进行查找
                sysMenu
                        .getChildren()
                        .add(findChildren(it,sysMenuList));
            }
        }
        // 递归结束,数据返回
        return sysMenu;
    }
    */
}

