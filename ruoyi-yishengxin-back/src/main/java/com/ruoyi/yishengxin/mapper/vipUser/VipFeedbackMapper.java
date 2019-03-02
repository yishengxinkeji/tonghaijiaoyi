package com.ruoyi.yishengxin.mapper.vipUser;

import com.ruoyi.yishengxin.domain.vipUser.VipFeedback;
import java.util.List;	

/**
 * 用户反馈 数据层
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
public interface VipFeedbackMapper 
{
	/**
     * 查询用户反馈信息
     * 
     * @param id 用户反馈ID
     * @return 用户反馈信息
     */
	public VipFeedback selectVipFeedbackById(Integer id);
	
	/**
     * 查询用户反馈列表
     * 
     * @param vipFeedback 用户反馈信息
     * @return 用户反馈集合
     */
	public List<VipFeedback> selectVipFeedbackList(VipFeedback vipFeedback);
	
	/**
     * 新增用户反馈
     * 
     * @param vipFeedback 用户反馈信息
     * @return 结果
     */
	public int insertVipFeedback(VipFeedback vipFeedback);
	
	/**
     * 修改用户反馈
     * 
     * @param vipFeedback 用户反馈信息
     * @return 结果
     */
	public int updateVipFeedback(VipFeedback vipFeedback);
	
	/**
     * 删除用户反馈
     * 
     * @param id 用户反馈ID
     * @return 结果
     */
	public int deleteVipFeedbackById(Integer id);
	
	/**
     * 批量删除用户反馈
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipFeedbackByIds(String[] ids);
	
}