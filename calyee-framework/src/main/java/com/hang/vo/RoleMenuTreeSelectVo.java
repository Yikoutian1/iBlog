package com.hang.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName RoleMenuTreeSelectVo
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/9/1 10:52
 * @Version 1.0
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuTreeSelectVo {

    private List<Long> checkedKeys;

    private List<MenuTreeVo> menus;
}