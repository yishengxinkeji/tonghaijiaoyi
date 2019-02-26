package com.ruoyi.yishengxin.mapper;

import com.ruoyi.yishengxin.domain.Trade;
import java.util.List;	

/**
 * 交易设置 数据层
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
public interface TradeMapper 
{
	/**
     * 查询交易设置信息
     * 
     * @param id 交易设置ID
     * @return 交易设置信息
     */
	public Trade selectTradeById(Integer id);
	
	/**
     * 查询交易设置列表
     * 
     * @param trade 交易设置信息
     * @return 交易设置集合
     */
	public List<Trade> selectTradeList(Trade trade);
	
	/**
     * 新增交易设置
     * 
     * @param trade 交易设置信息
     * @return 结果
     */
	public int insertTrade(Trade trade);
	
	/**
     * 修改交易设置
     * 
     * @param trade 交易设置信息
     * @return 结果
     */
	public int updateTrade(Trade trade);
	
	/**
     * 删除交易设置
     * 
     * @param id 交易设置ID
     * @return 结果
     */
	public int deleteTradeById(Integer id);
	
	/**
     * 批量删除交易设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTradeByIds(String[] ids);
	
}