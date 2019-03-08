package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslSale;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.mapper.TradeMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeSslSaleMapper;
import com.ruoyi.yishengxin.service.IVipTradeSslSaleService;
import com.ruoyi.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 挂卖SSL 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-06
 */
@Service
@Transactional
public class VipTradeSslSaleServiceImpl implements IVipTradeSslSaleService {
    @Autowired
    private VipTradeSslSaleMapper vipTradeSslSaleMapper;

    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private VipUserMapper vipUserMapper;


    /**
     * 查询挂卖SSL信息
     *
     * @param id 挂卖SSLID
     * @return 挂卖SSL信息
     */
    @Override
    public VipTradeSslSale selectVipTradeSaleById(Integer id) {
        return vipTradeSslSaleMapper.selectVipTradeSaleById(id);
    }

    /**
     * 查询挂卖SSL列表
     *
     * @param vipTradeSslSale 挂卖SSL信息
     * @return 挂卖SSL集合
     */
    @Override
    public List<VipTradeSslSale> selectVipTradeSaleList(VipTradeSslSale vipTradeSslSale) {
        return vipTradeSslSaleMapper.selectVipTradeSaleList(vipTradeSslSale);
    }

    /**
     * 新增挂卖SSL
     *
     * @param vipTradeSslSale 挂卖SSL信息
     * @return 结果
     */
    @Override
    public int insertVipTradeSale(VipTradeSslSale vipTradeSslSale) {
        return vipTradeSslSaleMapper.insertVipTradeSale(vipTradeSslSale);
    }

    /**
     * 修改挂卖SSL
     *
     * @param vipTradeSslSale 挂卖SSL信息
     * @return 结果
     */
    @Override
    public int updateVipTradeSale(VipTradeSslSale vipTradeSslSale) {
        return vipTradeSslSaleMapper.updateVipTradeSale(vipTradeSslSale);
    }

    /**
     * 删除挂卖SSL对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVipTradeSaleByIds(String ids) {
        return vipTradeSslSaleMapper.deleteVipTradeSaleByIds(Convert.toStrArray(ids));
    }

    /**
     * 挂卖SSL
     *
     * @param vipUser
     * @param number
     * @param price
     * @return 100.ssl币不足 200.今日交易已达上限 300.已超单次交易量上限
     */
    //保存交易订单
    //更新用户信息
    @Override
    public int saleSsl(VipUser vipUser, String number, String price) throws Exception{
        //查询其余额是否足够
        VipUser vipUser1 = vipUserMapper.selectVipUserById(vipUser.getId());
        double ssl = Double.parseDouble(vipUser1.getSslMoney());
        double sslCharge = 0.00;
        double maxTradeDay = 0.00; //每天最大交易量
        double maxTradeTime = 0.00; //每次最大交易量

        List<Trade> trades = tradeMapper.selectTradeList(new Trade());
        if (trades.size() > 0) {
            sslCharge = Double.parseDouble(trades.get(0).getSslCharge());
            maxTradeDay = Double.parseDouble(trades.get(0).getMaxSslTradeDay());
            maxTradeTime = Double.parseDouble(trades.get(0).getMaxSslTradeTime());
        }

        //查询该用户今日总共交易了多少ssl
        double maxNumber = vipTradeSslSaleMapper.selectSslMaxNumberByDay(vipUser.getId());
        if(maxNumber > maxTradeDay) {
            //交易已达上限
            return 200;
        }

        if(Double.parseDouble(number) > maxTradeTime ){
            //单次交易已超上限
            return 300;
        }

        //交易的是ssl
        double mul = NumberUtil.mul(Double.parseDouble(number), Double.parseDouble(price));
        if (mul > ssl) {
            //ssl余额不足
            return 100;
        }
        //内扣手续费后,实际应得的
        double mulCharge = NumberUtil.mul(mul, NumberUtil.sub(1, sslCharge));

        VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
        vipTradeSslSale.setVipId(vipUser.getId());
        vipTradeSslSale.setSaleStatus(TradeStatus.TRADING.getCode());
        vipTradeSslSale.setSaleNo(IdUtil.simpleUUID());
        vipTradeSslSale.setSaleNumber(number);
        vipTradeSslSale.setUnitPrice(price);
        vipTradeSslSale.setSaleTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));
        vipTradeSslSale.setSaleTotal(String.valueOf(mulCharge));

        vipTradeSslSaleMapper.insertVipTradeSale(vipTradeSslSale);
        vipUser.setSslMoney(String.valueOf(NumberUtil.sub(ssl, Double.parseDouble(number))));
        return vipUserMapper.updateVipUser(vipUser);
    }



}
