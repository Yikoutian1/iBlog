package com.hang.vo;

import com.hang.entity.Role;
import com.hang.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName UserInfoAndRoleIdsVo
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/9/1 11:16
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndRoleIdsVo {
    private User user;
    private List<Role> roles;
    private List<Long> roleIds;
}