package com.hang.controller;



import com.hang.result.ResponseResult;
import com.hang.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * (Tag)表控制层
 *
 * @author makejava
 * @since 2023-08-09 10:32:28
 */
@RestController
@RequestMapping("/content/tag")
public class TagController{
    @Autowired
    private TagService tagService;
    @GetMapping("/list")
    public ResponseResult list(){
        return ResponseResult.okResult(tagService.list(null));
    }
}

