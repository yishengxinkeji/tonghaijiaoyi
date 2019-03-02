package com.ruoyi.yishengxin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipBuyMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipBuy;
import com.ruoyi.yishengxin.service.IVipBuyService;
import com.ruoyi.common.support.Convert;

/**
 * 个人购买 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
@Service
public class VipBuyServiceImpl implements IVipBuyService 
{
	@Autowired
	private VipBuyMapper vipBuyMapper;

	/**
     * 查询个人购买信息
     * 
     * @param id 个人购买ID
     * @return 个人购买信息
     */
    @Override
	public VipBuy selectVipBuyById(Integer id)
	{
	    return vipBuyMapper.selectVipBuyById(id);
	}
	
	/**
     * 查询个人购买列表
     * 
     * @param vipBuy 个人购买信息
     * @return 个人购买集合
     */
	@Override
	public List<VipBuy> selectVipBuyList(VipBuy vipBuy)
	{
	    return vipBuyMapper.selectVipBuyList(vipBuy);
	}
	
    /**
     * 新增个人购买
     * 
     * @param vipBuy 个人购买信息
     * @return 结果
     */
	@Override
	public int insertVipBuy(VipBuy vipBuy)
	{
	    return vipBuyMapper.insertVipBuy(vipBuy);
	}
	
	/**
     * 修改个人购买
     * 
     * @param vipBuy 个人购买信息
     * @return 结果
     */
	@Override
	public int updateVipBuy(VipBuy vipBuy)
	{
	    return vipBuyMapper.updateVipBuy(vipBuy);
	}

	/**
     * 删除个人购买对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVipBuyByIds(String ids)
	{
		return vipBuyMapper.deleteVipBuyByIds(Convert.toStrArray(ids));
	}
	
}
