package com.ruoyi.yishengxin.service.impl;

import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeHkdSale;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeHkdSaleMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeHkdBuyMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeHkdBuy;
import com.ruoyi.yishengxin.service.IVipTradeHkdBuyService;
import com.ruoyi.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 购买HKD 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-07
 */
@Service
@Transactional
public class VipTradeHkdBuyServiceImpl implements IVipTradeHkdBuyService {
    @Autowired
    private VipTradeHkdBuyMapper vipTradeHkdBuyMapper;

    @Autowired
    private VipTradeHkdSaleMapper vipTradeHkdSaleMapper;

    @Autowired
    private VipUserMapper vipUserMapper;

    /**
     * 查询购买HKD信息
     *
     * @param id 购买HKDID
     * @return 购买HKD信息
     */
    @Override
    public VipTradeHkdBuy selectVipTradeHkdBuyById(Integer id) {
        return vipTradeHkdBuyMapper.selectVipTradeHkdBuyById(id);
    }

    /**
     * 查询购买HKD列表
     *
     * @param vipTradeHkdBuy 购买HKD信息
     * @return 购买HKD集合
     */
    @Override
    public List<VipTradeHkdBuy> selectVipTradeHkdBuyList(VipTradeHkdBuy vipTradeHkdBuy) {
        return vipTradeHkdBuyMapper.selectVipTradeHkdBuyList(vipTradeHkdBuy);
    }

    /**
     * 新增购买HKD
     *
     * @param vipTradeHkdBuy 购买HKD信息
     * @return 结果
     */
    @Override
    public int insertVipTradeHkdBuy(VipTradeHkdBuy vipTradeHkdBuy) {
        return vipTradeHkdBuyMapper.insertVipTradeHkdBuy(vipTradeHkdBuy);
    }

    /**
     * 修改购买HKD
     *
     * @param vipTradeHkdBuy 购买HKD信息
     * @return 结果
     */
    @Override
    public int updateVipTradeHkdBuy(VipTradeHkdBuy vipTradeHkdBuy) {
        return vipTradeHkdBuyMapper.updateVipTradeHkdBuy(vipTradeHkdBuy);
    }

    /**
     * 删除购买HKD对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVipTradeHkdBuyByIds(String ids) {
        return vipTradeHkdBuyMapper.deleteVipTradeHkdBuyByIds(Convert.toStrArray(ids));
    }

    /**
     * 购买hkd
     *
     * @param vipUser
     * @param id      记录id
     * @param number  购买数量
     * @return
     */
    @Override
    public String buyHkd(VipUser vipUser, String id, String number) throws Exception{


        VipTradeHkdSale vipTradeHkdSale = vipTradeHkdSaleMapper.selectVipTradeHkdSaleById(Integer.parseInt(id));
        //原先的订单号 或 新创建的订单号
        String mmNo = vipTradeHkdSale.getSaleNo();

        VipUser saleUser = vipUserMapper.selectVipUserById(vipTradeHkdSale.getVipId());

        //订单数量
        double saleNumber = Double.parseDouble(vipTradeHkdSale.getSaleNumber());
        double buyNumber = Double.parseDouble(number);
        if(saleNumber < buyNumber){
            //购买数量超出
            return "100";
        }

        if(vipUser.getId() == vipTradeHkdSale.getVipId()){
            return "9999";
        }


        if(saleNumber == buyNumber){
            //订单刚好完成;
            //修改卖订单,创建买订单
            vipTradeHkdSale.setSaleStatus(TradeStatus.WAIT_BUY_SEND.getCode()); //等待买家付款
            vipTradeHkdSale.setBuyAvater(vipUser.getAvater());
            vipTradeHkdSale.setBuyId(String.valueOf(vipUser.getId()));
            vipTradeHkdSale.setBuyNickname(vipUser.getNickname());
            vipTradeHkdSale.setBuyPhone(vipUser.getPhone());
            vipTradeHkdSale.setIsAppeal(CustomerConstants.NO);
            vipTradeHkdSale.setSaleTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));


            vipTradeHkdSaleMapper.updateVipTradeHkdSale(vipTradeHkdSale);

            VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
            vipTradeHkdBuy.setVipId(vipUser.getId());
            vipTradeHkdBuy.setBuyStatus(TradeStatus.WAIT_BUY_SEND.getCode()); //等待买家打款
            vipTradeHkdBuy.setBuyNo(vipTradeHkdSale.getSaleNo());   //订单编号
            vipTradeHkdBuy.setBuyNumber(number);
            vipTradeHkdBuy.setBuyTotal(number);
            vipTradeHkdBuy.setBuyTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));
            vipTradeHkdBuy.setSaleId(String.valueOf(saleUser.getId()));
            vipTradeHkdBuy.setSaleAvater(saleUser.getAvater());
            vipTradeHkdBuy.setSaleNickname(saleUser.getNickname());
            vipTradeHkdBuy.setSalePhone(saleUser.getPhone());
            vipTradeHkdBuy.setIsAppeal(CustomerConstants.NO);
            vipTradeHkdBuy.setBuyType(TradeType.BUY_HKD.getCode());
            vipTradeHkdBuy.setSaleAccount(vipTradeHkdSale.getSaleAccount());
            vipTradeHkdBuy.setSaleAccountProof(vipTradeHkdSale.getSaleAccountProof());

            vipTradeHkdBuyMapper.insertVipTradeHkdBuy(vipTradeHkdBuy);

        }else if(saleNumber > buyNumber){
            //修改卖订单,创建买卖订单
            mmNo = IdUtil.simpleUUID(); //新订单的订单号

            VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
            vipTradeHkdBuy.setVipId(vipUser.getId());
            vipTradeHkdBuy.setBuyStatus(TradeStatus.WAIT_BUY_SEND.getCode()); //等待买家打款
            vipTradeHkdBuy.setBuyNo(mmNo);   //订单编号
            vipTradeHkdBuy.setBuyNumber(number);
            vipTradeHkdBuy.setBuyTotal(number);
            vipTradeHkdBuy.setBuyTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));
            vipTradeHkdBuy.setSaleId(String.valueOf(saleUser.getId()));
            vipTradeHkdBuy.setSaleAvater(saleUser.getAvater());
            vipTradeHkdBuy.setSaleNickname(saleUser.getNickname());
            vipTradeHkdBuy.setSalePhone(saleUser.getPhone());
            vipTradeHkdBuy.setIsAppeal(CustomerConstants.NO);
            vipTradeHkdBuy.setBuyType(TradeType.BUY_HKD.getCode());
            vipTradeHkdBuy.setSaleAccount(vipTradeHkdSale.getSaleAccount());
            vipTradeHkdBuy.setSaleAccountProof(vipTradeHkdSale.getSaleAccountProof());

            vipTradeHkdBuyMapper.insertVipTradeHkdBuy(vipTradeHkdBuy);

            VipTradeHkdSale vipTradeHkdSale1 = new VipTradeHkdSale();
            vipTradeHkdSale1.setSaleStatus(TradeStatus.WAIT_BUY_SEND.getCode()); //等待买家付款
            vipTradeHkdSale1.setBuyAvater(vipUser.getAvater());
            vipTradeHkdSale1.setBuyId(String.valueOf(vipUser.getId()));
            vipTradeHkdSale1.setBuyNickname(vipUser.getNickname());
            vipTradeHkdSale1.setBuyPhone(vipUser.getPhone());
            vipTradeHkdSale1.setVipId(vipTradeHkdSale.getVipId());
            vipTradeHkdSale1.setSaleNo(mmNo);
            vipTradeHkdSale1.setSaleTotal(number);
            vipTradeHkdSale1.setSaleTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));
            vipTradeHkdSale1.setSaleNumber(number);
            vipTradeHkdSale1.setIsAppeal(CustomerConstants.NO);
            vipTradeHkdSale1.setSaleType(TradeType.SALE_HKD.getCode());
            vipTradeHkdSale1.setSaleAccount(vipTradeHkdSale.getSaleAccount());
            vipTradeHkdSale1.setSaleAccountProof(vipTradeHkdSale.getSaleAccountProof());

            vipTradeHkdSaleMapper.insertVipTradeHkdSale(vipTradeHkdSale1);

            //更新原订单数量以及时间为现在
            vipTradeHkdSale.setSaleNumber(String.valueOf(NumberUtil.sub(saleNumber,buyNumber)));
            vipTradeHkdSale.setSaleTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));
            vipTradeHkdSale.setSaleTotal(String.valueOf(NumberUtil.sub(saleNumber,buyNumber)));
            vipTradeHkdSale.setIsAppeal(CustomerConstants.NO);
            vipTradeHkdSaleMapper.updateVipTradeHkdSale(vipTradeHkdSale);
        }
        return mmNo;

    }

    /**
     * 上传打款凭证后,更新两个订单
     * @param id
     * @param img
     * @throws Exception
     */
    @Override
    public void updateBuyNo(String id, String img) throws Exception {

        //订单
        VipTradeHkdBuy vipTradeHkdBuy = vipTradeHkdBuyMapper.selectVipTradeHkdBuyById(Integer.parseInt(id));
        String buyNo = vipTradeHkdBuy.getBuyNo();

        //等待卖家确认
        vipTradeHkdBuy.setBuyStatus(TradeStatus.WAIT_SALE_CONFIRM.getCode());
        vipTradeHkdBuy.setProof(img);
        vipTradeHkdBuy.setBuyTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));
        vipTradeHkdBuyMapper.updateVipTradeHkdBuy(vipTradeHkdBuy);

        VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
        vipTradeHkdSale.setSaleNo(buyNo);
        vipTradeHkdSale.setSaleStatus(TradeStatus.WAIT_SALE_CONFIRM.getCode()); //等待卖家确认
        vipTradeHkdSale.setProof(img);
        vipTradeHkdSale.setSaleTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));
        vipTradeHkdSaleMapper.updateVipTradeHkdSale(vipTradeHkdSale);
    }

    /**
     * 根据时间统计交易数量
     * @param beginOfDay
     * @param endOfDay
     * @return
     */
    @Override
    public int selectSum(DateTime beginOfDay, DateTime endOfDay) {
        return vipTradeHkdBuyMapper.selectSum(beginOfDay,endOfDay);
    }
}
