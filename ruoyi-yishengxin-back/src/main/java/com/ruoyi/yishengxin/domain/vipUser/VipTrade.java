package com.ruoyi.yishengxin.domain.vipUser;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 转让记录 ysx_vip_trade
 * 
 * @author ruoyi
 * @date 2019-03-04
 */
public class VipTrade extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 用户id */
	private Integer vipId;
	/** 兑换类型(1,购买商品,2.挂卖HKD,3.挂买HKD,4.转入HKD,5.转出HKD,6.挂卖SSL,7.挂买SSL,8.转入SSL,9.转出SSL,10.平台兑换,11.平台购买)*/
	private String vipTrade;
	/** 交易时间 */
	private String tradeTime;
	/** 交易数量 */
	private String tradeNumber;
	/** 交易对象id */
	private String toVipId;
	/** 交易对象名称 */
	private String toVipNickname;
	/** 交易对象头像 */
	private String toVipAvater;

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
	public void setVipTrade(String vipTrade) 
	{
		this.vipTrade = vipTrade;
	}

	public String getVipTrade() 
	{
		return vipTrade;
	}
	public void setTradeTime(String tradeTime) 
	{
		this.tradeTime = tradeTime;
	}

	public String getTradeTime() 
	{
		return tradeTime;
	}
	public void setTradeNumber(String tradeNumber) 
	{
		this.tradeNumber = tradeNumber;
	}

	public String getTradeNumber() 
	{
		return tradeNumber;
	}
	public void setToVipId(String toVipId) 
	{
		this.toVipId = toVipId;
	}

	public String getToVipId() 
	{
		return toVipId;
	}
	public void setToVipNickname(String toVipNickname) 
	{
		this.toVipNickname = toVipNickname;
	}

	public String getToVipNickname() 
	{
		return toVipNickname;
	}
	public void setToVipAvater(String toVipAvater) 
	{
		this.toVipAvater = toVipAvater;
	}

	public String getToVipAvater() 
	{
		return toVipAvater;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("vipId", getVipId())
            .append("vipTrade", getVipTrade())
            .append("tradeTime", getTradeTime())
            .append("tradeNumber", getTradeNumber())
            .append("toVipId", getToVipId())
            .append("toVipNickname", getToVipNickname())
            .append("toVipAvater", getToVipAvater())
            .toString();
    }
}
