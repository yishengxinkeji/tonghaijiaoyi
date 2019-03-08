package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.goods.GoodsShare;

import java.util.List;

/**
 * 商品分享 服务层
 * 
 * @author ruoyi
 * @date 2019-03-05
 */
public interface IGoodsShareService 
{
	/**
     * 查询商品分享信息
     * 
     * @param id 商品分享ID
     * @return 商品分享信息
     */
	public GoodsShare selectGoodsShareById(Integer id);
	
	/**
     * 查询商品分享列表
     * 
     * @param goodsShare 商品分享信息
     * @return 商品分享集合
     */
	public List<GoodsShare> selectGoodsShareList(GoodsShare goodsShare);
	
	/**
     * 新增商品分享
     * 
     * @param goodsShare 商品分享信息
     * @return 结果
     */
	public int insertGoodsShare(GoodsShare goodsShare);
	
	/**
     * 修改商品分享
     * 
     * @param goodsShare 商品分享信息
     * @return 结果
     */
	public int updateGoodsShare(GoodsShare goodsShare);
		
	/**
     * 删除商品分享信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsShareByIds(String ids);

	/**
	 * 根据用户id查询用户的最后一次分享
	 *
	 * @param uid
	 * @return
	 */
	public GoodsShare selectGoodsShareByUid(int uid);
	
}
