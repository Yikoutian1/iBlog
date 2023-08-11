package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.dto.TagListDto;
import com.hang.entity.Tag;
import com.hang.result.ResponseResult;
import com.hang.vo.PageVo;
import com.hang.vo.TagVo;

import java.util.List;


/**
 * (Tag)表服务接口
 *
 * @author makejava
 * @since 2023-08-09 10:32:28
 */
public interface TagService extends IService<Tag> {
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    boolean addTag(TagListDto tagListDto);

    void deleteTagById(List<Long> id);

    ResponseResult<TagVo> selectById(Long id);

    ResponseResult updateTagInfo(TagListDto tagListDto);

    List<TagVo> listAllTag();

    void saveToArticleTag(Long articleId, List<Long> tags);
}

