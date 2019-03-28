package com.ruoyi.yishengxin.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 转入转出说明表 ysx_transfer
 * 
 * @author ruoyi
 * @date 2019-03-28
 */
@Data
@ToString
public class Transfer extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 说明标题 */
	private String title;
	/** 说明内容 */
	private String content;


}
