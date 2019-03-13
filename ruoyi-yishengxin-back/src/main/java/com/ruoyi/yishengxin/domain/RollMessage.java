package com.ruoyi.yishengxin.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 滚动消息表 ysx_roll_message
 * 
 * @author ruoyi
 * @date 2019-03-13
 */
@Data
@ToString
public class RollMessage extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 消息标题 */
	private String rollTitle;
	/** 消息内容 */
	private String rollContent;
	/** 是否显示 Y,是 N.否 */
	private String isShow;


}
