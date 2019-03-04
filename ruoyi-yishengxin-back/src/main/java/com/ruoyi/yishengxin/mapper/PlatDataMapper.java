package com.ruoyi.yishengxin.mapper;

import com.ruoyi.yishengxin.domain.PlatData;

import java.util.List;

/**
 * 平台基本配置 数据层
 * 
 * @author ruoyi
 * @date 2019-03-04
 */
public interface PlatDataMapper 
{
	/**
     * 查询平台基本配置信息
     * 
     * @param id 平台基本配置ID
     * @return 平台基本配置信息
     */
	public PlatData selectPlatDataById(Integer id);
	
	/**
     * 查询平台基本配置列表
     * 
     * @param platData 平台基本配置信息
     * @return 平台基本配置集合
     */
	public List<PlatData> selectPlatDataList(PlatData platData);
	
	/**
     * 新增平台基本配置
     * 
     * @param platData 平台基本配置信息
     * @return 结果
     */
	public int insertPlatData(PlatData platData);
	
	/**
     * 修改平台基本配置
     * 
     * @param platData 平台基本配置信息
     * @return 结果
     */
	public int updatePlatData(PlatData platData);
	
	/**
     * 删除平台基本配置
     * 
     * @param id 平台基本配置ID
     * @return 结果
     */
	public int deletePlatDataById(Integer id);
	
	/**
     * 批量删除平台基本配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deletePlatDataByIds(String[] ids);
	
}