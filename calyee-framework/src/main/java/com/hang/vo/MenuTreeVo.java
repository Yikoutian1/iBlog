package com.hang.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName MenuTreeVo
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/9/1 10:11
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
//新增角色-获取菜单下拉树列表
public class MenuTreeVo {

    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点名称
     */
    private String label;

    private Long parentId;

    /**
     * 子节点
     */
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuTreeVo> children;
}