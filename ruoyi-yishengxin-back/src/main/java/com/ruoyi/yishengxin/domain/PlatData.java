package com.ruoyi.yishengxin.domain;

import lombok.Data;
import lombok.ToString;
import com.ruoyi.common.base.BaseEntity;

/**
 * 平台基本配置表 ysx_plat_data
 *
 * @author ruoyi
 * @date 2019-03-12
 */
@Data
@ToString
public class PlatData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     * 平台收款账号
     */
    private String platAccount;
    /**
     * 新闻资讯大图片
     */
    private String newsBigPic;
    /**
     * 公告中心大图片
     */
    private String noticeBigPic;


}
