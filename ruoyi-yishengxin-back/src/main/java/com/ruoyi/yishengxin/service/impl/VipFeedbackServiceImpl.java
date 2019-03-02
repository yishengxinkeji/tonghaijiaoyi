package com.ruoyi.yishengxin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipFeedbackMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipFeedback;
import com.ruoyi.yishengxin.service.IVipFeedbackService;
import com.ruoyi.common.support.Convert;

/**
 * 用户反馈 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
@Service
public class VipFeedbackServiceImpl implements IVipFeedbackService 
{
	@Autowired
	private VipFeedbackMapper vipFeedbackMapper;

	/**
     * 查询用户反馈信息
     * 
     * @param id 用户反馈ID
     * @return 用户反馈信息
     */
    @Override
	public VipFeedback selectVipFeedbackById(Integer id)
	{
	    return vipFeedbackMapper.selectVipFeedbackById(id);
	}
	
	/**
     * 查询用户反馈列表
     * 
     * @param vipFeedback 用户反馈信息
     * @return 用户反馈集合
     */
	@Override
	public List<VipFeedback> selectVipFeedbackList(VipFeedback vipFeedback)
	{
	    return vipFeedbackMapper.selectVipFeedbackList(vipFeedback);
	}
	
    /**
     * 新增用户反馈
     * 
     * @param vipFeedback 用户反馈信息
     * @return 结果
     */
	@Override
	public int insertVipFeedback(VipFeedback vipFeedback)
	{
	    return vipFeedbackMapper.insertVipFeedback(vipFeedback);
	}
	
	/**
     * 修改用户反馈
     * 
     * @param vipFeedback 用户反馈信息
     * @return 结果
     */
	@Override
	public int updateVipFeedback(VipFeedback vipFeedback)
	{
	    return vipFeedbackMapper.updateVipFeedback(vipFeedback);
	}

	/**
     * 删除用户反馈对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVipFeedbackByIds(String ids)
	{
		return vipFeedbackMapper.deleteVipFeedbackByIds(Convert.toStrArray(ids));
	}
	
}
