package com.hang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hang.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-06 11:11:29
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

