package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.CustomerLog;

import java.util.List;

/**
 * 客服操作会员记录 服务层
 *
 * @author ruoyi
 * @date 2019-03-14
 */
public interface ICustomerLogService {
    /**
     * 查询客服操作会员记录信息
     *
     * @param id 客服操作会员记录ID
     * @return 客服操作会员记录信息
     */
    public CustomerLog selectCustomerLogById(Integer id);

    /**
     * 查询客服操作会员记录列表
     *
     * @param customerLog 客服操作会员记录信息
     * @return 客服操作会员记录集合
     */
    public List<CustomerLog> selectCustomerLogList(CustomerLog customerLog);

    /**
     * 新增客服操作会员记录
     *
     * @param customerLog 客服操作会员记录信息
     * @return 结果
     */
    public int insertCustomerLog(CustomerLog customerLog);

    /**
     * 修改客服操作会员记录
     *
     * @param customerLog 客服操作会员记录信息
     * @return 结果
     */
    public int updateCustomerLog(CustomerLog customerLog);

    /**
     * 删除客服操作会员记录信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomerLogByIds(String ids);

}
