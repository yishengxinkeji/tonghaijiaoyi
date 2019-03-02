package com.ruoyi.yishengxin.domain.vipUser;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 收款账户表 ysx_vip_account
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
public class VipAccount extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 用户id */
	private Integer vipId;
	/** 账户类型 */
	private String accountType;
	/** 账户姓名 */
	private String accountName;
	/** 账户号 */
	private String accountNumber;
	/** 账户图片 */
	private String accountImg;
	/** 是否默认 */
	private String isDefault;

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
	public void setAccountType(String accountType) 
	{
		this.accountType = accountType;
	}

	public String getAccountType() 
	{
		return accountType;
	}
	public void setAccountName(String accountName) 
	{
		this.accountName = accountName;
	}

	public String getAccountName() 
	{
		return accountName;
	}
	public void setAccountNumber(String accountNumber) 
	{
		this.accountNumber = accountNumber;
	}

	public String getAccountNumber() 
	{
		return accountNumber;
	}
	public void setAccountImg(String accountImg) 
	{
		this.accountImg = accountImg;
	}

	public String getAccountImg() 
	{
		return accountImg;
	}
	public void setIsDefault(String isDefault) 
	{
		this.isDefault = isDefault;
	}

	public String getIsDefault() 
	{
		return isDefault;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("vipId", getVipId())
            .append("accountType", getAccountType())
            .append("accountName", getAccountName())
            .append("accountNumber", getAccountNumber())
            .append("accountImg", getAccountImg())
            .append("isDefault", getIsDefault())
            .toString();
    }
}
