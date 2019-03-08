package com.ruoyi.yishengxin.domain.goods;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 商品分享表 ysx_goods_share
 * 
 * @author ruoyi
 * @date 2019-03-06
 */
public class GoodsShare extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 分享id */
	private Integer id;
	/** 用户id */
	private Integer uid;
	/** 赏金 */
	private String bounty;
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
	public void setBounty(String bounty) 
	{
		this.bounty = bounty;
	}

	public String getBounty() 
	{
		return bounty;
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
            .append("bounty", getBounty())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
