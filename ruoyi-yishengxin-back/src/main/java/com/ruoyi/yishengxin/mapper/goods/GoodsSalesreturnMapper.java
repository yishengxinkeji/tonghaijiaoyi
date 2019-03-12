package com.ruoyi.yishengxin.mapper.goods;


import com.ruoyi.yishengxin.domain.goods.GoodsSalesreturn;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品退货 数据层
 * 
 * @author ruoyi
 * @date 2019-03-05
 */
public interface GoodsSalesreturnMapper 
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
     * 删除商品退货
     * 
     * @param id 商品退货ID
     * @return 结果
     */
	public int deleteGoodsSalesreturnById(Integer id);
	
	/**
     * 批量删除商品退货
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsSalesreturnByIds(String[] ids);


	/**
	 * 根据订单号查询订单详情
	 *
	 * @param oraderNumber 订单号
	 * @return
	 */
	@Select("select * from ysx_goods_salesReturn where orderNumber = #{oraderNumber}")
	public GoodsSalesreturn selectGoodsSalesreturnByOrderNumber(String oraderNumber);
	
}