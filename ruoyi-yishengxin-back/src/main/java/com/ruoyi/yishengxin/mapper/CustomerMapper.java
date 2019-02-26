package com.ruoyi.yishengxin.mapper;

import com.ruoyi.yishengxin.domain.Customer;
import java.util.List;	

/**
 * 客服设置 数据层
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
public interface CustomerMapper 
{
	/**
     * 查询客服设置信息
     * 
     * @param id 客服设置ID
     * @return 客服设置信息
     */
	public Customer selectCustomerById(Integer id);
	
	/**
     * 查询客服设置列表
     * 
     * @param customer 客服设置信息
     * @return 客服设置集合
     */
	public List<Customer> selectCustomerList(Customer customer);
	
	/**
     * 新增客服设置
     * 
     * @param customer 客服设置信息
     * @return 结果
     */
	public int insertCustomer(Customer customer);
	
	/**
     * 修改客服设置
     * 
     * @param customer 客服设置信息
     * @return 结果
     */
	public int updateCustomer(Customer customer);
	
	/**
     * 删除客服设置
     * 
     * @param id 客服设置ID
     * @return 结果
     */
	public int deleteCustomerById(Integer id);
	
	/**
     * 批量删除客服设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCustomerByIds(String[] ids);
	
}