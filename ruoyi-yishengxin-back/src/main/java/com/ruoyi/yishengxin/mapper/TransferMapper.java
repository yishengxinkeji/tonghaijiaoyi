package com.ruoyi.yishengxin.mapper;

import com.ruoyi.yishengxin.domain.Transfer;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 转入转出说明 数据层
 * 
 * @author ruoyi
 * @date 2019-03-28
 */
@Repository
public interface TransferMapper {
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
     * 删除转入转出说明
     * 
     * @param id 转入转出说明ID
     * @return 结果
     */
	public int deleteTransferById(Integer id);
	
	/**
     * 批量删除转入转出说明
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTransferByIds(String[] ids);
	
}