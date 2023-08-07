package com.hang.controller;


import com.hang.constants.SystemConstants;
import com.hang.entity.Comment;
import com.hang.result.ResponseResult;
import com.hang.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论表(Comment)表控制层
 *
 * @author makejava
 * @since 2023-08-05 20:45:21
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Integer pageNum, Integer pageSize, Long articleId) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, pageNum, pageSize, articleId);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
        return ResponseResult.okResult();
    }

    /**
     * 友链评论
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, pageNum, pageSize, null);
    }
}

