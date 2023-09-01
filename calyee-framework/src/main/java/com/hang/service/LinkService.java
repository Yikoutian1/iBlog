package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.entity.Link;
import com.hang.result.ResponseResult;
import com.hang.vo.PageVo;


/**
 * (Link)表服务接口
 *
 * @author makejava
 * @since 2023-08-05 20:33:01
 */
public interface LinkService extends IService<Link> {

    //查询友链
    ResponseResult getAllLink();

    //分页查询友链
    PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize);
}

