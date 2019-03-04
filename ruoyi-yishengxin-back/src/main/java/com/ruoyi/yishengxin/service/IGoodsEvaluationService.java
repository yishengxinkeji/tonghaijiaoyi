package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.goods.GoodsEvaluation;

import java.util.List;

/**
 * 商品评价 服务层
 * 
 * @author ruoyi
 * @date 2019-03-04
 */
public interface IGoodsEvaluationService 
{
	/**
     * 查询商品评价信息
     * 
     * @param id 商品评价ID
     * @return 商品评价信息
     */
	public GoodsEvaluation selectGoodsEvaluationById(Integer id);
	
	/**
     * 查询商品评价列表
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 商品评价集合
     */
	public List<GoodsEvaluation> selectGoodsEvaluationList(GoodsEvaluation goodsEvaluation);
	
	/**
     * 新增商品评价
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 结果
     */
	public int insertGoodsEvaluation(GoodsEvaluation goodsEvaluation);
	
	/**
     * 修改商品评价
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 结果
     */
	public int updateGoodsEvaluation(GoodsEvaluation goodsEvaluation);
		
	/**
     * 删除商品评价信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsEvaluationByIds(String ids);
	
}
