package com.ruoyi.yishengxin.mapper.vipUser;

import com.ruoyi.yishengxin.domain.vipUser.VipAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收款账户 数据层
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
@Repository
public interface VipAccountMapper 
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
     * 删除收款账户
     * 
     * @param id 收款账户ID
     * @return 结果
     */
	public int deleteVipAccountById(Integer id);
	
	/**
     * 批量删除收款账户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipAccountByIds(String[] ids);

	/**
	 * 将该用户账户改为非默认
	 * @param vipId
	 */
	int updateDefaultAccount(Integer vipId);
}