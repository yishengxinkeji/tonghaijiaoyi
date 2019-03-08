package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.AppealType;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeHkdBuy;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeHkdSale;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeHkdBuyMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeHkdSaleMapper;
import com.ruoyi.yishengxin.mapper.vipUser.VipUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipAppealMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipAppeal;
import com.ruoyi.yishengxin.service.IVipAppealService;
import com.ruoyi.common.support.Convert;

/**
 * 我的申诉 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-08
 */
@Service
public class VipAppealServiceImpl implements IVipAppealService 
{
	@Autowired
	private VipAppealMapper vipAppealMapper;
	@Autowired
	private VipTradeHkdBuyMapper vipTradeHkdBuyMapper;
	@Autowired
	private VipTradeHkdSaleMapper vipTradeHkdSaleMapper;

	/**
     * 查询我的申诉信息
     * 
     * @param id 我的申诉ID
     * @return 我的申诉信息
     */
    @Override
	public VipAppeal selectVipAppealById(Integer id)
	{
	    return vipAppealMapper.selectVipAppealById(id);
	}
	
	/**
     * 查询我的申诉列表
     * 
     * @param vipAppeal 我的申诉信息
     * @return 我的申诉集合
     */
	@Override
	public List<VipAppeal> selectVipAppealList(VipAppeal vipAppeal)
	{
	    return vipAppealMapper.selectVipAppealList(vipAppeal);
	}
	
    /**
     * 新增我的申诉
     * 
     * @param vipAppeal 我的申诉信息
     * @return 结果
     */
	@Override
	public int insertVipAppeal(VipAppeal vipAppeal)
	{
	    return vipAppealMapper.insertVipAppeal(vipAppeal);
	}
	
	/**
     * 修改我的申诉
     * 
     * @param vipAppeal 我的申诉信息
     * @return 结果
     */
	@Override
	public int updateVipAppeal(VipAppeal vipAppeal)
	{
	    return vipAppealMapper.updateVipAppeal(vipAppeal);
	}

	/**
     * 删除我的申诉对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVipAppealByIds(String ids)
	{
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
	public int appeal(VipUser vipUser, VipAppeal vipAppeal) throws Exception{
		String orderId = vipAppeal.getOrderId();
		String orderType = vipAppeal.getOrderType();
		if(orderType.equals(TradeType.SALE_HKD)){   //卖hkd

			VipTradeHkdSale vipTradeHkdSale = vipTradeHkdSaleMapper.selectVipTradeHkdSaleById(Integer.parseInt(orderId));
			vipTradeHkdSale.setIsAppeal(CustomerConstants.YES);
			vipTradeHkdSale.setAppealStatus(AppealType.APPEALING.getCode());

			vipTradeHkdSaleMapper.updateVipTradeHkdSale(vipTradeHkdSale);

		}else if(orderType.equals(TradeType.BUY_HKD)){  //买HKD
			VipTradeHkdBuy vipTradeHkdBuy = vipTradeHkdBuyMapper.selectVipTradeHkdBuyById(Integer.parseInt(orderId));
			vipTradeHkdBuy.setIsAppeal(CustomerConstants.YES);
			vipTradeHkdBuy.setAppealStatus(AppealType.APPEALING.getCode());

			vipTradeHkdBuyMapper.updateVipTradeHkdBuy(vipTradeHkdBuy);

		}

		vipAppeal.setVipId(vipUser.getId());
		vipAppeal.setAppealStatus(AppealType.APPEALING.getCode());
		vipAppeal.setAppealTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM));
		return vipAppealMapper.insertVipAppeal(vipAppeal);
	}
}
