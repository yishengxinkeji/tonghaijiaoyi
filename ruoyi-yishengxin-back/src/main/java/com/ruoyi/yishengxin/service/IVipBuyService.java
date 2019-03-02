package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.vipUser.VipBuy;
import java.util.List;

/**
 * 个人购买 服务层
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
public interface IVipBuyService 
{
	/**
     * 查询个人购买信息
     * 
     * @param id 个人购买ID
     * @return 个人购买信息
     */
	public VipBuy selectVipBuyById(Integer id);
	
	/**
     * 查询个人购买列表
     * 
     * @param vipBuy 个人购买信息
     * @return 个人购买集合
     */
	public List<VipBuy> selectVipBuyList(VipBuy vipBuy);
	
	/**
     * 新增个人购买
     * 
     * @param vipBuy 个人购买信息
     * @return 结果
     */
	public int insertVipBuy(VipBuy vipBuy);
	
	/**
     * 修改个人购买
     * 
     * @param vipBuy 个人购买信息
     * @return 结果
     */
	public int updateVipBuy(VipBuy vipBuy);
		
	/**
     * 删除个人购买信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipBuyByIds(String ids);
	
}
