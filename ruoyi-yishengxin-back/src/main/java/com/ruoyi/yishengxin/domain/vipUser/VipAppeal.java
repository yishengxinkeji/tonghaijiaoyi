package com.ruoyi.yishengxin.domain.vipUser;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 我的申诉表 ysx_vip_appeal
 * 
 * @author ruoyi
 * @date 2019-03-08
 */
@Data
@ToString
public class VipAppeal extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 用户id */
	private Integer vipId;
	/** 申诉内容 */
	private String content;
	/** 申诉时间 */
	private String appealTime;
	/** 申诉状态(成功/失败) */
	private String appealStatus;
	/** 订单类型(卖/买) */
	private String orderType;
	/** 订单id */
	private String orderId;


}
