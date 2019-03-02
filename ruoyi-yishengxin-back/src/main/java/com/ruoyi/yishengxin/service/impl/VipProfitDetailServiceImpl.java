package com.ruoyi.yishengxin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipProfitDetailMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipProfitDetail;
import com.ruoyi.yishengxin.service.IVipProfitDetailService;
import com.ruoyi.common.support.Convert;

/**
 * 会员收益明细 服务层实现
 * 
 * @author ruoyi
 * @date 2019-02-28
 */
@Service
public class VipProfitDetailServiceImpl implements IVipProfitDetailService 
{
	@Autowired
	private VipProfitDetailMapper vipProfitDetailMapper;

	/**
     * 查询会员收益明细信息
     * 
     * @param id 会员收益明细ID
     * @return 会员收益明细信息
     */
    @Override
	public VipProfitDetail selectVipProfitDetailById(Integer id)
	{
	    return vipProfitDetailMapper.selectVipProfitDetailById(id);
	}
	
	/**
     * 查询会员收益明细列表
     * 
     * @param vipProfitDetail 会员收益明细信息
     * @return 会员收益明细集合
     */
	@Override
	public List<VipProfitDetail> selectVipProfitDetailList(VipProfitDetail vipProfitDetail)
	{
	    return vipProfitDetailMapper.selectVipProfitDetailList(vipProfitDetail);
	}
	
    /**
     * 新增会员收益明细
     * 
     * @param vipProfitDetail 会员收益明细信息
     * @return 结果
     */
	@Override
	public int insertVipProfitDetail(VipProfitDetail vipProfitDetail)
	{
	    return vipProfitDetailMapper.insertVipProfitDetail(vipProfitDetail);
	}
	
	/**
     * 修改会员收益明细
     * 
     * @param vipProfitDetail 会员收益明细信息
     * @return 结果
     */
	@Override
	public int updateVipProfitDetail(VipProfitDetail vipProfitDetail)
	{
	    return vipProfitDetailMapper.updateVipProfitDetail(vipProfitDetail);
	}

	/**
     * 删除会员收益明细对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVipProfitDetailByIds(String ids)
	{
		return vipProfitDetailMapper.deleteVipProfitDetailByIds(Convert.toStrArray(ids));
	}
	
}
