package com.ruoyi.yishengxin.mapper.vipUser;

import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslBuy;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 挂买SSL 数据层
 *
 * @author ruoyi
 * @date 2019-03-06
 */
@Repository
public interface VipTradeSslBuyMapper {
    /**
     * 查询挂买SSL信息
     *
     * @param id 挂买SSLID
     * @return 挂买SSL信息
     */
    public VipTradeSslBuy selectVipTradeBuyById(Integer id);

    /**
     * 查询挂买SSL列表
     *
     * @param vipTradeSslBuy 挂买SSL信息
     * @return 挂买SSL集合
     */
    public List<VipTradeSslBuy> selectVipTradeBuyList(VipTradeSslBuy vipTradeSslBuy);

    /**
     * 新增挂买SSL
     *
     * @param vipTradeSslBuy 挂买SSL信息
     * @return 结果
     */
    public int insertVipTradeBuy(VipTradeSslBuy vipTradeSslBuy);

    /**
     * 修改挂买SSL
     *
     * @param vipTradeSslBuy 挂买SSL信息
     * @return 结果
     */
    public int updateVipTradeBuy(VipTradeSslBuy vipTradeSslBuy);

    /**
     * 删除挂买SSL
     *
     * @param id 挂买SSLID
     * @return 结果
     */
    public int deleteVipTradeBuyById(Integer id);

    /**
     * 批量删除挂买SSL
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteVipTradeBuyByIds(String[] ids);

}