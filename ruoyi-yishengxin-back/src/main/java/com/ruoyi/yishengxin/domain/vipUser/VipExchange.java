package com.ruoyi.yishengxin.domain.vipUser;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 个人兑换表 ysx_vip_exchange
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
public class VipExchange extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 用户id */
	private Integer vipId;
	/** 兑换数量 */
	private String exchangeAmount;
	/** 兑换手续费 */
	private String exchangeCharge;
	/** 兑换金额 */
	private String exchangeMoney;
	/** 兑换账号 */
	private String exchangeAccount;
	/** 兑换时间 */
	private String exchangeTime;
	/** 兑换状态 */
	private String exchangeStatus;
	/** 打款凭证 */
	private String exchangeDetail;
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
	public void setExchangeAmount(String exchangeAmount) 
	{
		this.exchangeAmount = exchangeAmount;
	}

	public String getExchangeAmount() 
	{
		return exchangeAmount;
	}
	public void setExchangeCharge(String exchangeCharge) 
	{
		this.exchangeCharge = exchangeCharge;
	}

	public String getExchangeCharge() 
	{
		return exchangeCharge;
	}
	public void setExchangeMoney(String exchangeMoney) 
	{
		this.exchangeMoney = exchangeMoney;
	}

	public String getExchangeMoney() 
	{
		return exchangeMoney;
	}
	public void setExchangeAccount(String exchangeAccount) 
	{
		this.exchangeAccount = exchangeAccount;
	}

	public String getExchangeAccount() 
	{
		return exchangeAccount;
	}
	public void setExchangeTime(String exchangeTime)
	{
		this.exchangeTime = exchangeTime;
	}

	public String getExchangeTime()
	{
		return exchangeTime;
	}
	public void setExchangeStatus(String exchangeStatus) 
	{
		this.exchangeStatus = exchangeStatus;
	}

	public String getExchangeStatus() 
	{
		return exchangeStatus;
	}
	public void setExchangeDetail(String exchangeDetail) 
	{
		this.exchangeDetail = exchangeDetail;
	}

	public String getExchangeDetail() 
	{
		return exchangeDetail;
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
            .append("exchangeAmount", getExchangeAmount())
            .append("exchangeCharge", getExchangeCharge())
            .append("exchangeMoney", getExchangeMoney())
            .append("exchangeAccount", getExchangeAccount())
            .append("exchangeTime", getExchangeTime())
            .append("exchangeStatus", getExchangeStatus())
            .append("exchangeDetail", getExchangeDetail())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
