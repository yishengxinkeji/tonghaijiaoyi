package com.ruoyi.yishengxin.domain.goods;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 狗表 dog
 * 
 * @author ruoyi
 * @date 2019-03-22
 */
@Data
@ToString
public class Dog extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 消息标题 */
	private String name;
	/** tyet */
	private String betdfa;


}
