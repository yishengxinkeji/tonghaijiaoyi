package com.ruoyi.yishengxin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.GiftMapper;
import com.ruoyi.yishengxin.domain.Gift;
import com.ruoyi.yishengxin.service.IGiftService;
import com.ruoyi.common.support.Convert;

/**
 * 礼包设置 服务层实现
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
@Service
public class GiftServiceImpl implements IGiftService 
{
	@Autowired
	private GiftMapper giftMapper;

	/**
     * 查询礼包设置信息
     * 
     * @param id 礼包设置ID
     * @return 礼包设置信息
     */
    @Override
	public Gift selectGiftById(Integer id)
	{
	    return giftMapper.selectGiftById(id);
	}
	
	/**
     * 查询礼包设置列表
     * 
     * @param gift 礼包设置信息
     * @return 礼包设置集合
     */
	@Override
	public List<Gift> selectGiftList(Gift gift)
	{
	    return giftMapper.selectGiftList(gift);
	}
	
    /**
     * 新增礼包设置
     * 
     * @param gift 礼包设置信息
     * @return 结果
     */
	@Override
	public int insertGift(Gift gift)
	{
	    return giftMapper.insertGift(gift);
	}
	
	/**
     * 修改礼包设置
     * 
     * @param gift 礼包设置信息
     * @return 结果
     */
	@Override
	public int updateGift(Gift gift)
	{
	    return giftMapper.updateGift(gift);
	}

	/**
     * 删除礼包设置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGiftByIds(String ids)
	{
		return giftMapper.deleteGiftByIds(Convert.toStrArray(ids));
	}
	
}
