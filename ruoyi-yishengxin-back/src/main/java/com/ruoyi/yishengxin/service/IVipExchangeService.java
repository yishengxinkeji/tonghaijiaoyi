package com.ruoyi.yishengxin.service;

import cn.hutool.core.date.DateTime;
import com.ruoyi.yishengxin.domain.vipUser.VipExchange;
import java.util.List;

/**
 * 个人兑换 服务层
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
public interface IVipExchangeService 
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
     * 删除个人兑换信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipExchangeByIds(String ids);

	/**
	 * 客服处理用户兑换
	 * @param vipExchange
	 * @return
	 */
    int exchange(VipExchange vipExchange) throws Exception;

	double selectSumByIfExchage(String s);

    double selectSumByIfExchageAndTime(String s, DateTime begin, DateTime end);
}
