package com.hang.controller;


import com.hang.result.ResponseResult;
import com.hang.service.LinkService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Link)表控制层
 *
 * @author makejava
 * @since 2023-08-05 20:33:00
 */
@RestController
@RequestMapping("/link")
@Api(tags = "友链相关接口")
public class LinkController{
    @Autowired
    private LinkService linkService;
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}

