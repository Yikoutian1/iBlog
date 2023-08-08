package com.hang.runner;

import com.hang.constants.SystemConstants;
import com.hang.entity.Article;
import com.hang.mapper.ArticleMapper;
import com.hang.service.ArticleService;
import com.hang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName ViewCountRunner
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/8 20:01
 * @Version 1.0
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        // 查询博客信息 id + viewCount           key value
        List<Article> articleList = articleMapper.selectList(null);
        Map<String, Integer> map = new HashMap<>();
//        articleList.forEach(item->{
//            map.put(String.valueOf(item.getId()),item.getViewCount());
//        });
        map = articleList.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), // key
                        article -> {                                  // value
                            return article.getViewCount().intValue();   // 1L 在redis中要用Integer
                        }));
        // 存储到redis
        redisCache.setCacheMap(SystemConstants.ARTICLE_VIEW_COUNT,map);
    }
}
