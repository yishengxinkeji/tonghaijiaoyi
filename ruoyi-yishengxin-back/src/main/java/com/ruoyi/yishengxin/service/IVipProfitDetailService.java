package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.vipUser.VipProfitDetail;
import java.util.List;

/**
 * 会员收益明细 服务层
 * 
 * @author ruoyi
 * @date 2019-02-28
 */
public interface IVipProfitDetailService 
{
	/**
     * 查询会员收益明细信息
     * 
     * @param id 会员收益明细ID
     * @return 会员收益明细信息
     */
	public VipProfitDetail selectVipProfitDetailById(Integer id);
	
	/**
     * 查询会员收益明细列表
     * 
     * @param vipProfitDetail 会员收益明细信息
     * @return 会员收益明细集合
     */
	public List<VipProfitDetail> selectVipProfitDetailList(VipProfitDetail vipProfitDetail);
	
	/**
     * 新增会员收益明细
     * 
     * @param vipProfitDetail 会员收益明细信息
     * @return 结果
     */
	public int insertVipProfitDetail(VipProfitDetail vipProfitDetail);
	
	/**
     * 修改会员收益明细
     * 
     * @param vipProfitDetail 会员收益明细信息
     * @return 结果
     */
	public int updateVipProfitDetail(VipProfitDetail vipProfitDetail);
		
	/**
     * 删除会员收益明细信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVipProfitDetailByIds(String ids);
	
}
