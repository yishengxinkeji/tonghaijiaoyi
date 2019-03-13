package com.ruoyi.yishengxin.mapper;

import com.ruoyi.yishengxin.domain.RollMessage;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 滚动消息 数据层
 * 
 * @author ruoyi
 * @date 2019-03-13
 */
@Repository
public interface RollMessageMapper {
	/**
     * 查询滚动消息信息
     * 
     * @param id 滚动消息ID
     * @return 滚动消息信息
     */
	public RollMessage selectRollMessageById(Integer id);
	
	/**
     * 查询滚动消息列表
     * 
     * @param rollMessage 滚动消息信息
     * @return 滚动消息集合
     */
	public List<RollMessage> selectRollMessageList(RollMessage rollMessage);
	
	/**
     * 新增滚动消息
     * 
     * @param rollMessage 滚动消息信息
     * @return 结果
     */
	public int insertRollMessage(RollMessage rollMessage);
	
	/**
     * 修改滚动消息
     * 
     * @param rollMessage 滚动消息信息
     * @return 结果
     */
	public int updateRollMessage(RollMessage rollMessage);
	
	/**
     * 删除滚动消息
     * 
     * @param id 滚动消息ID
     * @return 结果
     */
	public int deleteRollMessageById(Integer id);
	
	/**
     * 批量删除滚动消息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRollMessageByIds(String[] ids);
	
}