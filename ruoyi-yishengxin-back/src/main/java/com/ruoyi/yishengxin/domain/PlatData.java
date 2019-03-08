package com.ruoyi.yishengxin.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 平台基本配置表 ysx_plat_data
 *
 * @author ruoyi
 * @date 2019-03-04
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


}
