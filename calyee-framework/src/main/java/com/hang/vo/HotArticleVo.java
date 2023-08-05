package com.hang.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName HotArticleVo
 * @Description 热文章 vo仅仅返回前端需要的数据
 *              vo就是优化过后指定返回数据库字段的实体类，
 *              减少了冗余的数据
 * @Author QiuLiHang
 * @DATE 2023/8/4 17:07
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    private Long id;
    //标题
    private String title;

    //访问量
    private Long viewCount;
}
