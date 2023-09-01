package com.hang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hang.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-01 11:12:19
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

