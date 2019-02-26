package com.ruoyi.yishengxin.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 分销设置表 ysx_distribution
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
public class Distribution extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private Integer id;
	/** 手续费比例 */
	private String charge;
	/** 上级手续费比例 */
	private String parentCharge;
	/** 上上级手续费比例 */
	private String grandparentCharge;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setCharge(String charge) 
	{
		this.charge = charge;
	}

	public String getCharge() 
	{
		return charge;
	}
	public void setParentCharge(String parentCharge) 
	{
		this.parentCharge = parentCharge;
	}

	public String getParentCharge() 
	{
		return parentCharge;
	}
	public void setGrandparentCharge(String grandparentCharge) 
	{
		this.grandparentCharge = grandparentCharge;
	}

	public String getGrandparentCharge() 
	{
		return grandparentCharge;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("charge", getCharge())
            .append("parentCharge", getParentCharge())
            .append("grandparentCharge", getGrandparentCharge())
            .toString();
    }
}
