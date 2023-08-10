package com.hang.vo;

import com.hang.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName AdminUserInfoVo
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/10 14:23
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {

    private List<String> permissions;

    private List<String> roles;

    private UserInfoVo userInfoVo;
}
