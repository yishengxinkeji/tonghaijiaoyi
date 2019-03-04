package com.ruoyi.yishengxin.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 平台基本配置表 ysx_plat_data
 *
 * @author ruoyi
 * @date 2019-03-04
 */
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPlatAccount(String platAccount) {
        this.platAccount = platAccount;
    }

    public String getPlatAccount() {
        return platAccount;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("platAccount", getPlatAccount())
                .toString();
    }
}
