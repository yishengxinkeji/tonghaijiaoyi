package com.ruoyi.yishengxin.mapper.goods;


import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 商品订单 数据层
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
public interface GoodsOrderMapper 
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
     * 删除商品订单
     * 
     * @param id 商品订单ID
     * @return 结果
     */
	public int deleteGoodsOrderById(Integer id);
	
	/**
     * 批量删除商品订单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsOrderByIds(String[] ids);

//@Select("select COUNT(*) from ysx_goods_order where goodsName=#{goodsName} and  UNIX_TIMESTAMP(create_time)  >= UNIX_TIMESTAMP('2015-01-01 13:50:42')  AND  UNIX_TIMESTAMP(create_time)  <= UNIX_TIMESTAMP('2020-02-06 00:00:00') and   goodsStatus != \"代付款\" and goodsStatus != \"已退款\"")
//
	@Select("select COUNT(*) from ysx_goods_order where goodsName=#{goodsName} and create_time between #{startTime} and #{stopTime} and   goodsStatus != \"代付款\" and goodsStatus != \"已退款\"")
	public int selectSoleNumber(@Param("goodsName") String goodsName, @Param("startTime") Date startTime, @Param("stopTime") Date stopTime);
	/**
	 * 根据订单号查订单
	 *
	 * @param orderNumber 订单号
	 * @return 结果
	 */
	@Select("select * from ysx_goods_order where orderNumber=#{orderNumber}")
	public GoodsOrder selectByOraderNumber(String orderNumber);

}