package com.hang.controller;

import com.hang.dto.ArticleDto;
import com.hang.result.ResponseResult;
import com.hang.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ArticleController
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/11 19:47
 * @Version 1.0
 */
@RestController
@RequestMapping("/content")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("/article")
    public ResponseResult article(@RequestBody ArticleDto articleDto){
        articleService.saveArticle(articleDto);
        return ResponseResult.okResult(articleDto.getThumbnail());
    }
}
