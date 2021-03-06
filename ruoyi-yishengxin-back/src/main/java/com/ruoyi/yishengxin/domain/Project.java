package com.ruoyi.yishengxin.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 项目表 ysx_project
 * 
 * @author ruoyi
 * @date 2019-03-12
 */
@Data
@ToString
public class Project extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/**  项目标题  */
	private String projectTitle;
	/**  项目时间  */
	private String projectTime;
	/**  项目图片  */
	private String projectPic;
	/**  项目简介  */
	private String projectIntroduction;
	/**  项目内容  */
	private String projectContent;
	//认购单价
	private String unitPrice;
	//认购最大购买量
	private String maxNumber;
	//认购最小购买量
	private String minNumber;
	//认购总量
	private String projectNumber;
	//认购余量
	private String projectOver;
	//项目结束时间
	private String projectEnd;
}
