package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import com.ruoyi.yishengxin.domain.goods.Goods;
import com.ruoyi.yishengxin.mapper.goods.GoodsMapper;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.service.IGoodsService;
import com.ruoyi.common.support.Convert;

import javax.annotation.Resource;

/**
 * 商品 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-05
 */
@Service
public class GoodsServiceImpl implements IGoodsService 
{
	@Resource
	private GoodsMapper goodsMapper;

	/**
     * 查询商品信息
     * 
     * @param id 商品ID
     * @return 商品信息
     */
    @Override
	public Goods selectGoodsById(Integer id)
	{
	    return goodsMapper.selectGoodsById(id);
	}
	
	/**
     * 查询商品列表
     * 
     * @param goods 商品信息
     * @return 商品集合
     */
	@Override
	public List<Goods> selectGoodsList(Goods goods)
	{
	    return goodsMapper.selectGoodsList(goods);
	}

	/**
	 * 查询商品信息
	 *
	 * @param goodsName 商品名称
	 * @return 商品信息
	 */
	@Override
	public Goods selectGoodsByGoodsName(String goodsName) {
		return goodsMapper.selectGoodsByGoodsName(goodsName);
	}

	/**
     * 新增商品
     * 
     * @param goods 商品信息
     * @return 结果
     */
	@Override
	public int insertGoods(Goods goods)
	{
	    return goodsMapper.insertGoods(goods);
	}
	
	/**
     * 修改商品
     * 
     * @param goods 商品信息
     * @return 结果
     */
	@Override
	public int updateGoods(Goods goods)
	{
	    return goodsMapper.updateGoods(goods);
	}

	/**
     * 删除商品对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsByIds(String ids)
	{
		return goodsMapper.deleteGoodsByIds(Convert.toStrArray(ids));
	}




}
