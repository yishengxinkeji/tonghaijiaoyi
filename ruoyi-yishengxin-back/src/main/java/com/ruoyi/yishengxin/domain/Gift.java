package com.ruoyi.yishengxin.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 礼包设置表 ysx_gift
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Data
public class Gift extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     * 新人礼包金额
     */
    private String newGift;
    /**
     * 每日分享礼包金额
     */
    private String dayGift;

    //新人礼包类型
    private String newType;
    //每日分享礼包类型
    private String dayType;



}
