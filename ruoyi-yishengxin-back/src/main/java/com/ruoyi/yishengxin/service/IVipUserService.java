package com.ruoyi.yishengxin.service;

import cn.hutool.core.date.DateTime;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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

	/**
	 * 新人领取礼包
	 * @param vipUser
	 */
	public void newReceiveGift(VipUser vipUser,String giftType,String giftNumber);


	/**
	 * 根据父级用户查找下一级
	 */
	List<VipUser> selectUserByParentCode(VipUser oneUser);

	/**
	 * 根据父级用户查询第二级会员
	 * @param oneUser
	 * @return
	 */
	List<VipUser> selectUserByGrandParentCode(VipUser oneUser);

	/**
	 * 转出
	 *
	 * @param vip
	 * @param myVip
	 * @param type
	 * @param number
	 * @param tranMoney
	 * @return
	 */
    int tranSport(VipUser vip, VipUser myVip, String type, String number, double tranMoney) throws Exception;



    int selectCount(VipUser vipUser);


	/**
	 * 根据手机号查询用户
	 * @param phone
	 * @return
	 */
	public VipUser selectUserByPhone(String phone);
}
