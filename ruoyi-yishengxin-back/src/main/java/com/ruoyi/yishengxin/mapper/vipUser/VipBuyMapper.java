package com.ruoyi.yishengxin.mapper.vipUser;

import cn.hutool.core.date.DateTime;
import com.ruoyi.yishengxin.domain.vipUser.VipBuy;
import com.ruoyi.yishengxin.domain.vipUser.VipBuy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 个人购买 数据层
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
public interface VipBuyMapper 
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
     * 删除个人购买
     * 
     * @param id 个人购买ID
     * @return 结果
     */
	public int deleteVipBuyById(Integer id);
	
	/**
     * 批量删除个人购买
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipBuyByIds(String[] ids);

	/**
	 * 所有已购买成功的金额总和
	 * @return
	 */
	@Select("select ifNull(SUM(buy_money),0) from ysx_vip_buy where buy_status=2")
    double selectSum();

	@Select("select ifNull(SUM(buy_money),0) from ysx_vip_buy where buy_status=#{status} and create_time between #{begin} and #{end}")
	double selectSumByTime(@Param("status") String status,@Param("begin") DateTime begin,@Param("end") DateTime end);
}