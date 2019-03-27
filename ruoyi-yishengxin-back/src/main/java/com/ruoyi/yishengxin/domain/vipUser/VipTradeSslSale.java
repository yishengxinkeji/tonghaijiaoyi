package com.ruoyi.yishengxin.domain.vipUser;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 挂卖SSL表 ysx_vip_trade_ssl_sale
 * 
 * @author ruoyi
 * @date 2019-03-18
 */
@Data
@ToString
public class VipTradeSslSale extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 用户id */
	private Integer vipId;
	/** 交易类型(挂卖SSL为6) */
	private String saleType;
	/** 交易状态 */
	private String saleStatus;
	/** 订单编号 */
	private String saleNo;
	/** 订单数量 */
	private String saleNumber;
	/** 单价 */
	private String unitPrice;
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
	/** 关联订单号 */
	private String relationOrderno;
	/** 订单扣除的手续费 */
	private String chargeMoney;


}
