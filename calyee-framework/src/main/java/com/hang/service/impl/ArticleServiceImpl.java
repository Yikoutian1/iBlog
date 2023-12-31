package com.hang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hang.constants.SystemConstants;
import com.hang.dto.ArticleDto;
import com.hang.entity.Category;
import com.hang.enums.AppHttpCodeEnum;
import com.hang.result.ResponseResult;
import com.hang.entity.Article;
import com.hang.mapper.ArticleMapper;
import com.hang.service.ArticleService;
import com.hang.service.CategoryService;
import com.hang.service.TagService;
import com.hang.utils.BeanCopyUtils;
import com.hang.utils.RedisCache;
import com.hang.utils.SecurityUtils;
import com.hang.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName ArticleServiceImpl
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/4 15:36
 * @Version 1.0
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;
    /**
     * 查询热门文章
     *
     * @return
     */
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                // .eq(Article::getIsTop,1)   // 置顶(这个置顶不需要)
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)      // 已发布
                .orderByDesc(Article::getViewCount);// 根据浏览量排序
        // 分页 只查10条
        Page<Article> page = new Page<>(1, 10);
        // page的构造方法
        page(page, queryWrapper);
        List<Article> records = page.getRecords();

        // 原方案
//        List<HotArticleVo> hotArticleVos = new ArrayList<>();
//        records.forEach(item->{
//            HotArticleVo vo = new HotArticleVo();
//            // 复制bean 从item 到 vo (字段名和字段类型一致)
//            BeanUtils.copyProperties(item,vo);
//            // 添加到list
//            hotArticleVos.add(vo);
//        });
        // 封装bean拷贝工具类后
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);
        // 浏览量从redis中获取
        for (HotArticleVo vo : hotArticleVos) {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, vo.getId().toString());
            vo.setViewCount(viewCount != null ? viewCount.longValue() : 0L);
        }
        return ResponseResult.okResult(hotArticleVos);
    }

    /**
     * 文章分页列表
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 前端如果传了categoryId，那么则表示的是查看详情(此时查询的则和传进来的那个一样)
        // 如果没有，那么则是在首页（类似于缩略图）
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 状态是正式发布的
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                // 对isTop进行降序
                .orderByDesc(Article::getIsTop);
        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<Article> records = page.getRecords();
        // 此时我们发现,categoryName是空的,那么我们还需要根据articleId查出来
        records.stream().map(item -> {
            // 获取到了分类id
//第一步            Long cId = item.getCategoryId();
            // 根据id查询categoryName
//第一步          records  Category category = categoryService.getById(cId);
//第二步优化            item.setCategoryName(categoryService.getById(item.getCategoryId()).getName());
            // 需要在实体类中加上注解 @Accessors(chain = true) // 设置set返回类型为当前对象本身
            return item.setCategoryName(categoryService.getById(item.getCategoryId()).getName());
        }).collect(Collectors.toList());
        // 封装查询结果
        // 使用封装的工具类
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);
        // 浏览量从redis中获取
        for(ArticleListVo listVo: articleListVos){
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, listVo.getId().toString());
            listVo.setViewCount(viewCount.longValue());
        }

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Integer id) {
        // 直接调用mybatis plus里面的getById查询
        Article article = getById(id);
        // 从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, id.toString());
        article.setViewCount(viewCount.longValue());

        ArticleDetailtVo articleDetailtVo = BeanCopyUtils.copyBean(article, ArticleDetailtVo.class);
        Long categoryId = articleDetailtVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        // 如果没有查到
        if(category!=null){
            // 设置vo的categoryName
            articleDetailtVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailtVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新redis中对应id的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT,id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult saveArticle(ArticleDto articleDto) {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);

        try {
            // 首先需要保存当前前端传回来的数据
            Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
            save(article);
            // 然后对article_tag表进行插入(需要把刚刚插入的Article_id查出来)
            Long articleId = article.getId();
            tagService.saveToArticleTag(articleId,articleDto.getTags());

            dataSourceTransactionManager.commit(transactionStatus);// 手动commit
            return ResponseResult.okResult();
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(transactionStatus);
            return ResponseResult.errorResult(201, "更新失败");
        }

    }

    @Override
    public ResponseResult queryArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(title!=null,Article::getTitle,title)
                .like(summary!=null,Article::getSummary,summary)
                .eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Article> records = page.getRecords();
        List<ArticleContentVo> articleContentVos = BeanCopyUtils.copyBeanList(records, ArticleContentVo.class);
        return ResponseResult.okResult(new PageVo(articleContentVos,page.getTotal()));
    }

    @Override
    public ResponseResult searchArticle(Long id) {
        // 首先查询文章
        LambdaQueryWrapper<Article> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,id)
                .eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        Article article = getBaseMapper().selectOne(queryWrapper);
        // 再查询文章对应的tag标签
        if(article == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_ARTICLE);
        }
        List<Long> tags = baseMapper.selectTagByArticleId(id);
        ArticleInfoWithTagVo articleInfoWithTagVo = BeanCopyUtils.copyBean(article, ArticleInfoWithTagVo.class);
        articleInfoWithTagVo.setTags(tags);
        return ResponseResult.okResult(articleInfoWithTagVo);
    }

    @Override
    public ResponseResult updateArticle(ArticleInfoWithTagVo articleInfoWithTagVo) {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);

        try {
            // 首先更新文章表
            Article article = BeanCopyUtils.copyBean(articleInfoWithTagVo, Article.class);
            baseMapper.updateById(article);
            // 再更新文章标签表
            List<Long> tags = articleInfoWithTagVo.getTags();
            Long articleId = articleInfoWithTagVo.getId();

            baseMapper.deleteTagOnArticleTag(articleId);
            tagService.saveToArticleTag(articleId,tags);
            dataSourceTransactionManager.commit(transactionStatus);// 手动commit
            return ResponseResult.okResult();
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(transactionStatus);
            return ResponseResult.errorResult(201, "更新失败");
        }
    }

    @Override
    public ResponseResult deleteArticleById(Long id) {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            // 删除文章表
            baseMapper.deleteById(id);
            // 删除文章标签表
            baseMapper.deleteTagOnArticleTag(id);
            dataSourceTransactionManager.commit(transactionStatus);// 手动commit
            return ResponseResult.okResult();
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(transactionStatus);
            return ResponseResult.errorResult(201, "删除失败");
        }
    }

}
