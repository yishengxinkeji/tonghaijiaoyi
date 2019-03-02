package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.vipUser.VipAddress;
import java.util.List;

/**
 * 会员地址 服务层
 * 
 * @author ruoyi
 * @date 2019-02-27
 */
public interface IVipAddressService 
{
	/**
     * 查询会员地址信息
     * 
     * @param id 会员地址ID
     * @return 会员地址信息
     */
	public VipAddress selectVipAddressById(Integer id);
	
	/**
     * 查询会员地址列表
     * 
     * @param vipAddress 会员地址信息
     * @return 会员地址集合
     */
	public List<VipAddress> selectVipAddressList(VipAddress vipAddress);
	
	/**
     * 新增会员地址
     * 
     * @param vipAddress 会员地址信息
     * @return 结果
     */
	public int insertVipAddress(VipAddress vipAddress);
	
	/**
     * 修改会员地址
     * 
     * @param vipAddress 会员地址信息
     * @return 结果
     */
	public int updateVipAddress(VipAddress vipAddress);
		
	/**
     * 删除会员地址信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipAddressByIds(String ids);

	/**
	 * 将该用户的所有地址改为非默认
	 * @param vipId
	 */
    void updateDefaultAddress(Integer vipId);
}
