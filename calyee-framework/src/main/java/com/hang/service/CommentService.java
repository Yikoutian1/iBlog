package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.entity.Comment;
import com.hang.result.ResponseResult;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-08-05 20:45:21
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String status,Integer pageNum, Integer pageSize, Long articleId);

    ResponseResult addComment(Comment comment);
}

