package com.hang.controller;


import com.hang.dto.TagListDto;
import com.hang.entity.Tag;
import com.hang.enums.AppHttpCodeEnum;
import com.hang.result.ResponseResult;
import com.hang.service.TagService;
import com.hang.utils.BeanCopyUtils;
import com.hang.utils.SecurityUtils;
import com.hang.vo.PageVo;
import com.hang.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * (Tag)表控制层
 *
 * @author makejava
 * @since 2023-08-09 10:32:28
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }
    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        boolean flag = tagService.addTag(tagListDto);
        return flag ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.ADD_TAG_ERROR);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public ResponseResult deleteTagById(@PathVariable List<Long> ids){
        tagService.deleteTagById(ids);
        return ResponseResult.okResult();
    }

    /**
     * 根据id获取标签基本信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult<TagVo> selectById(@PathVariable Long id){
        return tagService.selectById(id);
    }
//    @PreAuthorize("")
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
    @PutMapping
    public ResponseResult updateTagInfo(@RequestBody TagListDto tagListDto){
        return tagService.updateTagInfo(tagListDto);
    }
}



