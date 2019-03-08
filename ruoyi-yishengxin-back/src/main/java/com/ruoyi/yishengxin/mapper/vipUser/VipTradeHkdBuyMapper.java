package com.ruoyi.yishengxin.mapper.vipUser;

import com.ruoyi.yishengxin.domain.vipUser.VipTradeHkdBuy;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 购买HKD 数据层
 * 
 * @author ruoyi
 * @date 2019-03-07
 */
@Repository
public interface VipTradeHkdBuyMapper {
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
     * 删除购买HKD
     * 
     * @param id 购买HKDID
     * @return 结果
     */
	public int deleteVipTradeHkdBuyById(Integer id);
	
	/**
     * 批量删除购买HKD
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipTradeHkdBuyByIds(String[] ids);
	
}