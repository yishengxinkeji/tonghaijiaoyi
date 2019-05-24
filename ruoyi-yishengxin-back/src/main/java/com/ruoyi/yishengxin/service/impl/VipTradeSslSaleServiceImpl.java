package com.ruoyi.yishengxin.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.yishengxin.domain.Customer;
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
    @Transactional
    public int saleSsl(VipUser vipUser, String number, String price) throws Exception{
        //查询其余额是否足够
        VipUser vipUser1 = vipUserMapper.selectVipUserById(vipUser.getId());
        String special = vipUser1.getSpecial();
        double sslMoney = Double.parseDouble(vipUser1.getSslMoney());       //客户当前的ssl

        double ssl = Double.parseDouble(vipUser1.getSslMoney());
        double sslCharge = 0.00;
        double maxTradeDay = 0.00; //每天最大交易量
        double maxTradeTime = 0.00; //每次最大交易量
        double maxPrice = 0.00; //峰值
        double minPrice = 0.00; //谷值

        List<Trade> trades = tradeMapper.selectTradeList(new Trade());
        if (trades.size() > 0) {
            sslCharge = Double.parseDouble(trades.get(0).getSslCharge());
            maxTradeDay = Double.parseDouble(trades.get(0).getMaxSslTradeDay()) ;
            maxTradeTime = NumberUtil.mul(sslMoney,Double.parseDouble(trades.get(0).getMaxSslTradeTime())/100);
            maxPrice = Double.parseDouble(trades.get(0).getHigh());
            minPrice = Double.parseDouble(trades.get(0).getLow());
        }


        //查询该用户今日总共交易了多少ssl
        double maxNumber = vipTradeSslSaleMapper.selectSslMaxNumberByDay(vipUser.getId());
        if(special.equals(CustomerConstants.NO)){
            if((maxNumber+Double.parseDouble(number)) > maxTradeDay) {
                //交易已达上限
                return 200;
            }
            if(Double.parseDouble(number) > maxTradeTime ){
                //单次交易已超上限
                return 300;
            }
            if(Double.parseDouble(price) < minPrice){
                //单价太低
                return 400;
            }

            if(Double.parseDouble(price) > maxPrice){
                //单价太高
                return 500;
            }
        }

        if (Double.parseDouble(number) > ssl) {
            //ssl余额不足
            return 100;
        }

        //扣的手续费
        double mul2 = NumberUtil.mul(Double.parseDouble(number), sslCharge);
        //内扣手续费后,实际应得的
        double mul1 = NumberUtil.sub(Double.parseDouble(number), mul2);
        double mulCharge = NumberUtil.round(mul1,CustomerConstants.ROUND_NUMBER).doubleValue();

        VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
        vipTradeSslSale.setVipId(vipUser.getId());
        vipTradeSslSale.setSaleStatus(TradeStatus.TRADING.getCode());
        vipTradeSslSale.setSaleNo(IdUtil.simpleUUID());
        vipTradeSslSale.setSaleNumber(NumberUtil.roundStr(mulCharge,CustomerConstants.ROUND_NUMBER));   //实际订单金额是扣除了手续费之后的
        vipTradeSslSale.setUnitPrice(NumberUtil.roundStr(price,CustomerConstants.ROUND_NUMBER));
        vipTradeSslSale.setSaleTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));
        double mul = NumberUtil.mul(mulCharge, Double.parseDouble(price));
        vipTradeSslSale.setSaleTotal(NumberUtil.roundStr(mul,CustomerConstants.ROUND_NUMBER));
        vipTradeSslSale.setSaleType(TradeType.SALE_SSL.getCode());
        vipTradeSslSale.setChargeMoney(NumberUtil.roundStr(mul2,CustomerConstants.ROUND_NUMBER));

        vipTradeSslSaleMapper.insertVipTradeSale(vipTradeSslSale);
        vipUser.setSslMoney(NumberUtil.roundStr(NumberUtil.sub(ssl, Double.parseDouble(number)), CustomerConstants.ROUND_NUMBER));
        return vipUserMapper.updateVipUser(vipUser);
    }

    /**
     * 取消挂售
     * @param vipUser
     * @param id
     * @return
     */
    //更新用户余额,更新订单状态
    @Override
    @Transactional
    public int cancelSale(VipUser vipUser, String id) {
        VipUser vipUser1 = vipUserMapper.selectVipUserById(vipUser.getId());
        VipTradeSslSale vipTradeSslSale = vipTradeSslSaleMapper.selectVipTradeSaleById(Integer.parseInt(id));
        //原订单金额
        double div = NumberUtil.add(Double.parseDouble(vipTradeSslSale.getSaleNumber()), Double.parseDouble(vipTradeSslSale.getChargeMoney()));
        double yNo = NumberUtil.round(div,CustomerConstants.ROUND_NUMBER).doubleValue();

        vipUser1.setSslMoney(NumberUtil.roundStr(NumberUtil.add(Double.parseDouble(vipUser1.getSslMoney()),yNo),CustomerConstants.ROUND_NUMBER));
        //更新用户
        vipUserMapper.updateVipUser(vipUser1);
        //更新订单状态,以及手续费
        vipTradeSslSale.setSaleStatus(TradeStatus.CANCEL.getCode());
        vipTradeSslSale.setChargeMoney("0");
        return vipTradeSslSaleMapper.updateVipTradeSale(vipTradeSslSale);
    }

    @Override
    public List<Map<String,String>> selectSale(DateTime beginOfDay, DateTime endOfDay) {
        return vipTradeSslSaleMapper.selectSale(beginOfDay,endOfDay);
    }

    /**
     * 根据时间统计交易数量
     * @param beginOfDay
     * @param endOfDay
     * @return
     */
    @Override
    public double selectSum(DateTime beginOfDay, DateTime endOfDay) {
        return vipTradeSslSaleMapper.selectSum(beginOfDay,endOfDay);
    }

    @Override
    public List<Map<String,String>> selectTwoLeast() {
        return vipTradeSslSaleMapper.selectTwoLeast();
    }

    @Override
    public Map<String,String> selectByMaxId() {
        return vipTradeSslSaleMapper.selecByMaxId();
    }

    //交易中的数据按照单价分组统计数量
    @Override
    public List<Map<String,String>> selectSumNumberByUnitPrice() {
        return vipTradeSslSaleMapper.selectSumNumberByUnitPrice();
    }

    @Override
    public Map selectSumSaleNumber() {
        return vipTradeSslSaleMapper.selectSumSaleNumber();
    }
}
