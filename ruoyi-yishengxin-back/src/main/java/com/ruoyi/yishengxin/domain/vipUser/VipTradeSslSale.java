package com.ruoyi.yishengxin.domain.vipUser;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 挂卖SSL表 ysx_vip_trade_sale
 * 
 * @author ruoyi
 * @date 2019-03-06
 */
public class VipTradeSslSale extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 用户id */
	private Integer vipId;
	/** 交易类型(挂卖SSL为6) */
	private String saleType;
	/** 交易状态(TradeStatus) */
	private String saleStatus;
	/** 订单编号 */
	private String saleNo;
	/** 订单数量 */
	private String saleNumber;
	/** 单价 */
	private String unitPrice;
	/** 总金额 */
	private String saleTotal;
	/** 时间 */
	private String saleTime;
	/** 买方ID */
	private String buyId;
	/** 买方手机号 */
	private String buyPhone;
	/** 买方名称 */
	private String buyNickname;
	/** 买方头像 */
	private String buyAvater;

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
	public void setSaleType(String saleType) 
	{
		this.saleType = saleType;
	}

	public String getSaleType() 
	{
		return saleType;
	}
	public void setSaleStatus(String saleStatus) 
	{
		this.saleStatus = saleStatus;
	}

	public String getSaleStatus() 
	{
		return saleStatus;
	}
	public void setSaleNo(String saleNo) 
	{
		this.saleNo = saleNo;
	}

	public String getSaleNo() 
	{
		return saleNo;
	}
	public void setSaleNumber(String saleNumber) 
	{
		this.saleNumber = saleNumber;
	}

	public String getSaleNumber() 
	{
		return saleNumber;
	}
	public void setUnitPrice(String unitPrice) 
	{
		this.unitPrice = unitPrice;
	}

	public String getUnitPrice() 
	{
		return unitPrice;
	}
	public void setSaleTotal(String saleTotal) 
	{
		this.saleTotal = saleTotal;
	}

	public String getSaleTotal() 
	{
		return saleTotal;
	}
	public void setSaleTime(String saleTime) 
	{
		this.saleTime = saleTime;
	}

	public String getSaleTime() 
	{
		return saleTime;
	}
	public void setBuyId(String buyId) 
	{
		this.buyId = buyId;
	}

	public String getBuyId() 
	{
		return buyId;
	}
	public void setBuyPhone(String buyPhone) 
	{
		this.buyPhone = buyPhone;
	}

	public String getBuyPhone() 
	{
		return buyPhone;
	}
	public void setBuyNickname(String buyNickname) 
	{
		this.buyNickname = buyNickname;
	}

	public String getBuyNickname() 
	{
		return buyNickname;
	}
	public void setBuyAvater(String buyAvater) 
	{
		this.buyAvater = buyAvater;
	}

	public String getBuyAvater() 
	{
		return buyAvater;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("vipId", getVipId())
            .append("saleType", getSaleType())
            .append("saleStatus", getSaleStatus())
            .append("saleNo", getSaleNo())
            .append("saleNumber", getSaleNumber())
            .append("unitPrice", getUnitPrice())
            .append("saleTotal", getSaleTotal())
            .append("saleTime", getSaleTime())
            .append("buyId", getBuyId())
            .append("buyPhone", getBuyPhone())
            .append("buyNickname", getBuyNickname())
            .append("buyAvater", getBuyAvater())
            .toString();
    }
}
