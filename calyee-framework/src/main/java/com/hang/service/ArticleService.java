package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.dto.ArticleDto;
import com.hang.result.ResponseResult;
import com.hang.entity.Article;
import com.hang.vo.ArticleInfoWithTagVo;

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

    ResponseResult getArticleDetail(Integer id);

    ResponseResult updateViewCount(Long id);

    ResponseResult saveArticle(ArticleDto articleDto);

    ResponseResult queryArticleList(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult searchArticle(Long id);

    ResponseResult updateArticle(ArticleInfoWithTagVo articleInfoWithTagVo);

    ResponseResult deleteArticleById(Long id);
}
