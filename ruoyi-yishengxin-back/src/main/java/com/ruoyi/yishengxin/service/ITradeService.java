package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.Trade;

import java.util.List;

/**
 * 交易设置 服务层
 *
 * @author ruoyi
 * @date 2019-03-13
 */
public interface ITradeService {
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
     * 删除交易设置信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTradeByIds(String ids);

}
