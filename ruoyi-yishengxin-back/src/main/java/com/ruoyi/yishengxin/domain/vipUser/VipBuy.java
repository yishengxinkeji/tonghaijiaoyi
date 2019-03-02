package com.ruoyi.yishengxin.domain.vipUser;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 个人购买表 ysx_vip_buy
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
public class VipBuy extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 用户id */
	private Integer vipId;
	/** 兑换数量 */
	private String buyAmount;
	/** 兑换金额 */
	private String buyMoney;
	/** 平台账号 */
	private String buyAccount;
	/** 打款凭证 */
	private String buyDetail;
	/** 购买状态 */
	private String buyType;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 备注 */
	private String remark;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setVipId(Integer vipId) 
	{
		this.vipId = vipId;
	}

	public Integer getVipId() 
	{
		return vipId;
	}
	public void setBuyAmount(String buyAmount) 
	{
		this.buyAmount = buyAmount;
	}

	public String getBuyAmount() 
	{
		return buyAmount;
	}
	public void setBuyMoney(String buyMoney) 
	{
		this.buyMoney = buyMoney;
	}

	public String getBuyMoney() 
	{
		return buyMoney;
	}
	public void setBuyAccount(String buyAccount) 
	{
		this.buyAccount = buyAccount;
	}

	public String getBuyAccount() 
	{
		return buyAccount;
	}
	public void setBuyDetail(String buyDetail) 
	{
		this.buyDetail = buyDetail;
	}

	public String getBuyDetail() 
	{
		return buyDetail;
	}
	public void setBuyType(String buyType) 
	{
		this.buyType = buyType;
	}

	public String getBuyType() 
	{
		return buyType;
	}
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}

	public String getCreateBy() 
	{
		return createBy;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy() 
	{
		return updateBy;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("vipId", getVipId())
            .append("buyAmount", getBuyAmount())
            .append("buyMoney", getBuyMoney())
            .append("buyAccount", getBuyAccount())
            .append("buyDetail", getBuyDetail())
            .append("buyType", getBuyType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
