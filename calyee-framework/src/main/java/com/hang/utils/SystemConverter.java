package com.hang.utils;

import com.hang.entity.Menu;
import com.hang.vo.MenuTreeVo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName SystemConverter
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/9/1 10:27
 * @Version 1.0
 */

//新增角色-获取菜单下拉树列表
public class SystemConverter {

    private SystemConverter() {}

    public static List<MenuTreeVo> buildMenuSelectTree(List<Menu> menus) {
        List<MenuTreeVo> MenuTreeVos = menus.stream()
                .map(m -> new MenuTreeVo(m.getId(), m.getMenuName(), m.getParentId(), null))
                .collect(Collectors.toList());
        List<MenuTreeVo> options = MenuTreeVos.stream()
                .filter(o -> o.getParentId().equals(0L))
                .map(o -> o.setChildren(getChildList(MenuTreeVos, o)))
                .collect(Collectors.toList());


        return options;
    }


    /**
     * 得到子节点列表
     */
    private static List<MenuTreeVo> getChildList(List<MenuTreeVo> list, MenuTreeVo option) {
        List<MenuTreeVo> options = list.stream()
                .filter(o -> Objects.equals(o.getParentId(), option.getId()))
                .map(o -> o.setChildren(getChildList(list, o)))
                .collect(Collectors.toList());
        return options;

    }
}