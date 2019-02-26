package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.Gift;
import java.util.List;

/**
 * 礼包设置 服务层
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
public interface IGiftService 
{
	/**
     * 查询礼包设置信息
     * 
     * @param id 礼包设置ID
     * @return 礼包设置信息
     */
	public Gift selectGiftById(Integer id);
	
	/**
     * 查询礼包设置列表
     * 
     * @param gift 礼包设置信息
     * @return 礼包设置集合
     */
	public List<Gift> selectGiftList(Gift gift);
	
	/**
     * 新增礼包设置
     * 
     * @param gift 礼包设置信息
     * @return 结果
     */
	public int insertGift(Gift gift);
	
	/**
     * 修改礼包设置
     * 
     * @param gift 礼包设置信息
     * @return 结果
     */
	public int updateGift(Gift gift);
		
	/**
     * 删除礼包设置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGiftByIds(String ids);
	
}
