package com.hang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hang.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-09 10:32:28
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}

