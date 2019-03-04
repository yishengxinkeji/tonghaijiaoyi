package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipTrade;
import com.ruoyi.yishengxin.service.IVipTradeService;
import com.ruoyi.common.support.Convert;

/**
 * 交易记录 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-04
 */
@Service
public class VipTradeServiceImpl implements IVipTradeService {
    @Autowired
    private VipTradeMapper vipTradeMapper;

    /**
     * 查询交易记录信息
     *
     * @param id 交易记录ID
     * @return 交易记录信息
     */
    @Override
    public VipTrade selectVipTradeById(Integer id) {
        return vipTradeMapper.selectVipTradeById(id);
    }

    /**
     * 查询交易记录列表
     *
     * @param vipTrade 交易记录信息
     * @return 交易记录集合
     */
    @Override
    public List<VipTrade> selectVipTradeList(VipTrade vipTrade) {
        return vipTradeMapper.selectVipTradeList(vipTrade);
    }

    /**
     * 新增交易记录
     *
     * @param vipTrade 交易记录信息
     * @return 结果
     */
    @Override
    public int insertVipTrade(VipTrade vipTrade) {
        return vipTradeMapper.insertVipTrade(vipTrade);
    }

    /**
     * 修改交易记录
     *
     * @param vipTrade 交易记录信息
     * @return 结果
     */
    @Override
    public int updateVipTrade(VipTrade vipTrade) {
        return vipTradeMapper.updateVipTrade(vipTrade);
    }

    /**
     * 删除交易记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVipTradeByIds(String ids) {
        return vipTradeMapper.deleteVipTradeByIds(Convert.toStrArray(ids));
    }

}
