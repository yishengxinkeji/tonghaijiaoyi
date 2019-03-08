package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import com.ruoyi.yishengxin.domain.goods.GoodsShare;
import com.ruoyi.yishengxin.mapper.goods.GoodsShareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.yishengxin.service.IGoodsShareService;
import com.ruoyi.common.support.Convert;

import javax.annotation.Resource;

/**
 * 商品分享 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-05
 */
@Service
public class GoodsShareServiceImpl implements IGoodsShareService 
{
	@Resource
	private GoodsShareMapper goodsShareMapper;

	/**
     * 查询商品分享信息
     * 
     * @param id 商品分享ID
     * @return 商品分享信息
     */
    @Override
	public GoodsShare selectGoodsShareById(Integer id)
	{
	    return goodsShareMapper.selectGoodsShareById(id);
	}
	
	/**
     * 查询商品分享列表
     * 
     * @param goodsShare 商品分享信息
     * @return 商品分享集合
     */
	@Override
	public List<GoodsShare> selectGoodsShareList(GoodsShare goodsShare)
	{
	    return goodsShareMapper.selectGoodsShareList(goodsShare);
	}
	
    /**
     * 新增商品分享
     * 
     * @param goodsShare 商品分享信息
     * @return 结果
     */
	@Override
	public int insertGoodsShare(GoodsShare goodsShare)
	{
	    return goodsShareMapper.insertGoodsShare(goodsShare);
	}
	
	/**
     * 修改商品分享
     * 
     * @param goodsShare 商品分享信息
     * @return 结果
     */
	@Override
	public int updateGoodsShare(GoodsShare goodsShare)
	{
	    return goodsShareMapper.updateGoodsShare(goodsShare);
	}

	/**
     * 删除商品分享对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsShareByIds(String ids)
	{
		return goodsShareMapper.deleteGoodsShareByIds(Convert.toStrArray(ids));
	}
	
}
