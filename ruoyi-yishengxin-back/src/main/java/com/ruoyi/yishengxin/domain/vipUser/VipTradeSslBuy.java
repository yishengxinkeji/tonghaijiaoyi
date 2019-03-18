package com.ruoyi.yishengxin.domain.vipUser;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 挂买SSL表 ysx_vip_trade_ssl_buy
 * 
 * @author ruoyi
 * @date 2019-03-18
 */
@Data
@ToString
public class VipTradeSslBuy extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/**  用户id  */
	private Integer vipId;
	/**  交易类型(挂买SSL为7) */
	private String buyType;
	/**  交易状态(1.交易中,2.交易成功,3.交易失败) */
	private String buyStatus;
	/**  订单编号  */
	private String buyNo;
	/**  订单数量  */
	private String buyNumber;
	/**  单价  */
	private String unitPrice;
	/**  总金额  */
	private String buyTotal;
	/**  时间  */
	private String buyTime;
	/**  卖方ID  */
	private String saleId;
	/**  卖方手机号  */
	private String salePhone;
	/**  卖方名称  */
	private String saleNickname;
	/**  卖方头像  */
	private String saleAvater;
	/** 关联订单号 */
	private String relationOrderno;


}
