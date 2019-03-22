package com.ruoyi.yishengxin.service.impl;

import com.ruoyi.yishengxin.domain.goods.GoodsCollection;
import com.ruoyi.yishengxin.mapper.goods.GoodsCollectionMapper;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.service.IGoodsCollectionService;
import com.ruoyi.common.support.Convert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品收藏 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-01
 */
@Service
public class GoodsCollectionServiceImpl implements IGoodsCollectionService 
{
	@Resource
	private GoodsCollectionMapper goodsCollectionMapper;

	/**
     * 查询商品收藏信息
     * 
     * @param id 商品收藏ID
     * @return 商品收藏信息
     */
    @Override
	public GoodsCollection selectGoodsCollectionById(Integer id)
	{
	    return goodsCollectionMapper.selectGoodsCollectionById(id);
	}
	
	/**
     * 查询商品收藏列表
     * 
     * @param goodsCollection 商品收藏信息
     * @return 商品收藏集合
     */
	@Override
	public List<GoodsCollection> selectGoodsCollectionList(GoodsCollection goodsCollection)
	{
	    return goodsCollectionMapper.selectGoodsCollectionList(goodsCollection);
	}
	
    /**
     * 新增商品收藏
     * 
     * @param goodsCollection 商品收藏信息
     * @return 结果
     */
	@Override
	public int insertGoodsCollection(GoodsCollection goodsCollection)
	{
	    return goodsCollectionMapper.insertGoodsCollection(goodsCollection);
	}

	/**
     * 删除商品收藏对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsCollectionByIds(String ids)
	{
		return goodsCollectionMapper.deleteGoodsCollectionByIds(Convert.toStrArray(ids));
	}

	@Override
	public int deleteGoodsCollectionByGid(Integer uid, Integer gid) {
		return goodsCollectionMapper.deleteGoodsCollectionByGid(uid,gid);
	}

}
