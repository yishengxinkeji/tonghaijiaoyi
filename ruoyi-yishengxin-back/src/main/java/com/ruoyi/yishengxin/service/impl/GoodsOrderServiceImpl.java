package com.ruoyi.yishengxin.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import com.ruoyi.yishengxin.mapper.goods.GoodsOrderMapper;
import org.springframework.stereotype.Service;

import com.ruoyi.yishengxin.service.IGoodsOrderService;
import com.ruoyi.common.support.Convert;

import javax.annotation.Resource;

/**
 * 商品订单 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
@Service
public class GoodsOrderServiceImpl implements IGoodsOrderService

{
	@Resource
	private GoodsOrderMapper goodsOrderMapper;

	/**
     * 查询商品订单信息
     * 
     * @param id 商品订单ID
     * @return 商品订单信息
     */
    @Override
	public GoodsOrder selectGoodsOrderById(Integer id)
	{
	    return goodsOrderMapper.selectGoodsOrderById(id);
	}
	
	/**
     * 查询商品订单列表
     * 
     * @param goodsOrder 商品订单信息
     * @return 商品订单集合
     */
	@Override
	public List<GoodsOrder> selectGoodsOrderList(GoodsOrder goodsOrder)
	{
	    return goodsOrderMapper.selectGoodsOrderList(goodsOrder);
	}
	
    /**
     * 新增商品订单
     * 
     * @param goodsOrder 商品订单信息
     * @return 结果
     */
	@Override
	public int insertGoodsOrder(GoodsOrder goodsOrder)
	{
		goodsOrderMapper.insertGoodsOrder(goodsOrder);
		return goodsOrder.getId();
	}
	
	/**
     * 修改商品订单
     * 
     * @param goodsOrder 商品订单信息
     * @return 结果
     */
	@Override
	public int updateGoodsOrder(GoodsOrder goodsOrder)
	{
	    return goodsOrderMapper.updateGoodsOrder(goodsOrder);
	}

	/**
     * 删除商品订单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsOrderByIds(String ids)
	{
		return goodsOrderMapper.deleteGoodsOrderByIds(Convert.toStrArray(ids));
	}

	@Override
	public int selectSoleNumber(String goodsName, Date startTime, Date stopTime) {
		return goodsOrderMapper.selectSoleNumber(goodsName,startTime,stopTime);
	}

	@Override
	public GoodsOrder selectByOraderNumber(String orderNumber) {
		return goodsOrderMapper.selectByOraderNumber(orderNumber);
	}

	@Override
	public double selectSum() {
		return goodsOrderMapper.selectSum();
	}
}
