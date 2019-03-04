package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import com.ruoyi.yishengxin.domain.goods.GoodsEvaluation;
import com.ruoyi.yishengxin.mapper.goods.GoodsEvaluationMapper;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.service.IGoodsEvaluationService;
import com.ruoyi.common.support.Convert;

import javax.annotation.Resource;

/**
 * 商品评价 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-04
 */
@Service
public class GoodsEvaluationServiceImpl implements IGoodsEvaluationService 
{
	@Resource
	private GoodsEvaluationMapper goodsEvaluationMapper;

	/**
     * 查询商品评价信息
     * 
     * @param id 商品评价ID
     * @return 商品评价信息
     */
    @Override
	public GoodsEvaluation selectGoodsEvaluationById(Integer id)
	{
	    return goodsEvaluationMapper.selectGoodsEvaluationById(id);
	}
	
	/**
     * 查询商品评价列表
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 商品评价集合
     */
	@Override
	public List<GoodsEvaluation> selectGoodsEvaluationList(GoodsEvaluation goodsEvaluation)
	{
	    return goodsEvaluationMapper.selectGoodsEvaluationList(goodsEvaluation);
	}
	
    /**
     * 新增商品评价
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 结果
     */
	@Override
	public int insertGoodsEvaluation(GoodsEvaluation goodsEvaluation)
	{
	    return goodsEvaluationMapper.insertGoodsEvaluation(goodsEvaluation);
	}
	
	/**
     * 修改商品评价
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 结果
     */
	@Override
	public int updateGoodsEvaluation(GoodsEvaluation goodsEvaluation)
	{
	    return goodsEvaluationMapper.updateGoodsEvaluation(goodsEvaluation);
	}

	/**
     * 删除商品评价对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsEvaluationByIds(String ids)
	{
		return goodsEvaluationMapper.deleteGoodsEvaluationByIds(Convert.toStrArray(ids));
	}
	
}
