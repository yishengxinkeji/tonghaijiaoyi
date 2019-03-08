package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.Customer;

import java.util.List;

/**
 * 客服设置 服务层
 *
 * @author ruoyi
 * @date 2019-02-26
 */
public interface ICustomerService {
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
     * 删除客服设置信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomerByIds(String ids);

}
