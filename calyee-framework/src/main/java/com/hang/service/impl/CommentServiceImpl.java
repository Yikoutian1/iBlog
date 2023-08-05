package com.hang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hang.entity.Comment;
import com.hang.mapper.CommentMapper;
import com.hang.result.ResponseResult;
import com.hang.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-08-05 20:45:21
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Override
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize, Long articleId) {

        return null;
    }
}

