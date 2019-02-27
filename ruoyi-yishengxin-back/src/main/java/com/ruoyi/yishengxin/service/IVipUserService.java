package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.VipUser;
import java.util.List;

/**
 * 会员基本 服务层
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
public interface IVipUserService 
{
	/**
     * 查询会员基本信息
     * 
     * @param id 会员基本ID
     * @return 会员基本信息
     */
	public VipUser selectVipUserById(Integer id);
	
	/**
     * 查询会员基本列表
     * 
     * @param vipUser 会员基本信息
     * @return 会员基本集合
     */
	public List<VipUser> selectVipUserList(VipUser vipUser);
	
	/**
     * 新增会员基本
     * 
     * @param vipUser 会员基本信息
     * @return 结果
     */
	public int insertVipUser(VipUser vipUser);
	
	/**
     * 修改会员基本
     * 
     * @param vipUser 会员基本信息
     * @return 结果
     */
	public int updateVipUser(VipUser vipUser);
		
	/**
     * 删除会员基本信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipUserByIds(String ids);
	
}
