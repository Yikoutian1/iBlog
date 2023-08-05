package com.hang.constants;

/**
 * @ClassName SystemConstants
 * @Description 实际项目中都不允许直接在代码中使用字面值。都需要定义成常量来使用。这种方式有利于提高代码的可维护性。
 * @Author QiuLiHang
 * @DATE 2023/8/4 18:48
 * @Version 1.0
 */

public class SystemConstants {
    /**
     * 文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     * 文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;
    /**
     * 分类正常
     */
    public static final String CATEGORY_STATUS_NORMAL = "0";
    /**
     * 没有分类
     */
    public static final String CATEGORY_STATUS_DRAFT = "1";
    /**
     * 审核通过
     */
    public static final String LINK_STATUS_SUCCESS = "0";
    /**
     * 审核未通过
     */
    public static final String LINK_STATUS_FAILED = "1";
    /**
     * 未审核
     */
    public static final String LINK_STATUS_WAIT = "2";
}
