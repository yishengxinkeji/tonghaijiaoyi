package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.AppealType;
import com.ruoyi.common.enums.DutyDealWay;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.yishengxin.domain.Customer;
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.domain.vipUser.*;
import com.ruoyi.yishengxin.mapper.TradeMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeHkdBuyMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeHkdSaleMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipAppealMapper;
import com.ruoyi.yishengxin.service.IVipAppealService;
import com.ruoyi.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 我的申诉 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-13
 */
@Service
public class VipAppealServiceImpl implements IVipAppealService {
    @Autowired
    private VipAppealMapper vipAppealMapper;
    @Autowired
    private VipTradeHkdSaleMapper vipTradeHkdSaleMapper;
    @Autowired
    private VipTradeHkdBuyMapper vipTradeHkdBuyMapper;
    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private VipUserMapper vipUserMapper;


    /**
     * 查询我的申诉信息
     *
     * @param id 我的申诉ID
     * @return 我的申诉信息
     */
    @Override
    public VipAppeal selectVipAppealById(Integer id) {
        return vipAppealMapper.selectVipAppealById(id);
    }

    /**
     * 查询我的申诉列表
     *
     * @param vipAppeal 我的申诉信息
     * @return 我的申诉集合
     */
    @Override
    public List<VipAppeal> selectVipAppealList(VipAppeal vipAppeal) {
        return vipAppealMapper.selectVipAppealList(vipAppeal);
    }

    /**
     * 新增我的申诉
     *
     * @param vipAppeal 我的申诉信息
     * @return 结果
     */
    @Override
    public int insertVipAppeal(VipAppeal vipAppeal) {
        return vipAppealMapper.insertVipAppeal(vipAppeal);
    }

    /**
     * 修改我的申诉
     *
     * @param vipAppeal 我的申诉信息
     * @return 结果
     */
    @Override
    public int updateVipAppeal(VipAppeal vipAppeal) {
        return vipAppealMapper.updateVipAppeal(vipAppeal);
    }

    /**
     * 删除我的申诉对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVipAppealByIds(String ids) {
        return vipAppealMapper.deleteVipAppealByIds(Convert.toStrArray(ids));
    }

    /**
     * 申诉 //插入申诉记录,修改订单的申诉状态
     * @param vipUser
     * @param vipAppeal
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public int appeal(VipUser vipUser, VipAppeal vipAppeal) throws Exception{
        String orderId = vipAppeal.getOrderId();
        String orderType = vipAppeal.getOrderType();
        if(orderType.equals(TradeType.SALE_HKD.getCode())){   //卖hkd

            VipTradeHkdSale vipTradeHkdSale = vipTradeHkdSaleMapper.selectVipTradeHkdSaleById(Integer.parseInt(orderId));
            vipTradeHkdSale.setIsAppeal(CustomerConstants.YES);
            vipTradeHkdSale.setAppealStatus(AppealType.APPEALING.getCode());

            vipTradeHkdSaleMapper.updateVipTradeHkdSale(vipTradeHkdSale);

            vipAppeal.setOrderNo(vipTradeHkdSale.getSaleNo());  //订单号
            vipAppeal.setSaleId(vipTradeHkdSale.getVipId());    //卖家id
            vipAppeal.setBuyId(Integer.parseInt(vipTradeHkdSale.getBuyId()));   //买家id
            vipAppeal.setSalePhone(vipUser.getPhone()); //卖家手机号
            vipAppeal.setBuyPhone(vipTradeHkdSale.getBuyPhone());   //买家手机号

        }else if(orderType.equals(TradeType.BUY_HKD.getCode())){  //买HKD
            VipTradeHkdBuy vipTradeHkdBuy = vipTradeHkdBuyMapper.selectVipTradeHkdBuyById(Integer.parseInt(orderId));
            vipTradeHkdBuy.setIsAppeal(CustomerConstants.YES);
            vipTradeHkdBuy.setAppealStatus(AppealType.APPEALING.getCode());

            vipTradeHkdBuyMapper.updateVipTradeHkdBuy(vipTradeHkdBuy);

            vipAppeal.setOrderNo(vipTradeHkdBuy.getBuyNo());    //订单号
            vipAppeal.setSaleId(Integer.parseInt(vipTradeHkdBuy.getSaleId()));    //卖家id
            vipAppeal.setBuyId(vipUser.getId());   //买家id
            vipAppeal.setSalePhone(vipTradeHkdBuy.getSalePhone()); //卖家手机号
            vipAppeal.setBuyPhone(vipUser.getPhone());   //买家手机号
        }

        vipAppeal.setAppealVipId(String.valueOf(vipUser.getId()));
        vipAppeal.setAppealStatus(AppealType.APPEALING.getCode());
        vipAppeal.setAppealTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD));

        return vipAppealMapper.insertVipAppeal(vipAppeal);
    }

    /**
     * 处理客户申诉
     * @param vipAppeal
     * @return
     */
    @Override
    public int dealAppeal(VipAppeal vipAppeal) {
        vipAppeal.setAppealStatus(AppealType.SUCCESS.getCode());
        return vipAppealMapper.updateVipAppeal(vipAppeal);

    }
}
