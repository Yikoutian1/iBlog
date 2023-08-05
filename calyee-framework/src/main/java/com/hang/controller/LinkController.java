package com.hang.controller;



import com.hang.result.ResponseResult;
import com.hang.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * (Link)表控制层
 *
 * @author makejava
 * @since 2023-08-05 20:33:00
 */
@RestController
@RequestMapping("/link")
public class LinkController{
    @Autowired
    private LinkService linkService;
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}

