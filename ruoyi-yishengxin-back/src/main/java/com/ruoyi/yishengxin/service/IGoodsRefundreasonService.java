package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.goods.GoodsRefundreason;
import java.util.List;

/**
 * 退款原因 服务层
 * 
 * @author ruoyi
 * @date 2019-03-19
 */
public interface IGoodsRefundreasonService 
{
	/**
     * 查询退款原因信息
     * 
     * @param id 退款原因ID
     * @return 退款原因信息
     */
	public GoodsRefundreason selectGoodsRefundreasonById(Integer id);
	
	/**
     * 查询退款原因列表
     * 
     * @param goodsRefundreason 退款原因信息
     * @return 退款原因集合
     */
	public List<GoodsRefundreason> selectGoodsRefundreasonList(GoodsRefundreason goodsRefundreason);
	
	/**
     * 新增退款原因
     * 
     * @param goodsRefundreason 退款原因信息
     * @return 结果
     */
	public int insertGoodsRefundreason(GoodsRefundreason goodsRefundreason);
	
	/**
     * 修改退款原因
     * 
     * @param goodsRefundreason 退款原因信息
     * @return 结果
     */
	public int updateGoodsRefundreason(GoodsRefundreason goodsRefundreason);
		
	/**
     * 删除退款原因信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsRefundreasonByIds(String ids);
	
}
