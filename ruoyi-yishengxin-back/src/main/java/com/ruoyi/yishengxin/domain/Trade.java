package com.ruoyi.yishengxin.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 交易设置表 ysx_trade
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
public class Trade extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private Integer id;
	/** 跌幅谷值 */
	private String low;
	/** 涨幅峰值 */
	private String high;
	/** 每天最大互转量 */
	private String maxDeliverDay;
	/** 每笔最低互转量 */
	private String minDeliverTime;
	/** 每天最大交易量 */
	private String maxTradeDay;
	/** 每笔最大额 */
	private String maxTradeTime;
	/** 卖HKD手续费 */
	private String hkdCharge;
	/** 卖SSL手续费 */
	private String sslCharge;
	/** 锁仓最低值 */
	private String minLockPosition;
	/** 锁仓整数倍限制 */
	private String lockMultipleNumber;
	/** 中途中断锁仓手续费 */
	private String lockBreakCharge;
	/** 1个月利率 */
	private String oneRate;
	/** 3个月利率 */
	private String threeRate;
	/** 6个月利率 */
	private String sixRate;
	/** 12个月利率 */
	private String twelveRate;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setLow(String low) 
	{
		this.low = low;
	}

	public String getLow() 
	{
		return low;
	}
	public void setHigh(String high) 
	{
		this.high = high;
	}

	public String getHigh() 
	{
		return high;
	}
	public void setMaxDeliverDay(String maxDeliverDay) 
	{
		this.maxDeliverDay = maxDeliverDay;
	}

	public String getMaxDeliverDay() 
	{
		return maxDeliverDay;
	}
	public void setMinDeliverTime(String minDeliverTime) 
	{
		this.minDeliverTime = minDeliverTime;
	}

	public String getMinDeliverTime() 
	{
		return minDeliverTime;
	}
	public void setMaxTradeDay(String maxTradeDay) 
	{
		this.maxTradeDay = maxTradeDay;
	}

	public String getMaxTradeDay() 
	{
		return maxTradeDay;
	}
	public void setMaxTradeTime(String maxTradeTime) 
	{
		this.maxTradeTime = maxTradeTime;
	}

	public String getMaxTradeTime() 
	{
		return maxTradeTime;
	}
	public void setHkdCharge(String hkdCharge) 
	{
		this.hkdCharge = hkdCharge;
	}

	public String getHkdCharge() 
	{
		return hkdCharge;
	}
	public void setSslCharge(String sslCharge) 
	{
		this.sslCharge = sslCharge;
	}

	public String getSslCharge() 
	{
		return sslCharge;
	}
	public void setMinLockPosition(String minLockPosition) 
	{
		this.minLockPosition = minLockPosition;
	}

	public String getMinLockPosition() 
	{
		return minLockPosition;
	}
	public void setLockMultipleNumber(String lockMultipleNumber) 
	{
		this.lockMultipleNumber = lockMultipleNumber;
	}

	public String getLockMultipleNumber() 
	{
		return lockMultipleNumber;
	}
	public void setLockBreakCharge(String lockBreakCharge) 
	{
		this.lockBreakCharge = lockBreakCharge;
	}

	public String getLockBreakCharge() 
	{
		return lockBreakCharge;
	}
	public void setOneRate(String oneRate) 
	{
		this.oneRate = oneRate;
	}

	public String getOneRate() 
	{
		return oneRate;
	}
	public void setThreeRate(String threeRate) 
	{
		this.threeRate = threeRate;
	}

	public String getThreeRate() 
	{
		return threeRate;
	}
	public void setSixRate(String sixRate) 
	{
		this.sixRate = sixRate;
	}

	public String getSixRate() 
	{
		return sixRate;
	}
	public void setTwelveRate(String twelveRate) 
	{
		this.twelveRate = twelveRate;
	}

	public String getTwelveRate() 
	{
		return twelveRate;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("low", getLow())
            .append("high", getHigh())
            .append("maxDeliverDay", getMaxDeliverDay())
            .append("minDeliverTime", getMinDeliverTime())
            .append("maxTradeDay", getMaxTradeDay())
            .append("maxTradeTime", getMaxTradeTime())
            .append("hkdCharge", getHkdCharge())
            .append("sslCharge", getSslCharge())
            .append("minLockPosition", getMinLockPosition())
            .append("lockMultipleNumber", getLockMultipleNumber())
            .append("lockBreakCharge", getLockBreakCharge())
            .append("oneRate", getOneRate())
            .append("threeRate", getThreeRate())
            .append("sixRate", getSixRate())
            .append("twelveRate", getTwelveRate())
            .toString();
    }
}
