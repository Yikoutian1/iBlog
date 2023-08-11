package com.hang.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ArticleDto
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/11 19:49
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class ArticleDto {
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;
    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;

    //是否允许评论 1是，0否
    private String isComment;

    private List<Long> tags;
}
