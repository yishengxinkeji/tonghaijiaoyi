package com.ruoyi.yishengxin.domain.goods;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 商品订单2表 ysx_goods_order2
 * 
 * @author ruoyi
 * @date 2019-03-25
 */
@Data
@ToString
public class GoodsOrder2 extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 商品订单id */
	private Integer id;
	/** 用户的id */
	private Integer uid;
	/** 商品名称 */
	private String goodsName;
	/** 商品详情 */
	private String goodsDetails;
	/** 商品价格 */
	private String goodsPrice;
	/** 商品购买数量 */
	private Integer goodsSoldNumber;
	/** 商品订单总额 */
	private String goodsOrderTotalAmount;
	/** 商品图片 */
	private String goodsPicture;
	/** 商品状态 */
	private String goodsStatus;
	/** 收货地址id */
	private Integer shippingAddress;
	/** 快递单号 */
	private String courierNumber;
	/** 快递公司 */
	private String courierCompany;
	/** 订单号 */
	private String orderNumber;
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


}
