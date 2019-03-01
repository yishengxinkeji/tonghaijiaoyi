package com.ruoyi.yishengxin.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 商品表 ysx_goods
 * 
 * @author ruoyi
 * @date 2019-03-01
 */
public class Goods extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 商品id */
	private Integer id;
	/** 商品名称 */
	private String goodsName;
	/** 商品详情 */
	private String goodsDetails;
	/** 商品价格 */
	private Integer goodsPrice;
	/** 商品售出量 */
	private Integer goodsSoldNumber;
	/** 商品上下架 */
	private Integer standUpAndDown;
	/** 商品图片 */
	private String goodsPicture;
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
	public void setGoodsName(String goodsName) 
	{
		this.goodsName = goodsName;
	}

	public String getGoodsName() 
	{
		return goodsName;
	}
	public void setGoodsDetails(String goodsDetails) 
	{
		this.goodsDetails = goodsDetails;
	}

	public String getGoodsDetails() 
	{
		return goodsDetails;
	}
	public void setGoodsPrice(Integer goodsPrice) 
	{
		this.goodsPrice = goodsPrice;
	}

	public Integer getGoodsPrice() 
	{
		return goodsPrice;
	}
	public void setGoodsSoldNumber(Integer goodsSoldNumber) 
	{
		this.goodsSoldNumber = goodsSoldNumber;
	}

	public Integer getGoodsSoldNumber() 
	{
		return goodsSoldNumber;
	}
	public void setStandUpAndDown(Integer standUpAndDown) 
	{
		this.standUpAndDown = standUpAndDown;
	}

	public Integer getStandUpAndDown() 
	{
		return standUpAndDown;
	}
	public void setGoodsPicture(String goodsPicture) 
	{
		this.goodsPicture = goodsPicture;
	}

	public String getGoodsPicture() 
	{
		return goodsPicture;
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
            .append("goodsName", getGoodsName())
            .append("goodsDetails", getGoodsDetails())
            .append("goodsPrice", getGoodsPrice())
            .append("goodsSoldNumber", getGoodsSoldNumber())
            .append("standUpAndDown", getStandUpAndDown())
            .append("goodsPicture", getGoodsPicture())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
