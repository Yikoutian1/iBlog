package com.hang.controller;

import com.hang.dto.ArticleDto;
import com.hang.result.ResponseResult;
import com.hang.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseResult article(@RequestBody ArticleDto articleDto) {
        articleService.saveArticle(articleDto);
        return ResponseResult.okResult(articleDto.getThumbnail());
    }

    @GetMapping("/article/list")
    public ResponseResult queryArticleList(Integer pageNum,
                                           Integer pageSize,
                                           String title,
                                           String summary) {
        return articleService.queryArticleList(pageNum, pageSize, title, summary);
    }
    @GetMapping("/article/{id}")
    public ResponseResult queryArticleList(@PathVariable("id") Long id) {
        return articleService.searchArticle(id);
    }
}
