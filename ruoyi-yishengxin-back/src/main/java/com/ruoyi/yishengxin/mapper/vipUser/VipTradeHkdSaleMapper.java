package com.ruoyi.yishengxin.mapper.vipUser;

import cn.hutool.core.date.DateTime;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeHkdSale;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 挂卖HKD 数据层
 * 
 * @author ruoyi
 * @date 2019-03-07
 */
@Repository
public interface VipTradeHkdSaleMapper {
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
     * 删除挂卖HKD
     * 
     * @param id 挂卖HKDID
     * @return 结果
     */
	public int deleteVipTradeHkdSaleById(Integer id);
	
	/**
     * 批量删除挂卖HKD
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipTradeHkdSaleByIds(String[] ids);

	/**
	 * 该用户今日总共交易了多少
	 * @param id
	 * @return
	 */
	@Select("select ifnull(sum(sale_number),0) from ysx_vip_trade_hkd_sale where vip_id=#{id} and sale_status <> '4' and to_days(sale_time) = to_days(now())")
	double selectHkdMaxNumberByDay(Integer id);

	/**
	 * 从开始到结束总共交易了多少
	 * @param begin
	 * @param end
	 * @return
	 */
	@Select("select ifNull(sum(sale_number),0) from ysx_vip_trade_hkd_sale where sale_time between #{begin} and #{end}" )
	double selectSum(@Param("begin") DateTime begin, @Param("end") DateTime end);
}