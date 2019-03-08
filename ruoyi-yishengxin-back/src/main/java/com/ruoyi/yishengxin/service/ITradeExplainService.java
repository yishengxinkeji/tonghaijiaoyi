package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.TradeExplain;
import java.util.List;

/**
 * 交易说明 服务层
 * 
 * @author ruoyi
 * @date 2019-03-05
 */
public interface ITradeExplainService 
{
	/**
     * 查询交易说明信息
     * 
     * @param id 交易说明ID
     * @return 交易说明信息
     */
	public TradeExplain selectTradeExplainById(Integer id);
	
	/**
     * 查询交易说明列表
     * 
     * @param tradeExplain 交易说明信息
     * @return 交易说明集合
     */
	public List<TradeExplain> selectTradeExplainList(TradeExplain tradeExplain);
	
	/**
     * 新增交易说明
     * 
     * @param tradeExplain 交易说明信息
     * @return 结果
     */
	public int insertTradeExplain(TradeExplain tradeExplain);
	
	/**
     * 修改交易说明
     * 
     * @param tradeExplain 交易说明信息
     * @return 结果
     */
	public int updateTradeExplain(TradeExplain tradeExplain);
		
	/**
     * 删除交易说明信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTradeExplainByIds(String ids);
	
}
