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
 * @date 2019-03-13
 */
@Data
@ToString
public class VipAppeal extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 卖家手机号 */
	private String salePhone;
	/** 订单号 */
	private String orderNo;
	/** 买家手机号 */
	private String buyPhone;
	/** 买家id */
	private Integer buyId;
	/** 卖家id */
	private Integer saleId;
	/** 申诉内容 */
	private String content;
	/** 申诉时间 */
	private String appealTime;
	/** 申诉状态(申诉中/成功/失败) */
	private String appealStatus;
	/** 订单类型(卖/买) */
	private String orderType;
	/** 订单id */
	private String orderId;
	/** 责任归属(买家/卖家) */
	private String dutyFor;
	/** 申诉原因(订单失败原因) */
	private String appealReason;
	//申诉人
	private String appealVipId;



}
