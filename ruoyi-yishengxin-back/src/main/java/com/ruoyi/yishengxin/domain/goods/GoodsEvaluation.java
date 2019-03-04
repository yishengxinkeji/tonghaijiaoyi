package com.ruoyi.yishengxin.domain.goods;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 商品评价表 ysx_goods_evaluation
 * 
 * @author ruoyi
 * @date 2019-03-04
 */
public class GoodsEvaluation extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 商品评价id */
	private Integer id;
	/** 用户的id */
	private Integer uid;
	/** 商品的id */
	private Integer gid;
	/** 订单的id */
	private Integer oid;
	/** 评价内容 */
	private String evaluationContent;
	/** 评价图片 */
	private String evaluationImage;
	/** 描述评价 */
	private Integer describeEvaluation;
	/** 物流评价 */
	private Integer logisticsEvaluation;
	/** 服务态度 */
	private Integer serviceAttitude;
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
	public void setGid(Integer gid) 
	{
		this.gid = gid;
	}

	public Integer getGid() 
	{
		return gid;
	}
	public void setOid(Integer oid) 
	{
		this.oid = oid;
	}

	public Integer getOid() 
	{
		return oid;
	}
	public void setEvaluationContent(String evaluationContent) 
	{
		this.evaluationContent = evaluationContent;
	}

	public String getEvaluationContent() 
	{
		return evaluationContent;
	}
	public void setEvaluationImage(String evaluationImage) 
	{
		this.evaluationImage = evaluationImage;
	}

	public String getEvaluationImage() 
	{
		return evaluationImage;
	}
	public void setDescribeEvaluation(Integer describeEvaluation) 
	{
		this.describeEvaluation = describeEvaluation;
	}

	public Integer getDescribeEvaluation() 
	{
		return describeEvaluation;
	}
	public void setLogisticsEvaluation(Integer logisticsEvaluation) 
	{
		this.logisticsEvaluation = logisticsEvaluation;
	}

	public Integer getLogisticsEvaluation() 
	{
		return logisticsEvaluation;
	}
	public void setServiceAttitude(Integer serviceAttitude) 
	{
		this.serviceAttitude = serviceAttitude;
	}

	public Integer getServiceAttitude() 
	{
		return serviceAttitude;
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
            .append("gid", getGid())
            .append("oid", getOid())
            .append("evaluationContent", getEvaluationContent())
            .append("evaluationImage", getEvaluationImage())
            .append("describeEvaluation", getDescribeEvaluation())
            .append("logisticsEvaluation", getLogisticsEvaluation())
            .append("serviceAttitude", getServiceAttitude())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
