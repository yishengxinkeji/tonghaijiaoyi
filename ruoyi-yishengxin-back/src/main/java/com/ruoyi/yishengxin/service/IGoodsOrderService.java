package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.goods.GoodsOrder;

import java.util.Date;
import java.util.List;

/**
 * 商品订单 服务层
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
public interface IGoodsOrderService 
{
	/**
     * 查询商品订单信息
     * 
     * @param id 商品订单ID
     * @return 商品订单信息
     */
	public GoodsOrder selectGoodsOrderById(Integer id);
	
	/**
     * 查询商品订单列表
     * 
     * @param goodsOrder 商品订单信息
     * @return 商品订单集合
     */
	public List<GoodsOrder> selectGoodsOrderList(GoodsOrder goodsOrder);
	
	/**
     * 新增商品订单
     * 
     * @param goodsOrder 商品订单信息
     * @return 结果
     */
	public int insertGoodsOrder(GoodsOrder goodsOrder);
	
	/**
     * 修改商品订单
     * 
     * @param goodsOrder 商品订单信息
     * @return 结果
     */
	public int updateGoodsOrder(GoodsOrder goodsOrder);
		
	/**
     * 删除商品订单信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsOrderByIds(String ids);

	public int selectSoleNumber(String goodsName, Date startTime, Date stopTime);

	/**
	 * 根据订单号查订单
	 *
	 * @param orderNumber 订单号
	 * @return 结果
	 */
	public GoodsOrder selectByOraderNumber(String orderNumber);
}
