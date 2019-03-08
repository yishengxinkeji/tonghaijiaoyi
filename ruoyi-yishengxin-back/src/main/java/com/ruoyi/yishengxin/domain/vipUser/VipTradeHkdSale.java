package com.ruoyi.yishengxin.domain.vipUser;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 挂卖HKD表 ysx_vip_trade_hkd_sale
 * 
 * @author ruoyi
 * @date 2019-03-07
 */
@Data
@ToString
public class VipTradeHkdSale extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 用户id */
	private Integer vipId;
	/** 交易类型(挂买HKD为2) */
	private String saleType;
	/** 交易状态(交易中,(交易中,等待买家打款),(交易中,等待卖家确认)交易成功,交易失败) */
	private String saleStatus;
	/** 订单编号 */
	private String saleNo;
	/** 订单数量 */
	private String saleNumber;
	/** 总金额 */
	private String saleTotal;
	/** 时间 */
	private String saleTime;
	/** 买方ID */
	private String buyId;
	/** 买方手机号 */
	private String buyPhone;
	/** 买方名称 */
	private String buyNickname;
	/** 买方头像 */
	private String buyAvater;
	/** 失败原因 */
	private String failReason;
	//是否申诉
	private String isAppeal;
	//申诉状态(Y:成功 . N:失败)
	private String appealStatus;
	//打款凭证
	private String proof;


}
