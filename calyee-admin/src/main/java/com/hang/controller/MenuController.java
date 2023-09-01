package com.hang.controller;



import com.hang.entity.Menu;
import com.hang.result.ResponseResult;
import com.hang.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}

