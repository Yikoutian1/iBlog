package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.entity.Link;
import com.hang.result.ResponseResult;


/**
 * (Link)表服务接口
 *
 * @author makejava
 * @since 2023-08-05 20:33:01
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

