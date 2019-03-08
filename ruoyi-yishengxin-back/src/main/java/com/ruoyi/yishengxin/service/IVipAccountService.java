package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.vipUser.VipAccount;
import java.util.List;

/**
 * 收款账户 服务层
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
public interface IVipAccountService 
{
	/**
     * 查询收款账户信息
     * 
     * @param id 收款账户ID
     * @return 收款账户信息
     */
	public VipAccount selectVipAccountById(Integer id);
	
	/**
     * 查询收款账户列表
     * 
     * @param vipAccount 收款账户信息
     * @return 收款账户集合
     */
	public List<VipAccount> selectVipAccountList(VipAccount vipAccount);
	
	/**
     * 新增收款账户
     * 
     * @param vipAccount 收款账户信息
     * @return 结果
     */
	public int insertVipAccount(VipAccount vipAccount);
	
	/**
     * 修改收款账户
     * 
     * @param vipAccount 收款账户信息
     * @return 结果
     */
	public int updateVipAccount(VipAccount vipAccount);
		
	/**
     * 删除收款账户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipAccountByIds(String ids);

	/**
	 * 将该用户的所有地址改为非默认
	 * @param vipId
	 */
	int updateDefaultAccount(Integer vipId,Integer id);
	
}
