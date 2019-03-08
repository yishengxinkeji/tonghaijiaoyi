package com.ruoyi.yishengxin.mapper;

import com.ruoyi.yishengxin.domain.Distribution;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分销设置 数据层
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
@Repository
public interface DistributionMapper 
{
	/**
     * 查询分销设置信息
     * 
     * @param id 分销设置ID
     * @return 分销设置信息
     */
	public Distribution selectDistributionById(Integer id);
	
	/**
     * 查询分销设置列表
     * 
     * @param distribution 分销设置信息
     * @return 分销设置集合
     */
	public List<Distribution> selectDistributionList(Distribution distribution);
	
	/**
     * 新增分销设置
     * 
     * @param distribution 分销设置信息
     * @return 结果
     */
	public int insertDistribution(Distribution distribution);
	
	/**
     * 修改分销设置
     * 
     * @param distribution 分销设置信息
     * @return 结果
     */
	public int updateDistribution(Distribution distribution);
	
	/**
     * 删除分销设置
     * 
     * @param id 分销设置ID
     * @return 结果
     */
	public int deleteDistributionById(Integer id);
	
	/**
     * 批量删除分销设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteDistributionByIds(String[] ids);
	
}