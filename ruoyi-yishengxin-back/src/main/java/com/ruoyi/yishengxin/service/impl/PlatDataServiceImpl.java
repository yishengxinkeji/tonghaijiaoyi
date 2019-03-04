package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import com.ruoyi.yishengxin.domain.PlatData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.PlatDataMapper;
import com.ruoyi.yishengxin.service.IPlatDataService;
import com.ruoyi.common.support.Convert;

/**
 * 平台基本配置 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-04
 */
@Service
public class PlatDataServiceImpl implements IPlatDataService 
{
	@Autowired
	private PlatDataMapper platDataMapper;

	/**
     * 查询平台基本配置信息
     * 
     * @param id 平台基本配置ID
     * @return 平台基本配置信息
     */
    @Override
	public PlatData selectPlatDataById(Integer id)
	{
	    return platDataMapper.selectPlatDataById(id);
	}
	
	/**
     * 查询平台基本配置列表
     * 
     * @param platData 平台基本配置信息
     * @return 平台基本配置集合
     */
	@Override
	public List<PlatData> selectPlatDataList(PlatData platData)
	{
	    return platDataMapper.selectPlatDataList(platData);
	}
	
    /**
     * 新增平台基本配置
     * 
     * @param platData 平台基本配置信息
     * @return 结果
     */
	@Override
	public int insertPlatData(PlatData platData)
	{
	    return platDataMapper.insertPlatData(platData);
	}
	
	/**
     * 修改平台基本配置
     * 
     * @param platData 平台基本配置信息
     * @return 结果
     */
	@Override
	public int updatePlatData(PlatData platData)
	{
	    return platDataMapper.updatePlatData(platData);
	}

	/**
     * 删除平台基本配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePlatDataByIds(String ids)
	{
		return platDataMapper.deletePlatDataByIds(Convert.toStrArray(ids));
	}
	
}
