package com.ruoyi.yishengxin.mapper.vipUser;

import cn.hutool.core.date.DateTime;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 会员基本 数据层
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
@Repository
public interface VipUserMapper 
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
     * 删除会员基本
     * 
     * @param id 会员基本ID
     * @return 结果
     */
	public int deleteVipUserById(Integer id);
	
	/**
     * 批量删除会员基本
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipUserByIds(String[] ids);

	/**
	 * 查询用户下的一级会员
	 * @param oneUser
	 * @return
	 */
    List<VipUser> selectUserByParentCode(VipUser oneUser);

	/**
	 * 查询用户的二级会员
	 * @param oneUser
	 * @return
	 */
	List<VipUser> selectUserByGrandParentCode(VipUser oneUser);


    public int selectCount(VipUser vipUser);


	/**
	 * 根据手机号查询用户
	 * @param phone
	 * @return
	 */

	@Select("select * from ysx_vip_user where phone = #{phone}")
	public VipUser selectUserByPhone(String phone);

}