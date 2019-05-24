package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.TradeExplainMapper;
import com.ruoyi.yishengxin.domain.TradeExplain;
import com.ruoyi.yishengxin.service.ITradeExplainService;
import com.ruoyi.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 交易说明 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-05
 */
@Service
public class TradeExplainServiceImpl implements ITradeExplainService {
    @Autowired
    private TradeExplainMapper tradeExplainMapper;

    /**
     * 查询交易说明信息
     *
     * @param id 交易说明ID
     * @return 交易说明信息
     */
    @Override
    public TradeExplain selectTradeExplainById(Integer id) {
        return tradeExplainMapper.selectTradeExplainById(id);
    }

    /**
     * 查询交易说明列表
     *
     * @param tradeExplain 交易说明信息
     * @return 交易说明集合
     */
    @Override
    public List<TradeExplain> selectTradeExplainList(TradeExplain tradeExplain) {
        return tradeExplainMapper.selectTradeExplainList(tradeExplain);
    }

    /**
     * 新增交易说明
     *
     * @param tradeExplain 交易说明信息
     * @return 结果
     */
    @Override
    public int insertTradeExplain(TradeExplain tradeExplain) {
        return tradeExplainMapper.insertTradeExplain(tradeExplain);
    }

    /**
     * 修改交易说明
     *
     * @param tradeExplain 交易说明信息
     * @return 结果
     */
    @Override
    public int updateTradeExplain(TradeExplain tradeExplain) {
        return tradeExplainMapper.updateTradeExplain(tradeExplain);
    }

    /**
     * 删除交易说明对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTradeExplainByIds(String ids) {
        return tradeExplainMapper.deleteTradeExplainByIds(Convert.toStrArray(ids));
    }

}
