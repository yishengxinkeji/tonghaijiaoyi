package com.ruoyi.yishengxin.mapper.vipUser;

import com.ruoyi.yishengxin.domain.vipUser.VipLock;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * SSL锁仓 数据层
 * 
 * @author ruoyi
 * @date 2019-03-08
 */
@Repository
public interface VipLockMapper {
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
     * 删除SSL锁仓
     * 
     * @param id SSL锁仓ID
     * @return 结果
     */
	public int deleteVipLockById(Integer id);
	
	/**
     * 批量删除SSL锁仓
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipLockByIds(String[] ids);
	
}