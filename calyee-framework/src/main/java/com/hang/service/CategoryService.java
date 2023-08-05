package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.entity.Category;
import com.hang.result.ResponseResult;


/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2023-08-04 19:24:10
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
