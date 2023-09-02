package com.hang.utils;

import com.hang.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @ClassName SecurityUtils
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/7 14:16
 * @Version 1.0
 */

//在'发送评论'功能那里会用到的工具类
public class SecurityUtils {

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof LoginUser) {
            return (LoginUser) authentication.getPrincipal();
        }
        return null;
    }


    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 指定userid为1的用户就是网站管理员
     *
     * @return
     */
    public static Boolean isAdmin() {
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    /**
     * 获取用户id
     * @return
     */
    public static Long getUserId() {
        LoginUser loginUser = getLoginUser();
        if (loginUser != null && loginUser.getUser() != null) {
            return loginUser.getUser().getId();
        }
        return null;
    }
}