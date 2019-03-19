package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import com.ruoyi.yishengxin.domain.goods.GoodsRefundreason;
import com.ruoyi.yishengxin.mapper.goods.GoodsRefundreasonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.yishengxin.service.IGoodsRefundreasonService;
import com.ruoyi.common.support.Convert;

/**
 * 退款原因 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-19
 */
@Service
public class GoodsRefundreasonServiceImpl implements IGoodsRefundreasonService 
{
	@Autowired
	private GoodsRefundreasonMapper goodsRefundreasonMapper;

	/**
     * 查询退款原因信息
     * 
     * @param id 退款原因ID
     * @return 退款原因信息
     */
    @Override
	public GoodsRefundreason selectGoodsRefundreasonById(Integer id)
	{
	    return goodsRefundreasonMapper.selectGoodsRefundreasonById(id);
	}
	
	/**
     * 查询退款原因列表
     * 
     * @param goodsRefundreason 退款原因信息
     * @return 退款原因集合
     */
	@Override
	public List<GoodsRefundreason> selectGoodsRefundreasonList(GoodsRefundreason goodsRefundreason)
	{
	    return goodsRefundreasonMapper.selectGoodsRefundreasonList(goodsRefundreason);
	}
	
    /**
     * 新增退款原因
     * 
     * @param goodsRefundreason 退款原因信息
     * @return 结果
     */
	@Override
	public int insertGoodsRefundreason(GoodsRefundreason goodsRefundreason)
	{
	    return goodsRefundreasonMapper.insertGoodsRefundreason(goodsRefundreason);
	}
	
	/**
     * 修改退款原因
     * 
     * @param goodsRefundreason 退款原因信息
     * @return 结果
     */
	@Override
	public int updateGoodsRefundreason(GoodsRefundreason goodsRefundreason)
	{
	    return goodsRefundreasonMapper.updateGoodsRefundreason(goodsRefundreason);
	}

	/**
     * 删除退款原因对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsRefundreasonByIds(String ids)
	{
		return goodsRefundreasonMapper.deleteGoodsRefundreasonByIds(Convert.toStrArray(ids));
	}
	
}
