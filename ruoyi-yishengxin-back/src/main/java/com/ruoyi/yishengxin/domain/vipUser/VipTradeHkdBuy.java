package com.ruoyi.yishengxin.domain.vipUser;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 购买HKD表 ysx_vip_trade_hkd_buy
 * 
 * @author ruoyi
 * @date 2019-03-07
 */
@Data
@ToString
public class VipTradeHkdBuy extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 用户id */
	private Integer vipId;
	/** 交易类型(挂买HKD为2) */
	private String buyType;
	/** 交易状态(交易中,(交易中,等待卖家确认),交易成功,交易失败) */
	private String buyStatus;
	/** 订单编号 */
	private String buyNo;
	/** 订单数量 */
	private String buyNumber;
	/** 总金额 */
	private String buyTotal;
	/** 时间 */
	private String buyTime;
	/** 卖方ID */
	private String saleId;
	/** 卖方手机号 */
	private String salePhone;
	/** 卖方名称 */
	private String saleNickname;
	/** 卖方头像 */
	private String saleAvater;
	/** 失败原因 */
	private String failReason;
	//是否申诉
	private String isAppeal;
	//申诉状态(Y:成功 . N:失败)
	private String appealStatus;
	//打款凭证
	private String proof;
	//订单失败前状态
	private String tradeFailStatus;
}
