package com.ruoyi.yishengxin.domain.vipUser;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * SSL锁仓表 ysx_vip_lock
 * 
 * @author ruoyi
 * @date 2019-03-08
 */
@Data
@ToString
public class VipLock extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 用户id */
	private Integer vipId;
	/** 锁仓时间 */
	private String lockTime;
	/** 锁仓类型(1,3,6,12) */
	private String lockType;
	/** 锁仓数量 */
	private String lockNumber;
	/** 本息获利 */
	private String lockProfit;
	/** 锁仓状态(锁仓中,已收货,中断锁仓) */
	private String lockStatus;
	//到期时间
	private String lockExpire;


}
