package com.hang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hang.dto.TagListDto;
import com.hang.entity.Tag;
import com.hang.mapper.TagMapper;
import com.hang.result.ResponseResult;
import com.hang.service.TagService;
import com.hang.utils.BeanCopyUtils;
import com.hang.utils.SecurityUtils;
import com.hang.vo.PageVo;
import com.hang.vo.TagVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * (Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-08-09 10:32:28
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        Page<Tag> page = new Page<>();
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName())
                .like(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());
        // 分页
        page.setCurrent(pageNum);
        page.setPages(pageSize);
        page(page, queryWrapper);
        List<Tag> records = page.getRecords();
        PageVo pageVo = new PageVo(records, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public boolean addTag(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        if(StringUtils.hasText(tagListDto.getRemark())&&StringUtils.hasText(tagListDto.getName())){
            Long createAndUpdateBy = SecurityUtils.getUserId();
            tag.setCreateBy(createAndUpdateBy);
            Date data = new Date();
            tag.setCreateTime(data);
            tag.setUpdateTime(data);
            tag.setUpdateBy(createAndUpdateBy);
            save(tag);
            return true;
        }
        return false;
    }
    @Override
    public ResponseResult updateTagInfo(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        Long updateUserId = SecurityUtils.getUserId();
        tag.setUpdateBy(updateUserId);
        tag.setUpdateTime(new Date());
        baseMapper.updateTagInfo(tag);
        return ResponseResult.okResult();
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return tagVos;
    }

    /**
     * 插入tag 和 article 关系表
     * @param articleId
     * @param tags
     */
    @Override
    public void saveToArticleTag(Long articleId, List<Long> tags) {
        tags.forEach(item->{
            baseMapper.insertArticleTags(articleId,item);
        });
    }

    @Override
    public void deleteTagById(List<Long> id) {
        removeByIds(id);
    }

    @Override
    public ResponseResult<TagVo> selectById(Long id) {
        TagVo tagVo = baseMapper.selectByIdMapper(id);
        return ResponseResult.okResult(tagVo);
    }

}

