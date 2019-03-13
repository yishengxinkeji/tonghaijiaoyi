package com.ruoyi.yishengxin.mapper;

import com.ruoyi.yishengxin.domain.Project;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 项目 数据层
 * 
 * @author ruoyi
 * @date 2019-03-12
 */
@Repository
public interface ProjectMapper {
	/**
     * 查询项目信息
     * 
     * @param id 项目ID
     * @return 项目信息
     */
	public Project selectProjectById(Integer id);
	
	/**
     * 查询项目列表
     * 
     * @param project 项目信息
     * @return 项目集合
     */
	public List<Project> selectProjectList(Project project);
	
	/**
     * 新增项目
     * 
     * @param project 项目信息
     * @return 结果
     */
	public int insertProject(Project project);
	
	/**
     * 修改项目
     * 
     * @param project 项目信息
     * @return 结果
     */
	public int updateProject(Project project);
	
	/**
     * 删除项目
     * 
     * @param id 项目ID
     * @return 结果
     */
	public int deleteProjectById(Integer id);
	
	/**
     * 批量删除项目
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectByIds(String[] ids);
	
}