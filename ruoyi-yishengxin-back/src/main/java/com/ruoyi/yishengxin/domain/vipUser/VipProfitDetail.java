package com.ruoyi.yishengxin.domain.vipUser;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 会员收益明细表 ysx_vip_profit_detail
 *
 * @author ruoyi
 * @date 2019-02-28
 */
public class VipProfitDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer vipId;
    /**
     * 会员头像
     */
    private String vipAvater;
    /**
     * 会员昵称
     */
    private String vipName;
    /**
     * 收益描述
     */
    private String profitDescription;
    /**
     * 时间
     */
    private String profitDate;

    /**
     * 收益类型
     */
    private String profitType;

    public String getProfitType() {
        return profitType;
    }

    public void setProfitType(String profitType) {
        this.profitType = profitType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipAvater(String vipAvater) {
        this.vipAvater = vipAvater;
    }

    public String getVipAvater() {
        return vipAvater;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipName() {
        return vipName;
    }

    public void setProfitDescription(String profitDescription) {
        this.profitDescription = profitDescription;
    }

    public String getProfitDescription() {
        return profitDescription;
    }

    public void setProfitDate(String profitDate) {
        this.profitDate = profitDate;
    }

    public String getProfitDate() {
        return profitDate;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("vipId", getVipId())
                .append("vipAvater", getVipAvater())
                .append("vipName", getVipName())
                .append("profitDescription", getProfitDescription())
                .append("profitDate", getProfitDate())
                .toString();
    }
}
