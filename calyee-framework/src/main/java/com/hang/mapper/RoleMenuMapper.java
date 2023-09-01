package com.hang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hang.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;


/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-01 10:13:29
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}

