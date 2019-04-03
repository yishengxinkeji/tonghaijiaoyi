package com.ruoyi.yishengxin.service;

import cn.hutool.core.date.DateTime;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslSale;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;

import java.util.List;
import java.util.Map;

/**
 * 挂卖SSL 服务层
 * 
 * @author ruoyi
 * @date 2019-03-06
 */
public interface IVipTradeSslSaleService
{
	/**
     * 查询挂卖SSL信息
     * 
     * @param id 挂卖SSLID
     * @return 挂卖SSL信息
     */
	public VipTradeSslSale selectVipTradeSaleById(Integer id);
	
	/**
     * 查询挂卖SSL列表
     * 
     * @param vipTradeSslSale 挂卖SSL信息
     * @return 挂卖SSL集合
     */
	public List<VipTradeSslSale> selectVipTradeSaleList(VipTradeSslSale vipTradeSslSale);
	
	/**
     * 新增挂卖SSL
     * 
     * @param vipTradeSslSale 挂卖SSL信息
     * @return 结果
     */
	public int insertVipTradeSale(VipTradeSslSale vipTradeSslSale);
	
	/**
     * 修改挂卖SSL
     * 
     * @param vipTradeSslSale 挂卖SSL信息
     * @return 结果
     */
	public int updateVipTradeSale(VipTradeSslSale vipTradeSslSale);
		
	/**
     * 删除挂卖SSL信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipTradeSaleByIds(String ids);

	//处理挂卖ssl
    int saleSsl(VipUser vipUser,String number, String price) throws Exception;

    //取消挂售
	int cancelSale(VipUser vipUser, String id);


    List<Map<String,String>> selectSale(DateTime beginOfDay, DateTime endOfDay);

    int selectSum(DateTime beginOfDay, DateTime endOfDay);

	/**
	 * 查询最近两条成交记录
	 * @return
	 */
	List<Map<String,String>> selectTwoLeast();

	/**
	 * 查询最近一条成交记录
	 * @return
	 */
	Map<String,String> selectByMaxId();

	/**
	 * 按单价分组统计交易中的数量
	 * @return
	 */
	List<Map<String,String>> selectSumNumberByUnitPrice();
}
