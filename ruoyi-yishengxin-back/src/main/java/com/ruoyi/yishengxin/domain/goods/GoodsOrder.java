package com.ruoyi.yishengxin.domain.goods;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 商品订单表 ysx_goods_order
 * 
 * @author ruoyi
 * @date 2019-03-04
 */
public class GoodsOrder extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 商品订单id */
	private Integer id;
	/** 用户的id */
	private Integer uid;
	/** 商品名称 */
	private String goodsName;
	/** 商品详情 */
	private String goodsDetails;
	/** 商品价格 */
	private Integer goodsPrice;
	/** 商品购买数量 */
	private Integer goodsSoldNumber;
	/** 商品订单总额 */
	private Integer goodsOrderTotalAmount;
	/** 商品图片 */
	private String goodsPicture;
	/** 商品状态 */
	private String goodsStatus;
	/** 收货地址id */
	private Integer shippingAddress;
	/** 快递单号 */
	private String courierNumber;
	/** 快递公司 */
	private String courierCompany;
	/** 订单号 */
	private String orderNumber;
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
	public void setUid(Integer uid) 
	{
		this.uid = uid;
	}

	public Integer getUid() 
	{
		return uid;
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
	public void setGoodsOrderTotalAmount(Integer goodsOrderTotalAmount) 
	{
		this.goodsOrderTotalAmount = goodsOrderTotalAmount;
	}

	public Integer getGoodsOrderTotalAmount() 
	{
		return goodsOrderTotalAmount;
	}
	public void setGoodsPicture(String goodsPicture) 
	{
		this.goodsPicture = goodsPicture;
	}

	public String getGoodsPicture() 
	{
		return goodsPicture;
	}
	public void setGoodsStatus(String goodsStatus) 
	{
		this.goodsStatus = goodsStatus;
	}

	public String getGoodsStatus() 
	{
		return goodsStatus;
	}
	public void setShippingAddress(Integer shippingAddress) 
	{
		this.shippingAddress = shippingAddress;
	}

	public Integer getShippingAddress() 
	{
		return shippingAddress;
	}
	public void setCourierNumber(String courierNumber) 
	{
		this.courierNumber = courierNumber;
	}

	public String getCourierNumber() 
	{
		return courierNumber;
	}
	public void setCourierCompany(String courierCompany) 
	{
		this.courierCompany = courierCompany;
	}

	public String getCourierCompany() 
	{
		return courierCompany;
	}
	public void setOrderNumber(String orderNumber) 
	{
		this.orderNumber = orderNumber;
	}

	public String getOrderNumber() 
	{
		return orderNumber;
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
            .append("uid", getUid())
            .append("goodsName", getGoodsName())
            .append("goodsDetails", getGoodsDetails())
            .append("goodsPrice", getGoodsPrice())
            .append("goodsSoldNumber", getGoodsSoldNumber())
            .append("goodsOrderTotalAmount", getGoodsOrderTotalAmount())
            .append("goodsPicture", getGoodsPicture())
            .append("goodsStatus", getGoodsStatus())
            .append("shippingAddress", getShippingAddress())
            .append("courierNumber", getCourierNumber())
            .append("courierCompany", getCourierCompany())
            .append("orderNumber", getOrderNumber())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
