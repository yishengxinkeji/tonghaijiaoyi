package com.ruoyi.yishengxin.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 客服操作会员记录表 ysx_customer_log
 * 
 * @author ruoyi
 * @date 2019-03-14
 */
@Data
@ToString
public class CustomerLog extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 会员id */
	private String vipId;
	/** 操作类型 */
	private String logType;
	/** 操作SSL */
	private String sslMoney;
	/** 操作HKD */
	private String hkdMoney;
	/** 用户手机号 */
	private String phone;
	/** 用户昵称 */
	private String nickname;
	/** 操作原因 */
	private String reason;


}
