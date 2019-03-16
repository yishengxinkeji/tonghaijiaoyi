package com.ruoyi.yishengxin.service;

import cn.hutool.core.date.DateTime;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeHkdBuy;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;

import java.util.List;

/**
 * 购买HKD 服务层
 * 
 * @author ruoyi
 * @date 2019-03-07
 */
public interface IVipTradeHkdBuyService 
{
	/**
     * 查询购买HKD信息
     * 
     * @param id 购买HKDID
     * @return 购买HKD信息
     */
	public VipTradeHkdBuy selectVipTradeHkdBuyById(Integer id);
	
	/**
     * 查询购买HKD列表
     * 
     * @param vipTradeHkdBuy 购买HKD信息
     * @return 购买HKD集合
     */
	public List<VipTradeHkdBuy> selectVipTradeHkdBuyList(VipTradeHkdBuy vipTradeHkdBuy);
	
	/**
     * 新增购买HKD
     * 
     * @param vipTradeHkdBuy 购买HKD信息
     * @return 结果
     */
	public int insertVipTradeHkdBuy(VipTradeHkdBuy vipTradeHkdBuy);
	
	/**
     * 修改购买HKD
     * 
     * @param vipTradeHkdBuy 购买HKD信息
     * @return 结果
     */
	public int updateVipTradeHkdBuy(VipTradeHkdBuy vipTradeHkdBuy);
		
	/**
     * 删除购买HKD信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipTradeHkdBuyByIds(String ids);

	/**
	 * 购买HKD
	 * @param vipUser
	 * @param id
	 * @param number
	 * @return
	 */
    String buyHkd(VipUser vipUser, String id, String number) throws Exception;

    void updateBuyNo(String id, String img) throws Exception;

    int selectSum(DateTime beginOfDay, DateTime endOfDay);
}
