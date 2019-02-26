package com.ruoyi.yishengxin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.DistributionMapper;
import com.ruoyi.yishengxin.domain.Distribution;
import com.ruoyi.yishengxin.service.IDistributionService;
import com.ruoyi.common.support.Convert;

/**
 * 分销设置 服务层实现
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
@Service
public class DistributionServiceImpl implements IDistributionService 
{
	@Autowired
	private DistributionMapper distributionMapper;

	/**
     * 查询分销设置信息
     * 
     * @param id 分销设置ID
     * @return 分销设置信息
     */
    @Override
	public Distribution selectDistributionById(Integer id)
	{
	    return distributionMapper.selectDistributionById(id);
	}
	
	/**
     * 查询分销设置列表
     * 
     * @param distribution 分销设置信息
     * @return 分销设置集合
     */
	@Override
	public List<Distribution> selectDistributionList(Distribution distribution)
	{
	    return distributionMapper.selectDistributionList(distribution);
	}
	
    /**
     * 新增分销设置
     * 
     * @param distribution 分销设置信息
     * @return 结果
     */
	@Override
	public int insertDistribution(Distribution distribution)
	{
	    return distributionMapper.insertDistribution(distribution);
	}
	
	/**
     * 修改分销设置
     * 
     * @param distribution 分销设置信息
     * @return 结果
     */
	@Override
	public int updateDistribution(Distribution distribution)
	{
	    return distributionMapper.updateDistribution(distribution);
	}

	/**
     * 删除分销设置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteDistributionByIds(String ids)
	{
		return distributionMapper.deleteDistributionByIds(Convert.toStrArray(ids));
	}
	
}
