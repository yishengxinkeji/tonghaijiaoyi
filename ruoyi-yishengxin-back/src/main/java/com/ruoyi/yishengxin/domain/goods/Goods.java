package com.ruoyi.yishengxin.domain.goods;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 商品表 ysx_goods
 * 
 * @author ruoyi
 * @date 2019-03-15
 */
@Data
@ToString
public class Goods extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 商品id */
	private Integer id;
	/** 大图 */
	private String bigPicture;
	/** 中图 */
	private String centerPicture;
	/** 小图 */
	private String smallPicture;
	/** 商品名称 */
	private String goodsName;
	/** 商品介绍 */
	private String goodsIntroduce;
	/** 商品收藏状态 */
	private Integer collectionStatus;
	/** 商品详情 */
	private String goodsDetails;
	/** 商品价格 */
	private String goodsPrice;
	/** 商品详情图 */
	private String goodsDetailsPicture;
	/** 商品库存 */
	private Integer goodsInventory;
	/** 商品售出量 */
	private Integer goodsSoldNumber;
	/** 商品上下架 */
	private String standUpAndDown;
	/** 客服电话 */
	private String serviceTel;
	/** 商品轮播图 */
	private String goodsSlideShow;
	/** 商品主图 */
	private String goodsMainFigure;
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
