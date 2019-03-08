package com.ruoyi.yishengxin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.TradeMapper;
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.service.ITradeService;
import com.ruoyi.common.support.Convert;

/**
 * 交易设置 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-06
 */
@Service
public class TradeServiceImpl implements ITradeService 
{
	@Autowired
	private TradeMapper tradeMapper;

	/**
     * 查询交易设置信息
     * 
     * @param id 交易设置ID
     * @return 交易设置信息
     */
    @Override
	public Trade selectTradeById(Integer id)
	{
	    return tradeMapper.selectTradeById(id);
	}
	
	/**
     * 查询交易设置列表
     * 
     * @param trade 交易设置信息
     * @return 交易设置集合
     */
	@Override
	public List<Trade> selectTradeList(Trade trade)
	{
	    return tradeMapper.selectTradeList(trade);
	}
	
    /**
     * 新增交易设置
     * 
     * @param trade 交易设置信息
     * @return 结果
     */
	@Override
	public int insertTrade(Trade trade)
	{
	    return tradeMapper.insertTrade(trade);
	}
	
	/**
     * 修改交易设置
     * 
     * @param trade 交易设置信息
     * @return 结果
     */
	@Override
	public int updateTrade(Trade trade)
	{
	    return tradeMapper.updateTrade(trade);
	}

	/**
     * 删除交易设置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTradeByIds(String ids)
	{
		return tradeMapper.deleteTradeByIds(Convert.toStrArray(ids));
	}
	
}
