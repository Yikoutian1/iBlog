package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.result.ResponseResult;
import com.hang.entity.Article;

/**
 * @ClassName ArticleService
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/4 15:35
 * @Version 1.0
 */

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
}
