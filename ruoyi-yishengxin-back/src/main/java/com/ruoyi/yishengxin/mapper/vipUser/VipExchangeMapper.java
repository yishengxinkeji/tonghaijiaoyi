package com.ruoyi.yishengxin.mapper.vipUser;

import com.ruoyi.yishengxin.domain.vipUser.VipExchange;
import com.ruoyi.yishengxin.domain.vipUser.VipExchange;

import java.util.List;

/**
 * 个人兑换 数据层
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
public interface VipExchangeMapper 
{
	/**
     * 查询个人兑换信息
     * 
     * @param id 个人兑换ID
     * @return 个人兑换信息
     */
	public VipExchange selectVipExchangeById(Integer id);
	
	/**
     * 查询个人兑换列表
     * 
     * @param vipExchange 个人兑换信息
     * @return 个人兑换集合
     */
	public List<VipExchange> selectVipExchangeList(VipExchange vipExchange);
	
	/**
     * 新增个人兑换
     * 
     * @param vipExchange 个人兑换信息
     * @return 结果
     */
	public int insertVipExchange(VipExchange vipExchange);
	
	/**
     * 修改个人兑换
     * 
     * @param vipExchange 个人兑换信息
     * @return 结果
     */
	public int updateVipExchange(VipExchange vipExchange);
	
	/**
     * 删除个人兑换
     * 
     * @param id 个人兑换ID
     * @return 结果
     */
	public int deleteVipExchangeById(Integer id);
	
	/**
     * 批量删除个人兑换
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipExchangeByIds(String[] ids);
	
}