package com.hang.controller;

import com.hang.entity.LoginUser;
import com.hang.entity.Menu;
import com.hang.entity.User;
import com.hang.enums.AppHttpCodeEnum;
import com.hang.exception.SystemException;
import com.hang.result.ResponseResult;
import com.hang.service.LoginService;
import com.hang.service.MenuService;
import com.hang.service.RoleService;
import com.hang.utils.BeanCopyUtils;
import com.hang.utils.SecurityUtils;
import com.hang.vo.AdminUserInfoVo;
import com.hang.vo.RoutersVo;
import com.hang.vo.UserInfoVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @ClassName LoginController
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/9 11:29
 * @Version 1.0
 */
@RestController
@Api(tags = "后台登录相关接口")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    /**
     * 获取权限列表
     *
     * @return
     */
    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    /**
     * 路由获取
     *
     * @return
     */
    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters() {
        // 获取当前登录的用户
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> routersVoList = menuService.selectRouterMenuToTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(routersVoList));
    }

    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }
}
