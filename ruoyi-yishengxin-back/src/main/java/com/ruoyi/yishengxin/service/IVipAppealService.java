package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.vipUser.VipAppeal;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;

import java.util.List;

/**
 * 我的申诉 服务层
 * 
 * @author ruoyi
 * @date 2019-03-13
 */
public interface IVipAppealService 
{
	/**
     * 查询我的申诉信息
     * 
     * @param id 我的申诉ID
     * @return 我的申诉信息
     */
	public VipAppeal selectVipAppealById(Integer id);
	
	/**
     * 查询我的申诉列表
     * 
     * @param vipAppeal 我的申诉信息
     * @return 我的申诉集合
     */
	public List<VipAppeal> selectVipAppealList(VipAppeal vipAppeal);
	
	/**
     * 新增我的申诉
     * 
     * @param vipAppeal 我的申诉信息
     * @return 结果
     */
	public int insertVipAppeal(VipAppeal vipAppeal);
	
	/**
     * 修改我的申诉
     * 
     * @param vipAppeal 我的申诉信息
     * @return 结果
     */
	public int updateVipAppeal(VipAppeal vipAppeal);
		
	/**
     * 删除我的申诉信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipAppealByIds(String ids);
	//申诉
	int appeal(VipUser vipUser, VipAppeal vipAppeal) throws Exception;

	/**
	 * 处理客户申诉
	 * @param vipAppeal
	 * @return
	 */
    int dealAppeal(VipAppeal vipAppeal);
}
