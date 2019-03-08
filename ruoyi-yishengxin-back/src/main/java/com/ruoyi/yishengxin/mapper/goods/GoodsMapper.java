package com.ruoyi.yishengxin.mapper.goods;

import com.ruoyi.yishengxin.domain.goods.Goods;

import java.util.List;

/**
 * 商品 数据层
 * 
 * @author ruoyi
 * @date 2019-03-05
 */
public interface GoodsMapper 
{
	/**
     * 查询商品信息
     * 
     * @param id 商品ID
     * @return 商品信息
     */
	public Goods selectGoodsById(Integer id);
	
	/**
     * 查询商品列表
     * 
     * @param goods 商品信息
     * @return 商品集合
     */

	public List<Goods> selectGoodsList(Goods goods);
	
	/**
     * 新增商品
     * 
     * @param goods 商品信息
     * @return 结果
     */
	public int insertGoods(Goods goods);
	
	/**
     * 修改商品
     * 
     * @param goods 商品信息
     * @return 结果
     */
	public int updateGoods(Goods goods);
	
	/**
     * 删除商品
     * 
     * @param id 商品ID
     * @return 结果
     */
	public int deleteGoodsById(Integer id);
	
	/**
     * 批量删除商品
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsByIds(String[] ids);
	
}