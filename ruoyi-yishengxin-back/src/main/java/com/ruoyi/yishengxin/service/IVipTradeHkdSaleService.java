package com.ruoyi.yishengxin.service;

import cn.hutool.core.date.DateTime;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeHkdSale;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;

import java.util.List;

/**
 * 挂卖HKD 服务层
 * 
 * @author ruoyi
 * @date 2019-03-07
 */
public interface IVipTradeHkdSaleService 
{
	/**
     * 查询挂卖HKD信息
     * 
     * @param id 挂卖HKDID
     * @return 挂卖HKD信息
     */
	public VipTradeHkdSale selectVipTradeHkdSaleById(Integer id);
	
	/**
     * 查询挂卖HKD列表
     * 
     * @param vipTradeHkdSale 挂卖HKD信息
     * @return 挂卖HKD集合
     */
	public List<VipTradeHkdSale> selectVipTradeHkdSaleList(VipTradeHkdSale vipTradeHkdSale);
	
	/**
     * 新增挂卖HKD
     * 
     * @param vipTradeHkdSale 挂卖HKD信息
     * @return 结果
     */
	public int insertVipTradeHkdSale(VipTradeHkdSale vipTradeHkdSale);
	
	/**
     * 修改挂卖HKD
     * 
     * @param vipTradeHkdSale 挂卖HKD信息
     * @return 结果
     */
	public int updateVipTradeHkdSale(VipTradeHkdSale vipTradeHkdSale);
		
	/**
     * 删除挂卖HKD信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipTradeHkdSaleByIds(String ids);

	/**
	 * 挂卖HKD
	 * @param vipUser
	 * @param number
	 * @return
	 */
    int saleHkd(VipUser vipUser, String number);

	/**
	 * 确认收款
	 *
	 * @param vipUser
	 * @param id
	 * @throws Exception
	 */
	void confirmOrder(VipUser vipUser, String id) throws Exception;

	//取消挂售
	int cancelSale(VipUser vipUser, String id);

    int selectSum(DateTime beginOfDay, DateTime endOfDay);


	/**
	 * 订单过期,根据订单号更新买/卖订单状态
	 * @param orderNo
	 * @return
	 */
	int updateOrderByNo(String orderNo) throws Exception;
}
