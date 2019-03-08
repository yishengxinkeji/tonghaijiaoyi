package com.ruoyi.yishengxin.service.impl;

import java.math.BigDecimal;
import java.util.List;

import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ProfitType;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.exception.frontException.VipUserException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.yishengxin.domain.Distribution;
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.domain.vipUser.VipProfitDetail;
import com.ruoyi.yishengxin.domain.vipUser.VipTrade;
import com.ruoyi.yishengxin.mapper.DistributionMapper;
import com.ruoyi.yishengxin.mapper.TradeMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipProfitDetailMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipUserMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IVipUserService;
import com.ruoyi.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员基本 服务层实现
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Service
@Transactional
public class VipUserServiceImpl implements IVipUserService {
    @Autowired
    private VipUserMapper vipUserMapper;

    @Autowired
    private VipProfitDetailMapper vipProfitDetailMapper;

    @Autowired
    private VipTradeMapper vipTradeMapper;

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private DistributionMapper distributionMapper;

    /**
     * 查询会员基本信息
     *
     * @param id 会员基本ID
     * @return 会员基本信息
     */
    @Override
    public VipUser selectVipUserById(Integer id) {
        return vipUserMapper.selectVipUserById(id);
    }

    /**
     * 查询会员基本列表
     *
     * @param vipUser 会员基本信息
     * @return 会员基本集合
     */
    @Override
    public List<VipUser> selectVipUserList(VipUser vipUser) {
        return vipUserMapper.selectVipUserList(vipUser);
    }

    /**
     * 新增会员基本
     *
     * @param vipUser 会员基本信息
     * @return 结果
     */
    @Override
    public int insertVipUser(VipUser vipUser) {
        return vipUserMapper.insertVipUser(vipUser);
    }

    /**
     * 修改会员基本
     *
     * @param vipUser 会员基本信息
     * @return 结果
     */
    @Override
    public int updateVipUser(VipUser vipUser) {
        return vipUserMapper.updateVipUser(vipUser);
    }

    /**
     * 删除会员基本对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVipUserByIds(String ids) {
        return vipUserMapper.deleteVipUserByIds(Convert.toStrArray(ids));
    }


    /**
     * 新人领取礼包,还要给上级和上上级奖励
     * @param vipUser
     */
    @Override
    public void newReceiveGift(VipUser vipUser,String giftType,String giftNumber) {
        //上级和上上级
        VipUser parentUser = null;
        VipUser pparentUser = null;

        //分销对象
        Distribution distribution = new Distribution();
        List<Distribution> distributions = distributionMapper.selectDistributionList(new Distribution());
        if(distributions.size() > 0){
            distribution = distributions.get(0);
        }

        double parentCharge = Double.parseDouble(distribution.getParentCharge());   //上级手续费
        double grandparentCharge = Double.parseDouble(distribution.getGrandparentCharge()); //上上级手续费


        //找到卖家的上级和上上级
        VipUser paUser = new VipUser();
        if(!vipUser.getParentCode().equals("-1")){
            paUser.setRecommendCode(vipUser.getParentCode());
            List<VipUser> vipUsers = vipUserMapper.selectVipUserList(paUser);
            if(vipUsers.size() > 0){
                parentUser = vipUsers.get(0);
                if(!parentUser.getParentCode().equals("-1")){
                    paUser.setRecommendCode(parentUser.getParentCode());
                    List<VipUser> vipUsers1 = vipUserMapper.selectVipUserList(paUser);
                    pparentUser = vipUsers1.get(0);
                }
            }
        }

        try{
            //更新资产及状态,同时将信息插入收益表中
            if(giftType.equals(CustomerConstants.SSL)){
                VipProfitDetail detail = new VipProfitDetail();
                detail.setVipId(vipUser.getId());
                detail.setProfitDate(DateUtils.dateTimeNow("yyyy-MM-dd"));
                detail.setProfitDescription(String.format(CustomerConstants.PROFIT_TEMPLATE,giftNumber,giftType));
                //收益类型: 自己
                detail.setProfitType(ProfitType.SELF.getCode());

                vipUserMapper.updateVipUser(vipUser);
                vipProfitDetailMapper.insertVipProfitDetail(detail);
                if(parentUser != null){
                    VipProfitDetail detail1 = new VipProfitDetail();    //  一级
                    detail1.setVipId(parentUser.getId());
                    detail1.setProfitDate(DateUtils.dateTimeNow("yyyy-MM-dd"));
                    detail1.setProfitDescription(String.format(CustomerConstants.PROFIT_TEMPLATE,giftNumber,giftType));
                    //收益类型: 一级
                    detail1.setProfitType(ProfitType.ONE.getCode());


                    double mul = NumberUtil.mul(Double.parseDouble(giftNumber), parentCharge);
                    parentUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(parentUser.getSslMoney()),mul)));
                    vipUserMapper.updateVipUser(parentUser);
                    vipProfitDetailMapper.insertVipProfitDetail(detail1);
                }

                if(pparentUser != null){
                    VipProfitDetail detail2 = new VipProfitDetail();    //二级
                    detail2.setVipId(pparentUser.getId());
                    detail2.setProfitDate(DateUtils.dateTimeNow("yyyy-MM-dd"));
                    detail2.setProfitDescription(String.format(CustomerConstants.PROFIT_TEMPLATE,giftNumber,giftType));
                    //收益类型: 一级
                    detail2.setProfitType(ProfitType.TWO.getCode());

                    double mul = NumberUtil.mul(Double.parseDouble(giftNumber), grandparentCharge);
                    pparentUser.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(pparentUser.getSslMoney()),mul)));
                    vipUserMapper.updateVipUser(pparentUser);
                    vipProfitDetailMapper.insertVipProfitDetail(detail2);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new VipUserException();
        }
    }

    @Override
    public List<VipUser> selectUserByParentCode(VipUser oneUser) {
        return vipUserMapper.selectUserByParentCode(oneUser);
    }

    @Override
    public List<VipUser> selectUserByGrandParentCode(VipUser oneUser) {
        return vipUserMapper.selectUserByGrandParentCode(oneUser);
    }

    /**
     * 转出
     * @param vip 自己
     * @param toVip 目标对象
     * @param type  类型ssl/hkd
     * @param number    数量
     * @param tranMoney  计算后的数量
     * @return
     */
    @Override
    public int tranSport(VipUser vip, VipUser toVip, String type, String number, double tranMoney) throws Exception{

        //需要插入两条交易记录表
        VipTrade trade = new VipTrade();       //自己
        VipTrade toTrade = new VipTrade();  //目标

        //自己,转出
        trade.setVipId(vip.getId());
        trade.setTradeNumber(number);
        trade.setTradeTime(DateUtils.dateTimeNow("yyyy-MM-dd HH:mm"));
        trade.setToVipId(String.valueOf(toVip.getId()));
        trade.setToVipAvater(toVip.getAvater());
        trade.setToVipNickname(toVip.getNickname());

        //对方,转入
        toTrade.setVipId(toVip.getId());
        toTrade.setTradeNumber(number);
        toTrade.setTradeTime(DateUtils.dateTimeNow("yyyy-MM-dd HH:mm"));
        toTrade.setToVipId(String.valueOf(vip.getId()));
        toTrade.setToVipAvater(vip.getAvater());
        toTrade.setToVipNickname(vip.getNickname());


        if(type.equalsIgnoreCase("ssl")){

            //修改自己与目标对象数据
            //插入到交易记录表中
            vip.setSslMoney(String.valueOf(tranMoney));
            toVip.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(toVip.getSslMoney()), Double.parseDouble(number))));
            //自己转出,对方转入
            trade.setVipTrade(TradeType.OUT_SSL.getCode());
            toTrade.setVipTrade(TradeType.IN_SSL.getCode());

        }else if(type.equalsIgnoreCase("hkd")){
            vip.setHkdMoney(String.valueOf(tranMoney));
            toVip.setHkdMoney(String.valueOf(NumberUtil.add(Double.parseDouble(toVip.getHkdMoney()), Double.parseDouble(number))));
            //自己转出,对方转入
            trade.setVipTrade(TradeType.OUT_HKD.getCode());
            toTrade.setVipTrade(TradeType.IN_HKD.getCode());
        }


        vipUserMapper.updateVipUser(vip);
        vipUserMapper.updateVipUser(toVip);
        vipTradeMapper.insertVipTrade(trade);
        return vipTradeMapper.insertVipTrade(toTrade);
    }


}
