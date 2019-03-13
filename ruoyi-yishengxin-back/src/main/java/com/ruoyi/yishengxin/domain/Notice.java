package com.ruoyi.yishengxin.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 公告中心表 ysx_notice
 * 
 * @author ruoyi
 * @date 2019-03-12
 */
@Data
@ToString
public class Notice extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 公告标题 */
	private String noticeTitle;
	/** 公告时间 */
	private String noticeTime;
	/** 公告简介 */
	private String noticeIntroduction;
	/** 公告内容 */
	private String noticeContent;


}
