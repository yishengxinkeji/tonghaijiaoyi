package com.ruoyi.yishengxin.service.impl;

import java.math.RoundingMode;
import java.util.List;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ProfitType;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.yishengxin.domain.Customer;
import com.ruoyi.yishengxin.domain.Distribution;
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.domain.vipUser.VipProfitDetail;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslBuy;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslSale;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.mapper.DistributionMapper;
import com.ruoyi.yishengxin.mapper.TradeMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipProfitDetailMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeSslSaleMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeSslBuyMapper;
import com.ruoyi.yishengxin.service.IVipTradeSslBuyService;
import com.ruoyi.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 挂买SSL 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-06
 */
@Service
@Transactional
public class VipTradeSslBuyServiceImpl implements IVipTradeSslBuyService {
    @Autowired
    private VipTradeSslBuyMapper vipTradeSslBuyMapper;
    @Autowired
    private VipUserMapper vipUserMapper;

    @Autowired
    private DistributionMapper distributionMapper;

    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private VipProfitDetailMapper vipProfitDetailMapper;

    @Autowired
    private VipTradeSslSaleMapper vipTradeSslSaleMapper;

    /**
     * 查询挂买SSL信息
     *
     * @param id 挂买SSLID
     * @return 挂买SSL信息
     */
    @Override
    public VipTradeSslBuy selectVipTradeBuyById(Integer id) {
        return vipTradeSslBuyMapper.selectVipTradeBuyById(id);
    }

    /**
     * 查询挂买SSL列表
     *
     * @param vipTradeSslBuy 挂买SSL信息
     * @return 挂买SSL集合
     */
    @Override
    public List<VipTradeSslBuy> selectVipTradeBuyList(VipTradeSslBuy vipTradeSslBuy) {
        return vipTradeSslBuyMapper.selectVipTradeBuyList(vipTradeSslBuy);
    }

    /**
     * 新增挂买SSL
     *
     * @param vipTradeSslBuy 挂买SSL信息
     * @return 结果
     */
    @Override
    public int insertVipTradeBuy(VipTradeSslBuy vipTradeSslBuy) {
        return vipTradeSslBuyMapper.insertVipTradeBuy(vipTradeSslBuy);
    }

    /**
     * 修改挂买SSL
     *
     * @param vipTradeSslBuy 挂买SSL信息
     * @return 结果
     */
    @Override
    public int updateVipTradeBuy(VipTradeSslBuy vipTradeSslBuy) {
        return vipTradeSslBuyMapper.updateVipTradeBuy(vipTradeSslBuy);
    }

    /**
     * 删除挂买SSL对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVipTradeBuyByIds(String ids) {
        return vipTradeSslBuyMapper.deleteVipTradeBuyByIds(Convert.toStrArray(ids));
    }

    /**
     * 挂买SSL
     *
     * @param vipUser 用户
     * @param number  数量
     * @param price   单价
     */
    //保存挂买订单
    //更新用户的hdk
    @Override
    public int buySsl(VipUser vipUser, String number, String price) throws Exception {

        VipUser vipUser1 = vipUserMapper.selectVipUserById(vipUser.getId());
        double hkdMoney = Double.parseDouble(vipUser1.getHkdMoney());

        double mul = NumberUtil.mul(Double.parseDouble(number), Double.parseDouble(price));
        if (mul > hkdMoney) {
            //HDK余额不足
            return 0;
        }

        VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();
        vipTradeSslBuy.setVipId(vipUser.getId());
        //交易中
        vipTradeSslBuy.setBuyStatus(TradeStatus.TRADING.getCode());
        vipTradeSslBuy.setBuyNo(IdUtil.simpleUUID());
        vipTradeSslBuy.setBuyNumber(number);
        vipTradeSslBuy.setUnitPrice(price);
        vipTradeSslBuy.setBuyTotal(String.valueOf(mul));
        vipTradeSslBuy.setBuyTime(DateUtils.dateTimeNow("yyyy-MM-dd HH:mm"));
        vipTradeSslBuy.setBuyType(TradeType.BUY_SSL.getCode());

        vipTradeSslBuyMapper.insertVipTradeBuy(vipTradeSslBuy);
        vipUser.setHkdMoney(String.valueOf(NumberUtil.sub(hkdMoney, mul)));
        return vipUserMapper.updateVipUser(vipUser);
    }

    @Override
    public void dealTimer(VipTradeSslBuy vipTradeSslBuy1, VipTradeSslSale vipTradeSslSale1) {

        vipTradeSslBuy1 = vipTradeSslBuyMapper.selectVipTradeBuyById(vipTradeSslBuy1.getId());
        String saleNumber = vipTradeSslSale1.getSaleNumber();
        String buyNumber = vipTradeSslBuy1.getBuyNumber();
        if(Double.parseDouble(saleNumber) > Double.parseDouble(buyNumber)){
            //卖的数量大于买的数量
            dealLgOrder(vipTradeSslBuy1,vipTradeSslSale1);
        }else if(Double.parseDouble(saleNumber) == Double.parseDouble(buyNumber)){
            //两个数量相同
            dealEqOrder(vipTradeSslBuy1,vipTradeSslSale1);
        }else if(Double.parseDouble(saleNumber) < Double.parseDouble(buyNumber)){
            //卖的数量小于买的数量
            dealltOrder(vipTradeSslBuy1,vipTradeSslSale1);
        }else if(vipTradeSslBuy1.getBuyStatus().equals(TradeStatus.SUCCESS.getCode())){
            System.out.println("买订单已经处理完成了!");
        }
    }

    /**
     * 定时任务处理订单(卖家数量超过买家数量)
     *
     * @param vipTradeSslBuy1  买订单
     * @param vipTradeSslSale1 卖订单
     */
    //更新卖的该订单数量,买和卖分别创建一条交易成功订单,更新用户
    private void dealLgOrder(VipTradeSslBuy vipTradeSslBuy1, VipTradeSslSale vipTradeSslSale1) {
        //买和卖的用户信息
        VipUser saleUser = vipUserMapper.selectVipUserById(vipTradeSslSale1.getVipId());
        VipUser buyUser = vipUserMapper.selectVipUserById(vipTradeSslBuy1.getVipId());
        //上级和上上级
        VipUser parentUser = null;
        VipUser pparentUser = null;

        //分销对象
        Distribution distribution = new Distribution();
        List<Distribution> distributions = distributionMapper.selectDistributionList(new Distribution());
        if (distributions.size() > 0) {
            distribution = distributions.get(0);
        }

        Trade trade = new Trade();
        List<Trade> trades = tradeMapper.selectTradeList(new Trade());
        if (trades.size() > 0) {
            trade = trades.get(0);
        }

        //找到卖家的上级和上上级
        VipUser paUser = new VipUser();
        if (!saleUser.getParentCode().equals("-1")) {
            paUser.setRecommendCode(saleUser.getParentCode());
            List<VipUser> vipUsers = vipUserMapper.selectVipUserList(paUser);
            if (vipUsers.size() > 0) {
                parentUser = vipUsers.get(0);
                if (!parentUser.getParentCode().equals("-1")) {
                    paUser.setRecommendCode(parentUser.getParentCode());
                    List<VipUser> vipUsers1 = vipUserMapper.selectVipUserList(paUser);
                    pparentUser = vipUsers1.get(0);
                }
            }
        }

        //卖家的hkd,买家的ssl
        String hkdMoney = saleUser.getHkdMoney();
        String sslMoney = buyUser.getSslMoney();

        //卖的订单大于买的订单
        //修改卖的订单的数量,更新买的订单状态,创建卖的新订单,
        //更新用户的余额信息
        //手续费奖励上级和上上级
        //更新收益明细
        //卖的数量
        String saleNumber = vipTradeSslSale1.getSaleNumber();

        //卖家扣除的手续费比例
        String sslCharge = trade.getSslCharge();
        //卖家本来卖的量
        double div = NumberUtil.div(Double.parseDouble(saleNumber), NumberUtil.sub(1, Double.parseDouble(sslCharge)),2,RoundingMode.HALF_UP);
        //卖家实际扣除的手续费
        double chanrgeNumber = NumberUtil.mul(div, Double.parseDouble(sslCharge));

        //买的数量
        String buyNumber = vipTradeSslBuy1.getBuyNumber();

        String simpleUuid = IdUtil.simpleUUID();
        //创建新的卖成功订单信息
        VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
        vipTradeSslSale.setVipId(saleUser.getId());
        vipTradeSslSale.setSaleStatus(TradeStatus.SUCCESS.getCode());
        vipTradeSslSale.setSaleNo(simpleUuid);
        vipTradeSslSale.setSaleNumber(buyNumber);
        vipTradeSslSale.setUnitPrice(vipTradeSslSale1.getUnitPrice());
        vipTradeSslSale.setSaleTotal(String.valueOf(NumberUtil.mul(Double.parseDouble(buyNumber),Double.parseDouble(vipTradeSslSale1.getUnitPrice()))));
        vipTradeSslSale.setSaleTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));
        vipTradeSslSale.setBuyId(String.valueOf(buyUser.getId()));
        vipTradeSslSale.setBuyPhone(buyUser.getPhone());
        vipTradeSslSale.setBuyNickname(buyUser.getNickname());
        vipTradeSslSale.setBuyAvater(buyUser.getAvater());
        vipTradeSslSale.setSaleType(TradeType.SALE_SSL.getCode());
        vipTradeSslSale.setRelationOrderno(vipTradeSslBuy1.getBuyNo());

        vipTradeSslSaleMapper.insertVipTradeSale(vipTradeSslSale);

        //更新买的订单状态
        vipTradeSslBuy1.setBuyStatus(TradeStatus.SUCCESS.getCode());
        vipTradeSslBuy1.setSaleId(String.valueOf(vipTradeSslSale1.getVipId()));
        vipTradeSslBuy1.setSalePhone(saleUser.getPhone());
        vipTradeSslBuy1.setSaleNickname(saleUser.getNickname());
        vipTradeSslBuy1.setSaleAvater(saleUser.getAvater());
        vipTradeSslBuy1.setRelationOrderno(simpleUuid);

        updateVipTradeBuy(vipTradeSslBuy1);

        //更新卖的订单状态
        String update_saleNumber = String.valueOf(NumberUtil.sub(Double.parseDouble(saleNumber), Double.parseDouble(buyNumber)));
        vipTradeSslSale1.setSaleNumber(update_saleNumber);
        vipTradeSslSale1.setSaleTotal(String.valueOf(NumberUtil.mul(Double.parseDouble(update_saleNumber),Double.parseDouble(vipTradeSslSale1.getUnitPrice()))));
        vipTradeSslSaleMapper.updateVipTradeSale(vipTradeSslSale1);

        //更新两位用户的账户信息
        //卖家更新hkd余额 += number*unitprice
        //买家更新SSL余额 += number
        //卖的单价 * 买的数量 = 总价
        double total = NumberUtil.mul(Double.parseDouble(vipTradeSslSale1.getUnitPrice()), Double.parseDouble(buyNumber));
        //卖家更新hkd+卖出的总价
        saleUser.setHkdMoney(String.valueOf(NumberUtil.add(Double.parseDouble(hkdMoney), total)));
        //买家更新ssl += 买的数量
        buyUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(sslMoney), Double.parseDouble(buyNumber))));

        vipUserMapper.updateVipUser(saleUser);
        vipUserMapper.updateVipUser(buyUser);

        if (parentUser != null) {
            //父级账户不为空,更新其账户余额,并添加收益表
            if (distribution != null) {
                //手续费,上级和上上级的抽成比例
                String parentCharge = distribution.getParentCharge();
                String grandparentCharge = distribution.getGrandparentCharge();

                //上级收益
                double parent_mul = NumberUtil.mul(chanrgeNumber, Double.parseDouble(parentCharge));
                //上上级收益
                double grand_mul = NumberUtil.mul(chanrgeNumber, Double.parseDouble(grandparentCharge));

                //更新上级和上上级余额
                parentUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(parentUser.getSslMoney()), parent_mul)));
                vipUserMapper.updateVipUser(parentUser);
                //添加收益表,1级和二级
                VipProfitDetail vipProfitDetail = new VipProfitDetail();
                vipProfitDetail.setProfitDate(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD));
                vipProfitDetail.setProfitDescription(String.format(CustomerConstants.PROFIT_TEMPLATE, parent_mul, CustomerConstants.TRADE_TYPE_SSL));
                vipProfitDetail.setProfitType(ProfitType.ONE.getCode());
                vipProfitDetail.setVipAvater(saleUser.getAvater());
                vipProfitDetail.setVipName(saleUser.getNickname());
                vipProfitDetail.setVipId(parentUser.getId());
                vipProfitDetail.setChildrenVipId(saleUser.getId());
                vipProfitDetailMapper.insertVipProfitDetail(vipProfitDetail);

                if (pparentUser != null) {
                    pparentUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(pparentUser.getSslMoney()), grand_mul)));
                    vipUserMapper.updateVipUser(pparentUser);

                    VipProfitDetail vipProfitDetai2 = new VipProfitDetail();
                    vipProfitDetai2.setProfitDate(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD));
                    vipProfitDetai2.setProfitDescription(String.format(CustomerConstants.PROFIT_TEMPLATE, grand_mul, CustomerConstants.TRADE_TYPE_SSL));
                    vipProfitDetai2.setProfitType(ProfitType.TWO.getCode());
                    vipProfitDetai2.setVipAvater(saleUser.getAvater());
                    vipProfitDetai2.setVipName(saleUser.getNickname());
                    vipProfitDetai2.setVipId(pparentUser.getId());
                    vipProfitDetai2.setChildrenVipId(saleUser.getId());
                    vipProfitDetailMapper.updateVipProfitDetail(vipProfitDetai2);
                }
            }
        }
    }

    /**
     * 卖等于买
     *
     * @param vipTradeSslBuy1
     * @param vipTradeSslSale1
     */
    //卖的订单等于买的订单
    //更新买卖订单状态
    //更新用户的余额信息
    //手续费奖励上级和上上级
    //更新收益明细
    private void dealEqOrder(VipTradeSslBuy vipTradeSslBuy1, VipTradeSslSale vipTradeSslSale1) {
        //买和卖的用户信息
        VipUser saleUser = vipUserMapper.selectVipUserById(vipTradeSslSale1.getVipId());
        VipUser buyUser = vipUserMapper.selectVipUserById(vipTradeSslBuy1.getVipId());
        //上级和上上级
        VipUser parentUser = null;
        VipUser pparentUser = null;

        //分销对象
        Distribution distribution = new Distribution();
        List<Distribution> distributions = distributionMapper.selectDistributionList(new Distribution());
        if (distributions.size() > 0) {
            distribution = distributions.get(0);
        }

        Trade trade = new Trade();
        List<Trade> trades = tradeMapper.selectTradeList(new Trade());
        if (trades.size() > 0) {
            trade = trades.get(0);
        }

        //找到卖家的上级和上上级
        VipUser paUser = new VipUser();
        if (!saleUser.getParentCode().equals("-1")) {
            paUser.setRecommendCode(saleUser.getParentCode());
            List<VipUser> vipUsers = vipUserMapper.selectVipUserList(paUser);
            if (vipUsers.size() > 0) {
                parentUser = vipUsers.get(0);
                if (!parentUser.getParentCode().equals("-1")) {
                    paUser.setRecommendCode(parentUser.getParentCode());
                    List<VipUser> vipUsers1 = vipUserMapper.selectVipUserList(paUser);
                    pparentUser = vipUsers1.get(0);
                }
            }
        }

        //卖家的hkd,买家的ssl
        String hkdMoney = saleUser.getHkdMoney();
        String sslMoney = buyUser.getSslMoney();



        //卖的订单等于买的订单
        //更新买卖订单状态
        //更新用户的余额信息
        //手续费奖励上级和上上级
        //更新收益明细
        //卖的数量
        String saleNumber = vipTradeSslSale1.getSaleNumber();
        //卖家扣除的手续费比例
        String sslCharge = trade.getSslCharge();
        //卖家本来卖的量
        double div = NumberUtil.div(Double.parseDouble(saleNumber), NumberUtil.sub(1, Double.parseDouble(sslCharge)),2,RoundingMode.HALF_UP);
        //卖家实际扣除的手续费
        double chanrgeNumber = NumberUtil.mul(div, Double.parseDouble(sslCharge));
        //买的数量
        String buyNumber = vipTradeSslBuy1.getBuyNumber();

        //更新买的订单状态
        vipTradeSslBuy1.setBuyStatus(TradeStatus.SUCCESS.getCode());
        vipTradeSslBuy1.setSaleId(String.valueOf(vipTradeSslSale1.getVipId()));
        vipTradeSslBuy1.setSalePhone(saleUser.getPhone());
        vipTradeSslBuy1.setSaleNickname(saleUser.getNickname());
        vipTradeSslBuy1.setSaleAvater(saleUser.getAvater());
        vipTradeSslBuy1.setRelationOrderno(vipTradeSslSale1.getSaleNo());

        updateVipTradeBuy(vipTradeSslBuy1);

        //更新卖的订单状态
        vipTradeSslSale1.setSaleStatus(TradeStatus.SUCCESS.getCode());
        vipTradeSslSale1.setBuyId(String.valueOf(vipTradeSslBuy1.getVipId()));
        vipTradeSslSale1.setBuyAvater(buyUser.getAvater());
        vipTradeSslSale1.setBuyNickname(buyUser.getNickname());
        vipTradeSslSale1.setBuyPhone(buyUser.getPhone());
        vipTradeSslSale1.setRelationOrderno(vipTradeSslBuy1.getBuyNo());

        vipTradeSslSaleMapper.updateVipTradeSale(vipTradeSslSale1);

        //更新两位用户的账户信息
        //卖家更新hkd余额 += number*unitprice
        //买家更新SSL余额 += number
        //卖的单价 * 买的数量 = 总价
        double total = NumberUtil.mul(Double.parseDouble(vipTradeSslSale1.getUnitPrice()), Double.parseDouble(buyNumber));
        //卖家更新hkd+卖出的总价
        saleUser.setHkdMoney(String.valueOf(NumberUtil.add(Double.parseDouble(hkdMoney), total)));
        //买家更新ssl += 买的数量
        buyUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(sslMoney), Double.parseDouble(buyNumber))));

        vipUserMapper.updateVipUser(saleUser);
        vipUserMapper.updateVipUser(buyUser);

        if (parentUser != null) {
            //父级账户不为空,更新其账户余额,并添加收益表
            if (distribution != null) {
                //手续费,上级和上上级的抽成比例
                String parentCharge = distribution.getParentCharge();
                String grandparentCharge = distribution.getGrandparentCharge();

                //上级收益
                double parent_mul = NumberUtil.mul(chanrgeNumber, Double.parseDouble(parentCharge));
                //上上级收益
                double grand_mul = NumberUtil.mul(chanrgeNumber, Double.parseDouble(grandparentCharge));

                //更新上级和上上级余额
                parentUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(parentUser.getSslMoney()), parent_mul)));
                vipUserMapper.updateVipUser(parentUser);
                //添加收益表,1级和二级
                VipProfitDetail vipProfitDetail = new VipProfitDetail();
                vipProfitDetail.setProfitDate(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD));
                vipProfitDetail.setProfitDescription(String.format(CustomerConstants.PROFIT_TEMPLATE, parent_mul, CustomerConstants.TRADE_TYPE_SSL));
                vipProfitDetail.setProfitType(ProfitType.ONE.getCode());
                vipProfitDetail.setVipAvater(saleUser.getAvater());
                vipProfitDetail.setVipName(saleUser.getNickname());
                vipProfitDetail.setVipId(parentUser.getId());
                vipProfitDetail.setChildrenVipId(saleUser.getId());
                vipProfitDetailMapper.insertVipProfitDetail(vipProfitDetail);

                if (pparentUser != null) {
                    pparentUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(pparentUser.getSslMoney()), grand_mul)));
                    vipUserMapper.updateVipUser(pparentUser);

                    VipProfitDetail vipProfitDetai2 = new VipProfitDetail();
                    vipProfitDetai2.setProfitDate(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD));
                    vipProfitDetai2.setProfitDescription(String.format(CustomerConstants.PROFIT_TEMPLATE, grand_mul, CustomerConstants.TRADE_TYPE_SSL));
                    vipProfitDetai2.setProfitType(ProfitType.TWO.getCode());
                    vipProfitDetai2.setVipAvater(saleUser.getAvater());
                    vipProfitDetai2.setVipName(saleUser.getNickname());
                    vipProfitDetai2.setVipId(pparentUser.getId());
                    vipProfitDetai2.setChildrenVipId(saleUser.getId());
                    vipProfitDetailMapper.updateVipProfitDetail(vipProfitDetai2);
                }
            }
        }
    }


    /**
     * 卖小于买
     *
     * @param vipTradeSslBuy1
     * @param vipTradeSslSale1
     */
    //卖的订单小于买的订单
    //创建买订单,修改买卖订单
    //更新用户的余额信息
    //手续费奖励上级和上上级
    //更新收益明细
    private void dealltOrder(VipTradeSslBuy vipTradeSslBuy1, VipTradeSslSale vipTradeSslSale1) {
        //买和卖的用户信息
        VipUser saleUser = vipUserMapper.selectVipUserById(vipTradeSslSale1.getVipId());
        VipUser buyUser = vipUserMapper.selectVipUserById(vipTradeSslBuy1.getVipId());
        //上级和上上级
        VipUser parentUser = null;
        VipUser pparentUser = null;

        //分销对象
        Distribution distribution = new Distribution();
        List<Distribution> distributions = distributionMapper.selectDistributionList(new Distribution());
        if (distributions.size() > 0) {
            distribution = distributions.get(0);
        }

        Trade trade = new Trade();
        List<Trade> trades = tradeMapper.selectTradeList(new Trade());
        if (trades.size() > 0) {
            trade = trades.get(0);
        }

        //找到卖家的上级和上上级
        VipUser paUser = new VipUser();
        if (!saleUser.getParentCode().equals("-1")) {
            paUser.setRecommendCode(saleUser.getParentCode());
            List<VipUser> vipUsers = vipUserMapper.selectVipUserList(paUser);
            if (vipUsers.size() > 0) {
                parentUser = vipUsers.get(0);
                if (!parentUser.getParentCode().equals("-1")) {
                    paUser.setRecommendCode(parentUser.getParentCode());
                    List<VipUser> vipUsers1 = vipUserMapper.selectVipUserList(paUser);
                    pparentUser = vipUsers1.get(0);
                }
            }
        }

        //卖家的hkd,买家的ssl
        String hkdMoney = saleUser.getHkdMoney();
        String sslMoney = buyUser.getSslMoney();

        //卖的订单小于买的订单
        //创建买订单,修改买卖订单
        //更新用户的余额信息
        //手续费奖励上级和上上级
        //更新收益明细
        //卖的数量
        String saleNumber = vipTradeSslSale1.getSaleNumber();
        //卖家扣除的手续费比例
        String sslCharge = trade.getSslCharge();
        //卖家本来卖的量,四舍五入,保留2位
        double div = NumberUtil.div(Double.parseDouble(saleNumber), NumberUtil.sub(1, Double.parseDouble(sslCharge)),2, RoundingMode.HALF_UP);
        //卖家实际扣除的手续费
        double chanrgeNumber = NumberUtil.mul(div, Double.parseDouble(sslCharge));
        //买的数量
        String buyNumber = vipTradeSslBuy1.getBuyNumber();

        //剩余需要买的数量
        double buy_num = NumberUtil.sub(Double.parseDouble(buyNumber), Double.parseDouble(saleNumber));

        String simpleUuid = IdUtil.simpleUUID();
        //创建新的买订单信息
        VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();
        vipTradeSslBuy.setVipId(vipTradeSslBuy1.getVipId());
        vipTradeSslBuy.setBuyStatus(TradeStatus.SUCCESS.getCode());
        vipTradeSslBuy.setBuyNo(simpleUuid);
        vipTradeSslBuy.setUnitPrice(vipTradeSslBuy1.getUnitPrice());
        vipTradeSslBuy.setBuyNumber(saleNumber);
        vipTradeSslBuy.setBuyTotal(String.valueOf(NumberUtil.mul(Double.parseDouble(saleNumber), Double.parseDouble(vipTradeSslBuy1.getUnitPrice()))));
        vipTradeSslBuy.setBuyTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));
        vipTradeSslBuy.setSaleId(String.valueOf(vipTradeSslSale1.getVipId()));
        vipTradeSslBuy.setSalePhone(saleUser.getPhone());
        vipTradeSslBuy.setSaleNickname(saleUser.getNickname());
        vipTradeSslBuy.setSaleAvater(saleUser.getAvater());
        vipTradeSslBuy.setBuyType(TradeType.BUY_SSL.getCode());
        vipTradeSslBuy.setRelationOrderno(vipTradeSslSale1.getSaleNo());

        vipTradeSslBuyMapper.insertVipTradeBuy(vipTradeSslBuy);

        //更新买的订单状态
        vipTradeSslBuy1.setBuyNumber(String.valueOf(buy_num));
        vipTradeSslBuy1.setBuyTotal(String.valueOf(NumberUtil.mul(buy_num,Double.parseDouble(vipTradeSslBuy1.getUnitPrice()))));
        vipTradeSslBuyMapper.updateVipTradeBuy(vipTradeSslBuy1);

        //更新卖的订单状态
        vipTradeSslSale1.setSaleStatus(TradeStatus.SUCCESS.getCode());
        vipTradeSslSale1.setBuyId(String.valueOf(buyUser.getId()));
        vipTradeSslSale1.setBuyPhone(buyUser.getPhone());
        vipTradeSslSale1.setBuyNickname(buyUser.getNickname());
        vipTradeSslSale1.setBuyAvater(buyUser.getAvater());
        vipTradeSslSale1.setRelationOrderno(simpleUuid);

        vipTradeSslSaleMapper.updateVipTradeSale(vipTradeSslSale1);

        //更新两位用户的账户信息
        //卖家更新hkd余额 += number*unitprice
        //买家更新SSL余额 += number
        //卖的单价 * 卖的数量 = 总价
        double total = NumberUtil.mul(Double.parseDouble(vipTradeSslSale1.getUnitPrice()), Double.parseDouble(saleNumber));
        //卖家更新hkd+卖出的总价
        saleUser.setHkdMoney(String.valueOf(NumberUtil.add(Double.parseDouble(hkdMoney), total)));
        vipUserMapper.updateVipUser(saleUser);
        //买家更新ssl += 买的数量
        buyUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(sslMoney), Double.parseDouble(saleNumber))));
        vipUserMapper.updateVipUser(buyUser);

        if (parentUser != null) {
            //父级账户不为空,更新其账户余额,并添加收益表
            if (distribution != null) {
                //手续费,上级和上上级的抽成比例
                String parentCharge = distribution.getParentCharge();
                String grandparentCharge = distribution.getGrandparentCharge();

                //上级收益
                double parent_mul = NumberUtil.mul(chanrgeNumber, Double.parseDouble(parentCharge));
                //上上级收益
                double grand_mul = NumberUtil.mul(chanrgeNumber, Double.parseDouble(grandparentCharge));

                //更新上级和上上级余额
                parentUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(parentUser.getSslMoney()), parent_mul)));
                vipUserMapper.updateVipUser(parentUser);
                //添加收益表,1级和二级
                VipProfitDetail vipProfitDetail = new VipProfitDetail();
                vipProfitDetail.setProfitDate(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD));
                vipProfitDetail.setProfitDescription(String.format(CustomerConstants.PROFIT_TEMPLATE, parent_mul, CustomerConstants.TRADE_TYPE_SSL));
                vipProfitDetail.setProfitType(ProfitType.ONE.getCode());
                vipProfitDetail.setVipAvater(saleUser.getAvater());
                vipProfitDetail.setVipName(saleUser.getNickname());
                vipProfitDetail.setVipId(parentUser.getId());
                vipProfitDetail.setChildrenVipId(saleUser.getId());
                vipProfitDetailMapper.insertVipProfitDetail(vipProfitDetail);

                if (pparentUser != null) {
                    pparentUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(pparentUser.getSslMoney()), grand_mul)));
                    vipUserMapper.updateVipUser(pparentUser);

                    VipProfitDetail vipProfitDetai2 = new VipProfitDetail();
                    vipProfitDetai2.setProfitDate(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD));
                    vipProfitDetai2.setProfitDescription(String.format(CustomerConstants.PROFIT_TEMPLATE, grand_mul, CustomerConstants.TRADE_TYPE_SSL));
                    vipProfitDetai2.setProfitType(ProfitType.TWO.getCode());
                    vipProfitDetai2.setVipAvater(saleUser.getAvater());
                    vipProfitDetai2.setVipName(saleUser.getNickname());
                    vipProfitDetai2.setVipId(pparentUser.getId());
                    vipProfitDetai2.setChildrenVipId(saleUser.getId());
                    vipProfitDetailMapper.updateVipProfitDetail(vipProfitDetai2);
                }
            }
        }
    }

    @Override
    public int cancelBuy(VipUser vipUser, String id) {
        VipUser vipUser1 = vipUserMapper.selectVipUserById(vipUser.getId());
        VipTradeSslBuy vipTradeSslBuy = vipTradeSslBuyMapper.selectVipTradeBuyById(Integer.parseInt(id));

        //原订单金额
        double yNo = NumberUtil.mul(Double.parseDouble(vipTradeSslBuy.getBuyNumber()), Double.parseDouble(vipTradeSslBuy.getUnitPrice()));

        vipUser1.setHkdMoney(String.valueOf(NumberUtil.add(Double.parseDouble(vipUser1.getHkdMoney()),yNo)));
        //更新用户
        vipUserMapper.updateVipUser(vipUser1);
        //更新订单状态
        vipTradeSslBuy.setBuyStatus(TradeStatus.CANCEL.getCode());
        return vipTradeSslBuyMapper.updateVipTradeBuy(vipTradeSslBuy);
    }

    /**
     * 根据时间统计交易数量
     * @param beginOfDay
     * @param endOfDay
     * @return
     */
    @Override
    public int selectSum(DateTime beginOfDay, DateTime endOfDay) {
        return vipTradeSslBuyMapper.selectSum(beginOfDay,endOfDay);
    }

    @Override
    public double selectAvgByDay(DateTime beginOfDay, DateTime endOfDay) {
        return vipTradeSslBuyMapper.selectAvgByDay(beginOfDay,endOfDay);
    }
}
