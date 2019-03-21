package com.ruoyi.yishengxin.domain.goods;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 售量统计表 ysx_goods_statistics
 * 
 * @author ruoyi
 * @date 2019-04-20
 */
@Data
@ToString
public class GoodsStatistics
{
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	/**  */
	private Integer id;
	/** 开始时间 */
	private Date startTime;
	/** 商品名 */
	private String goodsName;
	/** 结束时间 */
	private Date stopTime;
	/** 售量 */
	private String sales;


}
