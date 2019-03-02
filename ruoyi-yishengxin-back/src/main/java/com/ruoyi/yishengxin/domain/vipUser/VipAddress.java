package com.ruoyi.yishengxin.domain.vipUser;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 会员地址表 ysx_vip_address
 * 
 * @author ruoyi
 * @date 2019-02-27
 */
public class VipAddress extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 用户id */
	private Integer vipId;
	/** 手机号 */
	private String phone;
	/** 收货人 */
	private String receivUser;
	/** 地址详情 */
	private String addressDetail;
	/** 是否默认 Y 是 N 否 */
	private String isDefault;
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
	/** 省 */
	private String province;
	/** 市 */
	private String city;
	/** 区 */
	private String district;

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
	public void setPhone(String phone) 
	{
		this.phone = phone;
	}

	public String getPhone() 
	{
		return phone;
	}
	public void setReceivUser(String receivUser) 
	{
		this.receivUser = receivUser;
	}

	public String getReceivUser() 
	{
		return receivUser;
	}
	public void setAddressDetail(String addressDetail) 
	{
		this.addressDetail = addressDetail;
	}

	public String getAddressDetail() 
	{
		return addressDetail;
	}
	public void setIsDefault(String isDefault) 
	{
		this.isDefault = isDefault;
	}

	public String getIsDefault() 
	{
		return isDefault;
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
	public void setProvince(String province) 
	{
		this.province = province;
	}

	public String getProvince() 
	{
		return province;
	}
	public void setCity(String city) 
	{
		this.city = city;
	}

	public String getCity() 
	{
		return city;
	}
	public void setDistrict(String district) 
	{
		this.district = district;
	}

	public String getDistrict() 
	{
		return district;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("vipId", getVipId())
            .append("phone", getPhone())
            .append("receivUser", getReceivUser())
            .append("addressDetail", getAddressDetail())
            .append("isDefault", getIsDefault())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("province", getProvince())
            .append("city", getCity())
            .append("district", getDistrict())
            .toString();
    }
}
