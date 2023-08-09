package com.hang.controller;


import com.hang.constants.SystemConstants;
import com.hang.entity.Comment;
import com.hang.result.ResponseResult;
import com.hang.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "评论相关接口")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @ApiOperation("评论列表")
    public ResponseResult commentList(Integer pageNum, Integer pageSize, Long articleId) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, pageNum, pageSize, articleId);
    }

    @PostMapping
    @ApiOperation("去评论")
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
    @ApiOperation("友链评论列表")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, pageNum, pageSize, null);
    }
}

