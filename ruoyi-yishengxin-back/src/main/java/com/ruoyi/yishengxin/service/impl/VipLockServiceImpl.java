package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.enums.LockStatus;
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.domain.vipUser.VipTrade;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.mapper.TradeMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipLockMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipLock;
import com.ruoyi.yishengxin.service.IVipLockService;
import com.ruoyi.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * SSL锁仓 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-08
 */
@Service
@Transactional
public class VipLockServiceImpl implements IVipLockService {
    @Autowired
    private VipLockMapper vipLockMapper;
    @Autowired
    private VipUserMapper vipUserMapper;
    @Autowired
    private TradeMapper tradeMapper;

    /**
     * 查询SSL锁仓信息
     *
     * @param id SSL锁仓ID
     * @return SSL锁仓信息
     */
    @Override
    public VipLock selectVipLockById(Integer id) {
        return vipLockMapper.selectVipLockById(id);
    }

    /**
     * 查询SSL锁仓列表
     *
     * @param vipLock SSL锁仓信息
     * @return SSL锁仓集合
     */
    @Override
    public List<VipLock> selectVipLockList(VipLock vipLock) {
        return vipLockMapper.selectVipLockList(vipLock);
    }

    /**
     * 新增SSL锁仓
     *
     * @param vipLock SSL锁仓信息
     * @return 结果
     */
    @Override
    public int insertVipLock(VipLock vipLock) {
        return vipLockMapper.insertVipLock(vipLock);
    }

    /**
     * 修改SSL锁仓
     *
     * @param vipLock SSL锁仓信息
     * @return 结果
     */
    @Override
    public int updateVipLock(VipLock vipLock) {
        return vipLockMapper.updateVipLock(vipLock);
    }

    /**
     * 删除SSL锁仓对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVipLockByIds(String ids) {
        return vipLockMapper.deleteVipLockByIds(Convert.toStrArray(ids));
    }

    /**
     * 锁仓
     *
     * @param vipUser
     * @param vipLock
     * @return
     */
    //插入lock,更新余额
    @Override
    public int vipLock(VipUser vipUser, VipLock vipLock) throws Exception {
        String lockNumber = vipLock.getLockNumber();
        String sslMoney = vipUser.getSslMoney();

        double sub = NumberUtil.sub(Double.parseDouble(sslMoney), Double.parseDouble(lockNumber));
        vipUser.setSslMoney(String.valueOf(sub));

        vipUserMapper.updateVipUser(vipUser);
        return vipLockMapper.insertVipLock(vipLock);
    }

    /**
     * 中断锁仓
     *
     * @param vipUser
     * @param vipLock
     * @return
     */
    //更新订单状态,扣除手续费,更新用户信息
    @Override
    public int interuptLock(VipUser vipUser, VipLock vipLock) {

        Trade trade = tradeMapper.selectTradeList(new Trade()).get(0);
        String lockBreakCharge = trade.getLockBreakCharge();

        //扣除的钱
        double mul = NumberUtil.mul(Double.parseDouble(vipLock.getLockNumber()), Double.parseDouble(lockBreakCharge));
        //给用户返的钱
        double backMoney = NumberUtil.mul(Double.parseDouble(vipLock.getLockNumber()), 1 - Double.parseDouble(lockBreakCharge));

        vipUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(vipUser.getSslMoney()),backMoney)));
        vipLock.setLockStatus(LockStatus.INTERUPT.getCode());
        vipLock.setLockProfit(String.valueOf(0-mul));

        vipUserMapper.updateVipUser(vipUser);
        return vipLockMapper.updateVipLock(vipLock);
    }
}