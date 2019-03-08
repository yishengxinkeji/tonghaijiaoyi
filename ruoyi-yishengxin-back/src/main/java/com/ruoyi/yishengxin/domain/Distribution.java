package com.ruoyi.yishengxin.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 分销设置表 ysx_distribution
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Data
public class Distribution extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;
    /**
     * 上级手续费比例
     */
    private String parentCharge;
    /**
     * 上上级手续费比例
     */
    private String grandparentCharge;


}
