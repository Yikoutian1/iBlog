package com.hang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hang.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ArticleMapper
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/4 15:33
 * @Version 1.0
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<Long> selectTagByArticleId(@Param("articleId") Long id);

    void deleteTagOnArticleTag(@Param("article_id") Long articleId);
}
