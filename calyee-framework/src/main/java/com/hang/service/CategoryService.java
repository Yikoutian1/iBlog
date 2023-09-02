package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.entity.Category;
import com.hang.result.ResponseResult;
import com.hang.vo.CategoryVo;
import com.hang.vo.PageVo;

import java.util.List;


/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2023-08-04 19:24:10
 */
public interface CategoryService extends IService<Category> {

    //查询文章分类的接口
    ResponseResult getCategoryList();

    //写博客-查询文章分类的接口
    List<CategoryVo> listAllCategory();

    //分页查询分类列表
    PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize);
}

