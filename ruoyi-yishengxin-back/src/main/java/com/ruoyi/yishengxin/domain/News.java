package com.ruoyi.yishengxin.domain;

import lombok.Data;
import lombok.ToString;
import com.ruoyi.common.base.BaseEntity;

/**
 * 新闻资讯表 ysx_news
 *
 * @author ruoyi
 * @date 2019-03-12
 */
@Data
@ToString
public class News extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     * 新闻时间
     */
    private String newsTime;
    /**
     * 新闻图片
     */
    private String newsPic;
    /**
     * 新闻简介
     */
    private String newsIntroduction;
    /**
     * 新闻内容
     */
    private String newsContent;

    /**
     * 新闻标题
     */
    private String newsTitle;

}
