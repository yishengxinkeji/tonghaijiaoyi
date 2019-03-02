package com.ruoyi.yishengxin.mapper.vipUser;

import com.ruoyi.yishengxin.domain.vipUser.VipAboutus;
import java.util.List;	

/**
 * 关于我们 数据层
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
public interface VipAboutusMapper 
{
	/**
     * 查询关于我们信息
     * 
     * @param id 关于我们ID
     * @return 关于我们信息
     */
	public VipAboutus selectVipAboutusById(Integer id);
	
	/**
     * 查询关于我们列表
     * 
     * @param vipAboutus 关于我们信息
     * @return 关于我们集合
     */
	public List<VipAboutus> selectVipAboutusList(VipAboutus vipAboutus);
	
	/**
     * 新增关于我们
     * 
     * @param vipAboutus 关于我们信息
     * @return 结果
     */
	public int insertVipAboutus(VipAboutus vipAboutus);
	
	/**
     * 修改关于我们
     * 
     * @param vipAboutus 关于我们信息
     * @return 结果
     */
	public int updateVipAboutus(VipAboutus vipAboutus);
	
	/**
     * 删除关于我们
     * 
     * @param id 关于我们ID
     * @return 结果
     */
	public int deleteVipAboutusById(Integer id);
	
	/**
     * 批量删除关于我们
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipAboutusByIds(String[] ids);
	
}