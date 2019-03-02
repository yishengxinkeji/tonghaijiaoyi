package com.ruoyi.yishengxin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipExchangeMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipExchange;
import com.ruoyi.yishengxin.service.IVipExchangeService;
import com.ruoyi.common.support.Convert;

/**
 * 个人兑换 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
@Service
public class VipExchangeServiceImpl implements IVipExchangeService 
{
	@Autowired
	private VipExchangeMapper vipExchangeMapper;

	/**
     * 查询个人兑换信息
     * 
     * @param id 个人兑换ID
     * @return 个人兑换信息
     */
    @Override
	public VipExchange selectVipExchangeById(Integer id)
	{
	    return vipExchangeMapper.selectVipExchangeById(id);
	}
	
	/**
     * 查询个人兑换列表
     * 
     * @param vipExchange 个人兑换信息
     * @return 个人兑换集合
     */
	@Override
	public List<VipExchange> selectVipExchangeList(VipExchange vipExchange)
	{
	    return vipExchangeMapper.selectVipExchangeList(vipExchange);
	}
	
    /**
     * 新增个人兑换
     * 
     * @param vipExchange 个人兑换信息
     * @return 结果
     */
	@Override
	public int insertVipExchange(VipExchange vipExchange)
	{
	    return vipExchangeMapper.insertVipExchange(vipExchange);
	}
	
	/**
     * 修改个人兑换
     * 
     * @param vipExchange 个人兑换信息
     * @return 结果
     */
	@Override
	public int updateVipExchange(VipExchange vipExchange)
	{
	    return vipExchangeMapper.updateVipExchange(vipExchange);
	}

	/**
     * 删除个人兑换对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVipExchangeByIds(String ids)
	{
		return vipExchangeMapper.deleteVipExchangeByIds(Convert.toStrArray(ids));
	}
	
}
