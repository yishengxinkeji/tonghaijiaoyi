package com.ruoyi.yishengxin.service;

import cn.hutool.core.date.DateTime;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslBuy;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslSale;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;

import java.util.List;
import java.util.Map;

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
	//取消挂买
    int cancelBuy(VipUser vipUser, String id);

	/**
	 * 根据时间统计
	 * @param beginOfDay
	 * @param endOfDay
	 * @return
	 */
    double selectSum(DateTime beginOfDay, DateTime endOfDay);

    double selectAvgByDay(DateTime beginOfDay, DateTime endOfDay);

    void dealTimer(VipTradeSslBuy vipTradeSslBuy1,List<VipTradeSslSale> list);

	/**
	 * 按单价分组统计交易中的数量
	 * @return
	 */
	List<Map<String,String>> selectSumNumberByUnitPrice();
}
