package com.ruoyi.yishengxin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipAccountMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipAccount;
import com.ruoyi.yishengxin.service.IVipAccountService;
import com.ruoyi.common.support.Convert;

/**
 * 收款账户 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
@Service
public class VipAccountServiceImpl implements IVipAccountService 
{
	@Autowired
	private VipAccountMapper vipAccountMapper;

	/**
     * 查询收款账户信息
     * 
     * @param id 收款账户ID
     * @return 收款账户信息
     */
    @Override
	public VipAccount selectVipAccountById(Integer id)
	{
	    return vipAccountMapper.selectVipAccountById(id);
	}
	
	/**
     * 查询收款账户列表
     * 
     * @param vipAccount 收款账户信息
     * @return 收款账户集合
     */
	@Override
	public List<VipAccount> selectVipAccountList(VipAccount vipAccount)
	{
	    return vipAccountMapper.selectVipAccountList(vipAccount);
	}
	
    /**
     * 新增收款账户
     * 
     * @param vipAccount 收款账户信息
     * @return 结果
     */
	@Override
	public int insertVipAccount(VipAccount vipAccount)
	{
	    return vipAccountMapper.insertVipAccount(vipAccount);
	}
	
	/**
     * 修改收款账户
     * 
     * @param vipAccount 收款账户信息
     * @return 结果
     */
	@Override
	public int updateVipAccount(VipAccount vipAccount)
	{
	    return vipAccountMapper.updateVipAccount(vipAccount);
	}

	/**
     * 删除收款账户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVipAccountByIds(String ids)
	{
		return vipAccountMapper.deleteVipAccountByIds(Convert.toStrArray(ids));
	}

	/**
	 * 将账户改为非默认
	 * @param vipId
	 */
	@Override
	public void updateDefaultAccount(Integer vipId) {
		vipAccountMapper.updateDefaultAccount(vipId);
	}
}
