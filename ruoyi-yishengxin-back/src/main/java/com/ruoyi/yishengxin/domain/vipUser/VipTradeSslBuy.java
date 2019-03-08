package com.ruoyi.yishengxin.domain.vipUser;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 挂买SSL表 ysx_vip_trade_buy
 * 
 * @author ruoyi
 * @date 2019-03-06
 */
public class VipTradeSslBuy extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/**  用户id  */
	private Integer vipId;
	/**  交易类型(挂买SSL为7) */
	private String buyType;
	/**  交易状态(1.交易中 2.交易成功,3.交易失败) */
	private String buyStatus;
	/**  订单编号  */
	private String buyNo;
	/**  订单数量  */
	private String buyNumber;
	/**  单价  */
	private String unitPrice;
	/**  总金额  */
	private String buyTotal;
	/**  时间  */
	private String buyTime;
	/**  卖方ID  */
	private String saleId;
	/**  卖方手机号  */
	private String salePhone;
	/**  卖方名称  */
	private String saleNickname;
	/**  卖方头像  */
	private String saleAvater;

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
	public void setBuyType(String buyType) 
	{
		this.buyType = buyType;
	}

	public String getBuyType() 
	{
		return buyType;
	}
	public void setBuyStatus(String buyStatus) 
	{
		this.buyStatus = buyStatus;
	}

	public String getBuyStatus() 
	{
		return buyStatus;
	}
	public void setBuyNo(String buyNo) 
	{
		this.buyNo = buyNo;
	}

	public String getBuyNo() 
	{
		return buyNo;
	}
	public void setBuyNumber(String buyNumber) 
	{
		this.buyNumber = buyNumber;
	}

	public String getBuyNumber() 
	{
		return buyNumber;
	}
	public void setUnitPrice(String unitPrice) 
	{
		this.unitPrice = unitPrice;
	}

	public String getUnitPrice() 
	{
		return unitPrice;
	}
	public void setBuyTotal(String buyTotal) 
	{
		this.buyTotal = buyTotal;
	}

	public String getBuyTotal() 
	{
		return buyTotal;
	}
	public void setBuyTime(String buyTime) 
	{
		this.buyTime = buyTime;
	}

	public String getBuyTime() 
	{
		return buyTime;
	}
	public void setSaleId(String saleId) 
	{
		this.saleId = saleId;
	}

	public String getSaleId() 
	{
		return saleId;
	}
	public void setSalePhone(String salePhone) 
	{
		this.salePhone = salePhone;
	}

	public String getSalePhone() 
	{
		return salePhone;
	}
	public void setSaleNickname(String saleNickname) 
	{
		this.saleNickname = saleNickname;
	}

	public String getSaleNickname() 
	{
		return saleNickname;
	}
	public void setSaleAvater(String saleAvater) 
	{
		this.saleAvater = saleAvater;
	}

	public String getSaleAvater() 
	{
		return saleAvater;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("vipId", getVipId())
            .append("buyType", getBuyType())
            .append("buyStatus", getBuyStatus())
            .append("buyNo", getBuyNo())
            .append("buyNumber", getBuyNumber())
            .append("unitPrice", getUnitPrice())
            .append("buyTotal", getBuyTotal())
            .append("buyTime", getBuyTime())
            .append("saleId", getSaleId())
            .append("salePhone", getSalePhone())
            .append("saleNickname", getSaleNickname())
            .append("saleAvater", getSaleAvater())
            .toString();
    }
}
