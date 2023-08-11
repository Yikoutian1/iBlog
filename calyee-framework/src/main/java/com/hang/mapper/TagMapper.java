package com.hang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hang.dto.TagListDto;
import com.hang.entity.Tag;
import com.hang.result.ResponseResult;
import com.hang.vo.TagVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;


/**
 * (Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-09 10:32:28
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    TagVo selectByIdMapper(@Param("id") Long id);

    void updateTagInfo(@Param("tag") Tag tag);

    void insertArticleTags(@Param("articleId") Long articleId, @Param("item") Long item);
}

