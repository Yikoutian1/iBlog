package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.entity.Menu;
import com.hang.result.ResponseResult;
import com.hang.vo.RoutersVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-08-10 14:25:53
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuToTreeByUserId(Long userId);

    ResponseResult listMenu(String status, String menuName);

    ResponseResult edit(Menu menu);

    ResponseResult deleteMenu(Long id);

    ResponseResult treeselect();

    //修改角色-根据角色id查询对应角色菜单列表树
    List<Long> selectMenuListByRoleId(Long roleId);
}

