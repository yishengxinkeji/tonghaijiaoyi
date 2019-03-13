package com.ruoyi.yishengxin.domain.vipUser;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 会员收益明细表 ysx_vip_profit_detail
 *
 * @author ruoyi
 * @date 2019-02-28
 */

@Data
@ToString
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

    /**
     * 下级用户id
     */
    private Integer childrenVipId;
}
