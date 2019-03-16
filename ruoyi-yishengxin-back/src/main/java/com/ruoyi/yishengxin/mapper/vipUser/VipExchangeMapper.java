package com.ruoyi.yishengxin.mapper.vipUser;

import cn.hutool.core.date.DateTime;
import com.ruoyi.yishengxin.domain.vipUser.VipExchange;
import com.ruoyi.yishengxin.domain.vipUser.VipExchange;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 个人兑换 数据层
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
@Repository
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

	/**
	 * 查询不同状态的兑换和
	 * @param status
	 * @return
	 */
	@Select("select ifNull(SUM(exchange_money),0) from ysx_vip_exchange where exchange_status=#{status}")
	double selectSumByIfExchage(@Param("status") String status);

	/**
	 * 指定时间段内指定状态的总和
	 * @param status 状态码
	 * @param begin
	 * @param end
	 * @return
	 */
	@Select("select ifNull(SUM(exchange_money),0) from ysx_vip_exchange where exchange_status=#{status} and exchange_time between #{begin} and #{end}")
	double selectSumByIfExchageAndTime(@Param("status") String status,@Param("begin") DateTime begin, @Param("end") DateTime end);
}