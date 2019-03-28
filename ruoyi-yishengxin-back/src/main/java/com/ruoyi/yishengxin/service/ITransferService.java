package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.Transfer;
import java.util.List;

/**
 * 转入转出说明 服务层
 * 
 * @author ruoyi
 * @date 2019-03-28
 */
public interface ITransferService 
{
	/**
     * 查询转入转出说明信息
     * 
     * @param id 转入转出说明ID
     * @return 转入转出说明信息
     */
	public Transfer selectTransferById(Integer id);
	
	/**
     * 查询转入转出说明列表
     * 
     * @param transfer 转入转出说明信息
     * @return 转入转出说明集合
     */
	public List<Transfer> selectTransferList(Transfer transfer);
	
	/**
     * 新增转入转出说明
     * 
     * @param transfer 转入转出说明信息
     * @return 结果
     */
	public int insertTransfer(Transfer transfer);
	
	/**
     * 修改转入转出说明
     * 
     * @param transfer 转入转出说明信息
     * @return 结果
     */
	public int updateTransfer(Transfer transfer);
		
	/**
     * 删除转入转出说明信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTransferByIds(String ids);
	
}
