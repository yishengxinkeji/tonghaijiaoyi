package com.ruoyi.yishengxin.service.impl;

import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.LockStatus;
import com.ruoyi.common.enums.LockType;
import com.ruoyi.common.utils.DateUtils;
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
    @Transactional
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
    @Transactional
    public int interuptLock(VipUser vipUser, VipLock vipLock) {

        Trade trade = tradeMapper.selectTradeList(new Trade()).get(0);
        String lockBreakCharge = trade.getLockBreakCharge();

        //扣除的钱
        double mul = NumberUtil.mul(Double.parseDouble(vipLock.getLockNumber()), Double.parseDouble(lockBreakCharge));
        //给用户返的钱
        double backMoney = NumberUtil.sub(Double.parseDouble(vipLock.getLockNumber()), mul);

        vipUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(vipUser.getSslMoney()),backMoney)));
        vipLock.setLockStatus(LockStatus.INTERUPT.getCode());
        vipLock.setDeduct("-"+NumberUtil.roundStr(mul, CustomerConstants.ROUND_NUMBER));

        vipUserMapper.updateVipUser(vipUser);
        return vipLockMapper.updateVipLock(vipLock);
    }

    /**
     * 每天早上0点更新今日到期的锁仓数据
     */
    @Override
    @Transactional
    public void updateTimerLock() {
        VipLock vipLock = new VipLock();
        vipLock.setLockExpire(DateUtil.format(new Date(), DateUtils.YYYY_MM_DD));
        vipLock.setLockStatus(LockStatus.LOCKING.getCode());
        List<VipLock> vipLocks = vipLockMapper.selectVipLockList(vipLock);
        if(vipLocks.size() > 0){
            //查找今日锁仓到期的数据,更新状态,并更新用户余额
            vipLocks.stream().forEach(vipLock1 -> {
                Integer vipId = vipLock1.getVipId();
                VipUser vipUser = vipUserMapper.selectVipUserById(vipId);
                String lockProfit = vipLock1.getLockProfit();
                vipUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(vipUser.getSslMoney()),Double.parseDouble(lockProfit))));
                vipLock1.setLockStatus(LockStatus.FINISH.getCode());
                vipUserMapper.updateVipUser(vipUser);
                vipLockMapper.updateVipLock(vipLock1);
            });
        }
    }
}
