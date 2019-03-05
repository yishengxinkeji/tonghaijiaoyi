package com.ruoyi.yishengxin.service;


import com.ruoyi.yishengxin.domain.goods.GoodsSalesreturn;

import java.util.List;

/**
 * 商品退货 服务层
 * 
 * @author ruoyi
 * @date 2019-03-05
 */
public interface IGoodsSalesreturnService 
{
	/**
     * 查询商品退货信息
     * 
     * @param id 商品退货ID
     * @return 商品退货信息
     */
	public GoodsSalesreturn selectGoodsSalesreturnById(Integer id);
	
	/**
     * 查询商品退货列表
     * 
     * @param goodsSalesreturn 商品退货信息
     * @return 商品退货集合
     */
	public List<GoodsSalesreturn> selectGoodsSalesreturnList(GoodsSalesreturn goodsSalesreturn);
	
	/**
     * 新增商品退货
     * 
     * @param goodsSalesreturn 商品退货信息
     * @return 结果
     */
	public int insertGoodsSalesreturn(GoodsSalesreturn goodsSalesreturn);
	
	/**
     * 修改商品退货
     * 
     * @param goodsSalesreturn 商品退货信息
     * @return 结果
     */
	public int updateGoodsSalesreturn(GoodsSalesreturn goodsSalesreturn);
		
	/**
     * 删除商品退货信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsSalesreturnByIds(String ids);

	/**
	 * 根据订单号查询订单详情
	 *
	 * @param oraderNumber 订单号
	 * @return
	 */

	public GoodsSalesreturn selectGoodsSalesreturnByOrderNumber(String oraderNumber);
	
}
