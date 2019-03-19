package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.yishengxin.domain.vipUser.VipExchange;
import com.ruoyi.yishengxin.domain.vipUser.VipTrade;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipBuyMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipBuy;
import com.ruoyi.yishengxin.service.IVipBuyService;
import com.ruoyi.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 个人购买 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-02
 */
@Service
@Transactional
public class VipBuyServiceImpl implements IVipBuyService {
    @Autowired
    private VipBuyMapper vipBuyMapper;

    @Autowired
    private VipUserMapper vipUserMapper;

    @Autowired
    private VipTradeMapper vipTradeMapper;

    /**
     * 查询个人购买信息
     *
     * @param id 个人购买ID
     * @return 个人购买信息
     */
    @Override
    public VipBuy selectVipBuyById(Integer id) {
        return vipBuyMapper.selectVipBuyById(id);
    }

    /**
     * 查询个人购买列表
     *
     * @param vipBuy 个人购买信息
     * @return 个人购买集合
     */
    @Override
    public List<VipBuy> selectVipBuyList(VipBuy vipBuy) {
        return vipBuyMapper.selectVipBuyList(vipBuy);
    }

    /**
     * 新增个人购买
     *
     * @param vipBuy 个人购买信息
     * @return 结果
     */
    @Override
    public int insertVipBuy(VipBuy vipBuy) {
        return vipBuyMapper.insertVipBuy(vipBuy);
    }

    /**
     * 修改个人购买
     *
     * @param vipBuy 个人购买信息
     * @return 结果
     */
    @Override
    public int updateVipBuy(VipBuy vipBuy) {
        return vipBuyMapper.updateVipBuy(vipBuy);
    }

    /**
     * 删除个人购买对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVipBuyByIds(String ids) {
        return vipBuyMapper.deleteVipBuyByIds(Convert.toStrArray(ids));
    }

    /**
     * 客服处理用户购买
     * 更新购买信息
     * 更新用户余额
     * 查询到交易表中
     *
     * @param vipBuy
     * @return
     */
    @Override
    public int exchange(VipBuy vipBuy) throws Exception {

        String buyAmount = vipBuy.getBuyAmount();
        VipUser vipUser = vipUserMapper.selectVipUserById(vipBuy.getVipId());
        double add = NumberUtil.add(Double.parseDouble(vipUser.getHkdMoney()), Double.parseDouble(buyAmount));
        double sub = NumberUtil.round(add, CustomerConstants.ROUND_NUMBER).doubleValue();
        vipUser.setHkdMoney(String.valueOf(sub));

        VipTrade trade = new VipTrade();
        trade.setVipId(vipUser.getId());
        trade.setVipTrade(TradeType.BACK_BUY.getCode());
        trade.setTradeTime(DateUtils.dateTimeNow("yyyy-MM-dd HH:mm"));
        trade.setTradeNumber(buyAmount);

        //更新购买信息
        updateVipBuy(vipBuy);
        //更新用户余额
        vipUserMapper.updateVipUser(vipUser);
        //插入到交易信息表中
        return vipTradeMapper.insertVipTrade(trade);
    }

    @Override
    public double selectSum() {
        return vipBuyMapper.selectSum();
    }

    @Override
    public double selectSumByTime(String s, DateTime begin, DateTime end) {
        return vipBuyMapper.selectSumByTime(s, begin, end);
    }
}
