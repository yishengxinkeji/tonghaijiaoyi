package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import com.ruoyi.yishengxin.domain.goods.GoodsSalesreturn;
import com.ruoyi.yishengxin.mapper.goods.GoodsSalesreturnMapper;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.service.IGoodsSalesreturnService;
import com.ruoyi.common.support.Convert;

import javax.annotation.Resource;

/**
 * 商品退货 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-05
 */
@Service
public class GoodsSalesreturnServiceImpl implements IGoodsSalesreturnService 
{
	@Resource
	private GoodsSalesreturnMapper goodsSalesreturnMapper;

	/**
     * 查询商品退货信息
     * 
     * @param id 商品退货ID
     * @return 商品退货信息
     */
    @Override
	public GoodsSalesreturn selectGoodsSalesreturnById(Integer id)
	{
	    return goodsSalesreturnMapper.selectGoodsSalesreturnById(id);
	}
	
	/**
     * 查询商品退货列表
     * 
     * @param goodsSalesreturn 商品退货信息
     * @return 商品退货集合
     */
	@Override
	public List<GoodsSalesreturn> selectGoodsSalesreturnList(GoodsSalesreturn goodsSalesreturn)
	{
	    return goodsSalesreturnMapper.selectGoodsSalesreturnList(goodsSalesreturn);
	}
	
    /**
     * 新增商品退货
     * 
     * @param goodsSalesreturn 商品退货信息
     * @return 结果
     */
	@Override
	public int insertGoodsSalesreturn(GoodsSalesreturn goodsSalesreturn)
	{
	    return goodsSalesreturnMapper.insertGoodsSalesreturn(goodsSalesreturn);
	}
	
	/**
     * 修改商品退货
     * 
     * @param goodsSalesreturn 商品退货信息
     * @return 结果
     */
	@Override
	public int updateGoodsSalesreturn(GoodsSalesreturn goodsSalesreturn)
	{
	    return goodsSalesreturnMapper.updateGoodsSalesreturn(goodsSalesreturn);
	}

	/**
     * 删除商品退货对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsSalesreturnByIds(String ids)
	{
		return goodsSalesreturnMapper.deleteGoodsSalesreturnByIds(Convert.toStrArray(ids));
	}

	@Override
	public GoodsSalesreturn selectGoodsSalesreturnByOrderNumber(String oraderNumber) {
		return goodsSalesreturnMapper.selectGoodsSalesreturnByOrderNumber(oraderNumber);
	}

}
