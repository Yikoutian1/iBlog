package com.hang.controller;


import com.hang.dto.ChangeRoleStatusDto;
import com.hang.entity.Role;
import com.hang.result.ResponseResult;
import com.hang.service.RoleService;
import com.hang.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色信息表(Role)表控制层
 *
 * @author makejava
 * @since 2023-09-01 09:51:35
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult listByPageInfo(Integer pageNum, Integer pageSize,
                                         String roleName, String status
    ) {
        return roleService.listByPageInfo(pageNum,pageSize,roleName,status);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto roleStatusDto){
        return roleService.changeStatus(roleStatusDto);
    }
    //-------------------------------新增角色-----------------------------------------

    @PostMapping
    public ResponseResult add( @RequestBody Role role) {
        roleService.insertRole(role);
        return ResponseResult.okResult();
    }
    //----------------------修改角色-根据角色id查询对应的角色-----------------------------

    @GetMapping(value = "/{roleId}")
    public ResponseResult getInfo(@PathVariable Long roleId) {
        Role role = roleService.getById(roleId);
        return ResponseResult.okResult(role);
    }

    //-------------------------修改角色-保存修改好的角色信息------------------------------

    @PutMapping
    public ResponseResult edit(@RequestBody Role role) {
        roleService.updateRole(role);
        return ResponseResult.okResult();
    }
    //--------------------------------删除角色---------------------------------------

    @DeleteMapping("/{id}")
    public ResponseResult remove(@PathVariable(name = "id") Long id) {
        roleService.removeById(id);
        return ResponseResult.okResult();
    }
}

