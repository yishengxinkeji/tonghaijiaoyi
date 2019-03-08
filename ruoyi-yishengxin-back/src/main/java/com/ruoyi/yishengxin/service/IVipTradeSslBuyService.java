package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslBuy;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslSale;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;

import java.util.List;

/**
 * 挂买SSL 服务层
 * 
 * @author ruoyi
 * @date 2019-03-06
 */
public interface IVipTradeSslBuyService
{
	/**
     * 查询挂买SSL信息
     * 
     * @param id 挂买SSLID
     * @return 挂买SSL信息
     */
	public VipTradeSslBuy selectVipTradeBuyById(Integer id);
	
	/**
     * 查询挂买SSL列表
     * 
     * @param vipTradeSslBuy 挂买SSL信息
     * @return 挂买SSL集合
     */
	public List<VipTradeSslBuy> selectVipTradeBuyList(VipTradeSslBuy vipTradeSslBuy);
	
	/**
     * 新增挂买SSL
     * 
     * @param vipTradeSslBuy 挂买SSL信息
     * @return 结果
     */
	public int insertVipTradeBuy(VipTradeSslBuy vipTradeSslBuy);
	
	/**
     * 修改挂买SSL
     * 
     * @param vipTradeSslBuy 挂买SSL信息
     * @return 结果
     */
	public int updateVipTradeBuy(VipTradeSslBuy vipTradeSslBuy);
		
	/**
     * 删除挂买SSL信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipTradeBuyByIds(String ids);

	/**
	 * 挂买SSL
	 * @param vipUser
	 * @param number
	 * @param price
	 */
    int buySsl(VipUser vipUser, String number, String price) throws Exception;

    //卖大于买
	void dealLgOrder(VipTradeSslBuy vipTradeSslBuy1, VipTradeSslSale vipTradeSslSale1);
	//卖等于买
	void dealEqOrder(VipTradeSslBuy vipTradeSslBuy1, VipTradeSslSale vipTradeSslSale1);
	//卖小于买
	void dealltOrder(VipTradeSslBuy vipTradeSslBuy1, VipTradeSslSale vipTradeSslSale1);
}
