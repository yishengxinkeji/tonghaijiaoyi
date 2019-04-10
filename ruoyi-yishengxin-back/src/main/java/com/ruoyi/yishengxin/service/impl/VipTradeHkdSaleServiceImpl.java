package com.ruoyi.yishengxin.service.impl;

import java.math.RoundingMode;
import java.util.List;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.exception.frontException.VipUserException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.domain.vipUser.*;
import com.ruoyi.yishengxin.mapper.DistributionMapper;
import com.ruoyi.yishengxin.mapper.TradeMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipProfitDetailMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeHkdBuyMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeHkdSaleMapper;
import com.ruoyi.yishengxin.service.IVipTradeHkdSaleService;
import com.ruoyi.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 挂卖HKD 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-07
 */
@Service
@Transactional
public class VipTradeHkdSaleServiceImpl implements IVipTradeHkdSaleService {
    @Autowired
    private VipTradeHkdSaleMapper vipTradeHkdSaleMapper;
    @Autowired
    private VipTradeHkdBuyMapper vipTradeHkdBuyMapper;

    @Autowired
    private VipUserMapper vipUserMapper;
    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private DistributionMapper distributionMapper;

    @Autowired
    private VipProfitDetailMapper vipProfitDetailMapper;
    /**
     * 查询挂卖HKD信息
     *
     * @param id 挂卖HKDID
     * @return 挂卖HKD信息
     */
    @Override
    public VipTradeHkdSale selectVipTradeHkdSaleById(Integer id) {
        return vipTradeHkdSaleMapper.selectVipTradeHkdSaleById(id);
    }

    /**
     * 查询挂卖HKD列表
     *
     * @param vipTradeHkdSale 挂卖HKD信息
     * @return 挂卖HKD集合
     */
    @Override
    public List<VipTradeHkdSale> selectVipTradeHkdSaleList(VipTradeHkdSale vipTradeHkdSale) {
        return vipTradeHkdSaleMapper.selectVipTradeHkdSaleList(vipTradeHkdSale);
    }

    /**
     * 新增挂卖HKD
     *
     * @param vipTradeHkdSale 挂卖HKD信息
     * @return 结果
     */
    @Override
    public int insertVipTradeHkdSale(VipTradeHkdSale vipTradeHkdSale) {
        return vipTradeHkdSaleMapper.insertVipTradeHkdSale(vipTradeHkdSale);
    }

    /**
     * 修改挂卖HKD
     *
     * @param vipTradeHkdSale 挂卖HKD信息
     * @return 结果
     */
    @Override
    public int updateVipTradeHkdSale(VipTradeHkdSale vipTradeHkdSale) {
        return vipTradeHkdSaleMapper.updateVipTradeHkdSale(vipTradeHkdSale);
    }

    /**
     * 删除挂卖HKD对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVipTradeHkdSaleByIds(String ids) {
        return vipTradeHkdSaleMapper.deleteVipTradeHkdSaleByIds(Convert.toStrArray(ids));
    }

    /**
     * 挂卖HKD
     * @param vipUser
     * @param number
     * @param vipAccount
     * @return
     */
    @Override
    public int saleHkd(VipUser vipUser, String number, VipAccount vipAccount) {
        //查询其余额是否足够
        VipUser vipUser1 = vipUserMapper.selectVipUserById(vipUser.getId());
        String special = vipUser1.getSpecial();
        double hkdMoney = Double.parseDouble(vipUser1.getHkdMoney());
        double hkd = Double.parseDouble(vipUser1.getHkdMoney());
        double hkdCharge = 0.00;
        double maxTradeDay = 0.00; //每天最大交易量
        double maxTradeTime = 0.00; //每次最大交易量

        List<Trade> trades = tradeMapper.selectTradeList(new Trade());
        if (trades.size() > 0) {
            hkdCharge = Double.parseDouble(trades.get(0).getHkdCharge());
            maxTradeDay = Double.parseDouble(trades.get(0).getMaxHdkTradeDay());
            maxTradeTime = NumberUtil.mul(hkdMoney,Double.parseDouble(trades.get(0).getMaxHdkTradeTime())/100);
        }

        if (Double.parseDouble(number) > hkd) {
            //hkd余额不足
            return 100;
        }

        if(special.equals(CustomerConstants.NO)){
            //查询该用户今日总共交易了多少ssl
            double maxNumber = vipTradeHkdSaleMapper.selectHkdMaxNumberByDay(vipUser.getId());
            if((maxNumber+Double.parseDouble(number)) > maxTradeDay) {
                //交易已达上限
                return 200;
            }
            if(Double.parseDouble(number) > maxTradeTime ){
                //单次交易已超上限
                return 300;
            }
        }

        //内扣手续费之后,实际卖的HKD是多少
        double mul = NumberUtil.mul(Double.parseDouble(number), NumberUtil.sub(1, hkdCharge));
        double mulCharge = NumberUtil.round(mul,CustomerConstants.ROUND_NUMBER).doubleValue();

        VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
        vipTradeHkdSale.setVipId(vipUser.getId());
        vipTradeHkdSale.setSaleStatus(TradeStatus.WAITING.getCode());
        vipTradeHkdSale.setSaleNo(IdUtil.simpleUUID());
        vipTradeHkdSale.setSaleNumber(NumberUtil.roundStr(mulCharge,CustomerConstants.ROUND_NUMBER));       //订单数量
        vipTradeHkdSale.setSaleTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));
        vipTradeHkdSale.setIsAppeal(CustomerConstants.NO);
        vipTradeHkdSale.setSaleTotal(String.valueOf(mulCharge));    //实际订单数量,是扣除手续费之后的数量
        vipTradeHkdSale.setSaleType(TradeType.SALE_HKD.getCode());
        vipTradeHkdSale.setSaleAccount(vipAccount.getAccountNumber());
        vipTradeHkdSale.setSaleAccountProof(vipAccount.getAccountImg());

        vipTradeHkdSaleMapper.insertVipTradeHkdSale(vipTradeHkdSale);
        double sub = NumberUtil.sub(hkd, Double.parseDouble(number));
        vipUser.setHkdMoney(NumberUtil.roundStr(sub,CustomerConstants.ROUND_NUMBER));
        return vipUserMapper.updateVipUser(vipUser);
    }

    /**
     * 确认收款
     *
     * @param vipUser
     * @param id
     * @throws Exception
     */
    //更新两个的订单状态,更新买家账户信息, 更新余额
    @Override
    public void confirmOrder(VipUser vipUser, String id) throws Exception {

        try{
            VipTradeHkdSale vipTradeHkdSale = vipTradeHkdSaleMapper.selectVipTradeHkdSaleById(Integer.parseInt(id));

            //交易成功
            vipTradeHkdSale.setSaleStatus(TradeStatus.SUCCESS.getCode());

            vipTradeHkdSaleMapper.updateVipTradeHkdSale(vipTradeHkdSale);

            String orderNo = vipTradeHkdSale.getSaleNo();
            VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
            vipTradeHkdBuy.setBuyNo(orderNo);
            List<VipTradeHkdBuy> vipTradeHkdBuys = vipTradeHkdBuyMapper.selectVipTradeHkdBuyList(vipTradeHkdBuy);

            vipTradeHkdBuys.get(0).setBuyStatus(TradeStatus.SUCCESS.getCode());
            //更新订单状态
            vipTradeHkdBuyMapper.updateVipTradeHkdBuy(vipTradeHkdBuys.get(0));

            String buyId = vipTradeHkdSale.getBuyId();
            VipUser buyUser = vipUserMapper.selectVipUserById(Integer.parseInt(buyId));
            String hkdMoney = buyUser.getHkdMoney();
            double add1 = NumberUtil.add(Double.parseDouble(hkdMoney), Double.parseDouble(vipTradeHkdSale.getSaleNumber()));
            double add = NumberUtil.round(add1,CustomerConstants.ROUND_NUMBER).doubleValue();
            buyUser.setHkdMoney(String.valueOf(add));
            //更新买家账户信息
            vipUserMapper.updateVipUser(buyUser);
        }catch (Exception e){
            e.printStackTrace();
            throw new VipUserException();
        }

    }


    /**
     * 取消挂售
     * @param vipUser
     * @param id
     * @return
     */
    //更新用户余额,更新订单状态
    @Override
    public int cancelSale(VipUser vipUser, String id) {
        VipUser vipUser1 = vipUserMapper.selectVipUserById(vipUser.getId());
        VipTradeHkdSale vipTradeHkdSale = vipTradeHkdSaleMapper.selectVipTradeHkdSaleById(Integer.parseInt(id));
        Trade trade = tradeMapper.selectTradeList(new Trade()).get(0);
        String hkdCharge = trade.getHkdCharge();
        //原订单金额
        double yNo = NumberUtil.div(Double.parseDouble(vipTradeHkdSale.getSaleNumber()), NumberUtil.sub(1, Double.parseDouble(hkdCharge)),CustomerConstants.ROUND_NUMBER,RoundingMode.HALF_UP);

        vipUser1.setHkdMoney(String.valueOf(NumberUtil.add(Double.parseDouble(vipUser1.getHkdMoney()),yNo)));
        //更新用户
        vipUserMapper.updateVipUser(vipUser1);
        //更新订单状态
        vipTradeHkdSale.setSaleStatus(TradeStatus.CANCEL.getCode());
        return vipTradeHkdSaleMapper.updateVipTradeHkdSale(vipTradeHkdSale);
    }

    /**
     * 根据时间统计交易数量
     * @param beginOfDay
     * @param endOfDay
     * @return
     */
    @Override
    public double selectSum(DateTime beginOfDay, DateTime endOfDay) {
        return vipTradeHkdSaleMapper.selectSum(beginOfDay,endOfDay);
    }


    /**
     * 订单过期,根据订单号更新买/卖订单状态
     * @param orderNo
     * @return
     */
    @Override
    public int updateOrderByNo(String orderNo,String type) throws Exception{

        VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
        vipTradeHkdSale.setSaleNo(orderNo);
        VipTradeHkdSale vipTradeHkdSale1 = vipTradeHkdSaleMapper.selectVipTradeHkdSaleList(vipTradeHkdSale).get(0);
        VipUser vipUser = vipUserMapper.selectVipUserById(vipTradeHkdSale1.getVipId());
        Trade trade = tradeMapper.selectTradeList(new Trade()).get(0);
        //卖家实际花费的钱,保留两位小数
        double div = NumberUtil.div(Double.parseDouble(vipTradeHkdSale1.getSaleNumber()), NumberUtil.sub(1, Double.parseDouble(trade.getHkdCharge())),CustomerConstants.ROUND_NUMBER, RoundingMode.HALF_UP);
        vipUser.setHkdMoney(String.valueOf(NumberUtil.add(Double.parseDouble(vipUser.getHkdMoney()),div)));
        vipUserMapper.updateVipUser(vipUser);   //更新用户hkd金额

        VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
        vipTradeHkdBuy.setBuyNo(orderNo);
        vipTradeHkdBuy.setBuyStatus(TradeStatus.FAIL.getCode());

        vipTradeHkdSale.setSaleStatus(TradeStatus.FAIL.getCode());
        if(type.equals(CustomerConstants.LISTEN_TRADE_BUY_PREFIX_KEY)){
            //买家的问题
            vipTradeHkdBuy.setFailReason(CustomerConstants.TRADE_FAIL_BUY);
            vipTradeHkdSale.setFailReason(CustomerConstants.TRADE_FAIL_BUY);
            vipTradeHkdBuy.setTradeFailStatus(TradeStatus.WAIT_BUY_SEND.getCode());
            vipTradeHkdSale.setTradeFailStatus(TradeStatus.WAIT_BUY_SEND.getCode());
        }else if(type.equals(CustomerConstants.LISTEN_TRADE_SALE_PREFIX_KEY)){
            //卖家的问题
            vipTradeHkdBuy.setFailReason(CustomerConstants.TRADE_FAIL_SALE);
            vipTradeHkdSale.setFailReason(CustomerConstants.TRADE_FAIL_SALE);
            vipTradeHkdBuy.setTradeFailStatus(TradeStatus.WAIT_SALE_CONFIRM.getCode());
            vipTradeHkdSale.setTradeFailStatus(TradeStatus.WAIT_SALE_CONFIRM.getCode());
        }

        vipTradeHkdBuyMapper.updateVipTradeHkdBuy(vipTradeHkdBuy);  //更新买订单
        return vipTradeHkdSaleMapper.updateVipTradeHkdSale(vipTradeHkdSale);    //更新卖订单
    }
}
