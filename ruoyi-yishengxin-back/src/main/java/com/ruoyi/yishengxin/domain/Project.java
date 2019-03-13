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


}
