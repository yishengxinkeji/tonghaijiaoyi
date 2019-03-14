package com.ruoyi.yishengxin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.CustomerLogMapper;
import com.ruoyi.yishengxin.domain.CustomerLog;
import com.ruoyi.yishengxin.service.ICustomerLogService;
import com.ruoyi.common.support.Convert;

/**
 * 客服操作会员记录 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-14
 */
@Service
public class CustomerLogServiceImpl implements ICustomerLogService 
{
	@Autowired
	private CustomerLogMapper customerLogMapper;

	/**
     * 查询客服操作会员记录信息
     * 
     * @param id 客服操作会员记录ID
     * @return 客服操作会员记录信息
     */
    @Override
	public CustomerLog selectCustomerLogById(Integer id)
	{
	    return customerLogMapper.selectCustomerLogById(id);
	}
	
	/**
     * 查询客服操作会员记录列表
     * 
     * @param customerLog 客服操作会员记录信息
     * @return 客服操作会员记录集合
     */
	@Override
	public List<CustomerLog> selectCustomerLogList(CustomerLog customerLog)
	{
	    return customerLogMapper.selectCustomerLogList(customerLog);
	}
	
    /**
     * 新增客服操作会员记录
     * 
     * @param customerLog 客服操作会员记录信息
     * @return 结果
     */
	@Override
	public int insertCustomerLog(CustomerLog customerLog)
	{
	    return customerLogMapper.insertCustomerLog(customerLog);
	}
	
	/**
     * 修改客服操作会员记录
     * 
     * @param customerLog 客服操作会员记录信息
     * @return 结果
     */
	@Override
	public int updateCustomerLog(CustomerLog customerLog)
	{
	    return customerLogMapper.updateCustomerLog(customerLog);
	}

	/**
     * 删除客服操作会员记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCustomerLogByIds(String ids)
	{
		return customerLogMapper.deleteCustomerLogByIds(Convert.toStrArray(ids));
	}
	
}
