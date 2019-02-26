package com.ruoyi.yishengxin.mapper;

import com.ruoyi.yishengxin.domain.PullMessage;
import java.util.List;	

/**
 * 推送消息 数据层
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
public interface PullMessageMapper 
{
	/**
     * 查询推送消息信息
     * 
     * @param id 推送消息ID
     * @return 推送消息信息
     */
	public PullMessage selectPullMessageById(Integer id);
	
	/**
     * 查询推送消息列表
     * 
     * @param pullMessage 推送消息信息
     * @return 推送消息集合
     */
	public List<PullMessage> selectPullMessageList(PullMessage pullMessage);
	
	/**
     * 新增推送消息
     * 
     * @param pullMessage 推送消息信息
     * @return 结果
     */
	public int insertPullMessage(PullMessage pullMessage);
	
	/**
     * 修改推送消息
     * 
     * @param pullMessage 推送消息信息
     * @return 结果
     */
	public int updatePullMessage(PullMessage pullMessage);
	
	/**
     * 删除推送消息
     * 
     * @param id 推送消息ID
     * @return 结果
     */
	public int deletePullMessageById(Integer id);
	
	/**
     * 批量删除推送消息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deletePullMessageByIds(String[] ids);
	
}