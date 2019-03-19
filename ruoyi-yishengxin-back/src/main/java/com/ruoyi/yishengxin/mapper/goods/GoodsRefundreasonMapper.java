package com.ruoyi.yishengxin.mapper.goods;

import java.util.List;

import com.ruoyi.yishengxin.domain.goods.GoodsRefundreason;
import org.springframework.stereotype.Repository;

/**
 * 退款原因 数据层
 * 
 * @author ruoyi
 * @date 2019-03-19
 */
@Repository
public interface GoodsRefundreasonMapper {
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
     * 删除退款原因
     * 
     * @param id 退款原因ID
     * @return 结果
     */
	public int deleteGoodsRefundreasonById(Integer id);
	
	/**
     * 批量删除退款原因
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsRefundreasonByIds(String[] ids);
	
}