package com.ruoyi.yishengxin.mapper.vipUser;

import com.ruoyi.yishengxin.domain.vipUser.VipTrade;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 交易记录 数据层
 * 
 * @author ruoyi
 * @date 2019-03-04
 */
@Repository
public interface VipTradeMapper 
{
	/**
     * 查询交易记录信息
     * 
     * @param id 交易记录ID
     * @return 交易记录信息
     */
	public VipTrade selectVipTradeById(Integer id);
	
	/**
     * 查询交易记录列表
     * 
     * @param vipTrade 交易记录信息
     * @return 交易记录集合
     */
	public List<VipTrade> selectVipTradeList(VipTrade vipTrade);
	
	/**
     * 新增交易记录
     * 
     * @param vipTrade 交易记录信息
     * @return 结果
     */
	public int insertVipTrade(VipTrade vipTrade);
	
	/**
     * 修改交易记录
     * 
     * @param vipTrade 交易记录信息
     * @return 结果
     */
	public int updateVipTrade(VipTrade vipTrade);
	
	/**
     * 删除交易记录
     * 
     * @param id 交易记录ID
     * @return 结果
     */
	public int deleteVipTradeById(Integer id);
	
	/**
     * 批量删除交易记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipTradeByIds(String[] ids);

	/**
	 * 今日总共转了多少
	 * @param vipUser
	 * @return
	 */
	String selectTranByDay(VipTrade vipUser);
}