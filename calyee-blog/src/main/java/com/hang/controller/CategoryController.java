package com.hang.controller;


import com.hang.result.ResponseResult;
import com.hang.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Category)表控制层
 *
 * @author makejava
 * @since 2023-08-04 19:24:10
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类相关接口")
public class CategoryController{
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}

