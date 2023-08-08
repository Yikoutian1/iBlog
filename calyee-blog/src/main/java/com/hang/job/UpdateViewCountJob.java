package com.hang.job;

import com.hang.constants.SystemConstants;
import com.hang.entity.Article;
import com.hang.service.ArticleService;
import com.hang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ViewCountUpdateJob
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/8 20:29
 * @Version 1.0
 */
@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;
    @Scheduled(cron = "0 */10 * * * ?") // 开启定时任务
    public void updateViewCountJob(){
        // 获取redis中存取的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEW_COUNT);
        // 双列集合不能用流,可以先转entrySet(键值对)或者keySet
        List<Article> articleList = viewCountMap.entrySet()
                .stream()                            //  key(long)  value(long)
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        // 更新到数据库
        articleService.updateBatchById(articleList);
    }
}
