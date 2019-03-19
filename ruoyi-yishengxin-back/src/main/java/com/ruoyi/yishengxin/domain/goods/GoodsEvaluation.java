package com.ruoyi.yishengxin.domain.goods;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 商品评价表 ysx_goods_evaluation
 * 
 * @author ruoyi
 * @date 2019-03-19
 */
@Data
@ToString
public class GoodsEvaluation extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 商品评价id */
	private Integer id;
	/** 用户手机号 */
	private String phone;
	/** 订单号 */
	private String oraderNumber;
	/** 商品名 */
	private String goodsName;
	/** 用户的id */
	private Integer uid;
	/** 商品的id */
	private Integer gid;
	/** 订单的id */
	private Integer oid;
	/** 评价内容 */
	private String evaluationContent;
	/** 评价图片 */
	private String evaluationImage;
	/** 描述评价 */
	private Integer describeEvaluation;
	/** 物流评价 */
	private Integer logisticsEvaluation;
	/** 服务态度 */
	private Integer serviceAttitude;
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
