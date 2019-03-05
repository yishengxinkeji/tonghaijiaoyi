package com.ruoyi.yishengxin.domain.goods;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 商品退货表 ysx_goods_salesReturn
 * 
 * @author ruoyi
 * @date 2019-03-05
 */
public class GoodsSalesreturn extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 退货id */
	private Integer id;
	/** 用户id */
	private Integer uid;
	/** 订单编号 */
	private String orderNumber;
	/** 订单时间 */
	private Date orderTime;
	/** 商品的名称 */
	private String goodsName;
	/** 商品的图片 */
	private String goodsImages;
	/** 商品介绍 */
	private String goodsIntroduce;
	/** 商品单价 */
	private String goodsUnitPrice;
	/** 购买数量 */
	private Integer buyNumber;
	/** 退款方式 */
	private String refundWay;
	/** 退款原因 */
	private String refundReason;
	/** 退款失败原因 */
	private String reasonFailure;
	/** 退款说明 */
	private String efundInstructions;
	/** 上传凭证 */
	private String ploadDocuments;
	/** 退款金额 */
	private String refundAmount;
	/** 退货数量 */
	private Integer refundNumber;
	/** 申请退款时间 */
	private Date refundTime;
	/** 退款编号 */
	private String refundSerialNumber;
	/** 退货物流公司 */
	private String refundCompany;
	/** 退货物流号 */
	private String refundLogistics;
	/** 退货状态 */
	private String refundStatus;
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
	public void setOrderNumber(String orderNumber) 
	{
		this.orderNumber = orderNumber;
	}

	public String getOrderNumber() 
	{
		return orderNumber;
	}
	public void setOrderTime(Date orderTime) 
	{
		this.orderTime = orderTime;
	}

	public Date getOrderTime() 
	{
		return orderTime;
	}
	public void setGoodsName(String goodsName) 
	{
		this.goodsName = goodsName;
	}

	public String getGoodsName() 
	{
		return goodsName;
	}
	public void setGoodsImages(String goodsImages) 
	{
		this.goodsImages = goodsImages;
	}

	public String getGoodsImages() 
	{
		return goodsImages;
	}
	public void setGoodsIntroduce(String goodsIntroduce) 
	{
		this.goodsIntroduce = goodsIntroduce;
	}

	public String getGoodsIntroduce() 
	{
		return goodsIntroduce;
	}
	public void setGoodsUnitPrice(String goodsUnitPrice) 
	{
		this.goodsUnitPrice = goodsUnitPrice;
	}

	public String getGoodsUnitPrice() 
	{
		return goodsUnitPrice;
	}
	public void setBuyNumber(Integer buyNumber) 
	{
		this.buyNumber = buyNumber;
	}

	public Integer getBuyNumber() 
	{
		return buyNumber;
	}
	public void setRefundWay(String refundWay) 
	{
		this.refundWay = refundWay;
	}

	public String getRefundWay() 
	{
		return refundWay;
	}
	public void setRefundReason(String refundReason) 
	{
		this.refundReason = refundReason;
	}

	public String getRefundReason() 
	{
		return refundReason;
	}
	public void setReasonFailure(String reasonFailure) 
	{
		this.reasonFailure = reasonFailure;
	}

	public String getReasonFailure() 
	{
		return reasonFailure;
	}
	public void setEfundInstructions(String efundInstructions) 
	{
		this.efundInstructions = efundInstructions;
	}

	public String getEfundInstructions() 
	{
		return efundInstructions;
	}
	public void setPloadDocuments(String ploadDocuments) 
	{
		this.ploadDocuments = ploadDocuments;
	}

	public String getPloadDocuments() 
	{
		return ploadDocuments;
	}
	public void setRefundAmount(String refundAmount) 
	{
		this.refundAmount = refundAmount;
	}

	public String getRefundAmount() 
	{
		return refundAmount;
	}
	public void setRefundNumber(Integer refundNumber) 
	{
		this.refundNumber = refundNumber;
	}

	public Integer getRefundNumber() 
	{
		return refundNumber;
	}
	public void setRefundTime(Date refundTime) 
	{
		this.refundTime = refundTime;
	}

	public Date getRefundTime() 
	{
		return refundTime;
	}
	public void setRefundSerialNumber(String refundSerialNumber) 
	{
		this.refundSerialNumber = refundSerialNumber;
	}

	public String getRefundSerialNumber() 
	{
		return refundSerialNumber;
	}
	public void setRefundCompany(String refundCompany) 
	{
		this.refundCompany = refundCompany;
	}

	public String getRefundCompany() 
	{
		return refundCompany;
	}
	public void setRefundLogistics(String refundLogistics) 
	{
		this.refundLogistics = refundLogistics;
	}

	public String getRefundLogistics() 
	{
		return refundLogistics;
	}
	public void setRefundStatus(String refundStatus) 
	{
		this.refundStatus = refundStatus;
	}

	public String getRefundStatus() 
	{
		return refundStatus;
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
            .append("orderNumber", getOrderNumber())
            .append("orderTime", getOrderTime())
            .append("goodsName", getGoodsName())
            .append("goodsImages", getGoodsImages())
            .append("goodsIntroduce", getGoodsIntroduce())
            .append("goodsUnitPrice", getGoodsUnitPrice())
            .append("buyNumber", getBuyNumber())
            .append("refundWay", getRefundWay())
            .append("refundReason", getRefundReason())
            .append("reasonFailure", getReasonFailure())
            .append("efundInstructions", getEfundInstructions())
            .append("ploadDocuments", getPloadDocuments())
            .append("refundAmount", getRefundAmount())
            .append("refundNumber", getRefundNumber())
            .append("refundTime", getRefundTime())
            .append("refundSerialNumber", getRefundSerialNumber())
            .append("refundCompany", getRefundCompany())
            .append("refundLogistics", getRefundLogistics())
            .append("refundStatus", getRefundStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
