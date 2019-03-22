package com.ruoyi.yishengxin.service;


import com.ruoyi.yishengxin.domain.goods.GoodsCollection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品收藏 服务层
 * 
 * @author ruoyi
 * @date 2019-03-01
 */
public interface IGoodsCollectionService 
{
	/**
     * 查询商品收藏信息
     * 
     * @param id 商品收藏ID
     * @return 商品收藏信息
     */
	public GoodsCollection selectGoodsCollectionById(Integer id);
	
	/**
     * 查询商品收藏列表
     * 
     * @param goodsCollection 商品收藏信息
     * @return 商品收藏集合
     */
	public List<GoodsCollection> selectGoodsCollectionList(GoodsCollection goodsCollection);
	
	/**
     * 新增商品收藏
     * 
     * @param goodsCollection 商品收藏信息
     * @return 结果
     */
	public int insertGoodsCollection(GoodsCollection goodsCollection);

	/**
     * 删除商品收藏信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsCollectionByIds(String ids);

	/**
	 * 用户删除特定商品收藏
	 *
	 * @param
	 * @return 结果
	 */

	public int deleteGoodsCollectionByGid(Integer uid, Integer gid);
	
}
