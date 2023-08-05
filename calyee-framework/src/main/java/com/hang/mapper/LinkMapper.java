package com.hang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hang.entity.Link;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Link)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-05 20:33:01
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}

