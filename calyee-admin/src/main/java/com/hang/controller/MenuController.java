package com.hang.controller;



import com.hang.entity.Menu;
import com.hang.mapper.MenuMapper;
import com.hang.result.ResponseResult;
import com.hang.service.MenuService;
import com.hang.utils.SystemConverter;
import com.hang.vo.MenuTreeVo;
import com.hang.vo.RoleMenuTreeSelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单权限表(Menu)表控制层
 *
 * @author makejava
 * @since 2023-08-31 20:54:04
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController{
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuMapper mapper;
    @GetMapping("/list")
    public ResponseResult listMenu(String status,String menuName){
        return menuService.listMenu(status,menuName);
    }
    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }
    @GetMapping("/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id){
        return ResponseResult.okResult(menuService.getById(id));
    }
    @PutMapping
    public ResponseResult edit(@RequestBody Menu menu){
        return menuService.edit(menu);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteMenu(@PathVariable("id")Long id){
        return menuService.deleteMenu(id);
    }
    @GetMapping("/treeselect")
    public ResponseResult treeselect(){
        return menuService.treeselect();
    }
    //---------------------修改角色-根据角色id查询对应角色菜单列表树--------------------------

    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = mapper.selectAllRouterMenu();
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }
}

