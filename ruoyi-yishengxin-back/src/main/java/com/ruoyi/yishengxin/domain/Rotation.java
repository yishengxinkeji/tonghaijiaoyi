package com.ruoyi.yishengxin.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 轮播图表 ysx_rotation
 * 
 * @author ruoyi
 * @date 2019-03-12
 */
@Data
@ToString
public class Rotation extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 图片标题 */
	private String picTitle;
	/** 图片地址 */
	private String picDetail;
	/** 图片链接 */
	private String picLink;
	/** 是否显示 Y,是 N.否 */
	private String isShow;


}
