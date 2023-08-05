package com.hang.controller;

import com.hang.result.ResponseResult;
import com.hang.service.ArticleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ArticleController
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/4 15:43
 * @Version 1.0
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
//    @GetMapping("/list")
//    public List<Article> list(){
//        List<Article> list = articleService.list();
//        return list;
//    }
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        // 查询热门文章  封装ResponseResult返回
        ResponseResult res = articleService.hotArticleList();
        return res;
    }
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Integer id){
        return articleService.getArticleDetail(id);
    }
}
