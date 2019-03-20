package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.vipUser.VipLock;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;

import java.util.List;

/**
 * SSL锁仓 服务层
 * 
 * @author ruoyi
 * @date 2019-03-08
 */
public interface IVipLockService 
{
	/**
     * 查询SSL锁仓信息
     * 
     * @param id SSL锁仓ID
     * @return SSL锁仓信息
     */
	public VipLock selectVipLockById(Integer id);
	
	/**
     * 查询SSL锁仓列表
     * 
     * @param vipLock SSL锁仓信息
     * @return SSL锁仓集合
     */
	public List<VipLock> selectVipLockList(VipLock vipLock);
	
	/**
     * 新增SSL锁仓
     * 
     * @param vipLock SSL锁仓信息
     * @return 结果
     */
	public int insertVipLock(VipLock vipLock);
	
	/**
     * 修改SSL锁仓
     * 
     * @param vipLock SSL锁仓信息
     * @return 结果
     */
	public int updateVipLock(VipLock vipLock);
		
	/**
     * 删除SSL锁仓信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipLockByIds(String ids);

    int vipLock(VipUser vipUser, VipLock vipLock) throws Exception;

	int interuptLock(VipUser vipUser, VipLock vipLock);

	//每天的锁仓到期信息
    void updateTimerLock();
}
