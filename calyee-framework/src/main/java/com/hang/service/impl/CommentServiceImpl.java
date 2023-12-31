package com.hang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hang.constants.SystemConstants;
import com.hang.entity.Comment;
import com.hang.enums.AppHttpCodeEnum;
import com.hang.exception.SystemException;
import com.hang.mapper.CommentMapper;
import com.hang.result.ResponseResult;
import com.hang.service.CommentService;
import com.hang.service.UserService;
import com.hang.utils.BeanCopyUtils;
import com.hang.vo.CommentVo;
import com.hang.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-08-05 20:45:21
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;
    /**
     * 评论列表
     * @param pageNum
     * @param pageSize
     * @param articleId
     * @return
     */
    @Override
    public ResponseResult commentList(String status,Integer pageNum, Integer pageSize, Long articleId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 对articleId进行判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(status),Comment::getArticleId,articleId);
        // 根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId,SystemConstants.ROOT_COMMENT);

        // 评论类型
        queryWrapper.eq(Comment::getType,status);

        // 分页
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        // 传入Vo(里面的字段 有些还没有值)
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        // 查询根评论对应的子评论集合,赋值给对应的属性(children)
        for (CommentVo vo : commentVoList) {
            Long id = vo.getId();
            List<CommentVo> children = getChildren(id);
            vo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    /**
     * 添加评论
     * @param comment
     */
    @Override
    public ResponseResult addComment(Comment comment) {
        // 评论不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * comment -> commentVo
     * @param commentList
     * @return
     */
    private List<CommentVo> toCommentVoList(List<Comment> commentList){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        // 还有需要去处理的值，遍历vo
        // 通过creatBy查询用户昵称并赋值
        for (CommentVo vo : commentVos) {
            // getCreateBy对应的用户id
            String nikeName = userService.getById(vo.getCreateBy()).getNickName();
            vo.setUsername(nikeName);
            // 通过toCommentUserId查询用户昵称并赋值
            // 如果toCommentId不为-1 才进行查询
            if(vo.getToCommentUserId() != -1){
                String toCommentUserName = userService.getById(vo.getToCommentUserId()).getNickName();
                vo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }

    /**
     * 查询根评论下面的子评论
     * @param rootId 根据 根id
     * @return
     */
    private List<CommentVo> getChildren(Long rootId){
        // 如果评论过多需要考虑分页,现在不考虑
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,rootId)
                .orderByAsc(Comment::getCreateTime);
        List<Comment> list = list(queryWrapper);
        // 直接调用之前封装的方法
        List<CommentVo> commentVos = toCommentVoList(list);
        return commentVos;
    }
}

