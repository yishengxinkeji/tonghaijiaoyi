package com.ruoyi.yishengxin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.CustomerMapper;
import com.ruoyi.yishengxin.domain.Customer;
import com.ruoyi.yishengxin.service.ICustomerService;
import com.ruoyi.common.support.Convert;

/**
 * 客服设置 服务层实现
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
@Service
public class CustomerServiceImpl implements ICustomerService 
{
	@Autowired
	private CustomerMapper customerMapper;

	/**
     * 查询客服设置信息
     * 
     * @param id 客服设置ID
     * @return 客服设置信息
     */
    @Override
	public Customer selectCustomerById(Integer id)
	{
	    return customerMapper.selectCustomerById(id);
	}
	
	/**
     * 查询客服设置列表
     * 
     * @param customer 客服设置信息
     * @return 客服设置集合
     */
	@Override
	public List<Customer> selectCustomerList(Customer customer)
	{
	    return customerMapper.selectCustomerList(customer);
	}
	
    /**
     * 新增客服设置
     * 
     * @param customer 客服设置信息
     * @return 结果
     */
	@Override
	public int insertCustomer(Customer customer)
	{
	    return customerMapper.insertCustomer(customer);
	}
	
	/**
     * 修改客服设置
     * 
     * @param customer 客服设置信息
     * @return 结果
     */
	@Override
	public int updateCustomer(Customer customer)
	{
	    return customerMapper.updateCustomer(customer);
	}

	/**
     * 删除客服设置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCustomerByIds(String ids)
	{
		return customerMapper.deleteCustomerByIds(Convert.toStrArray(ids));
	}
	
}
