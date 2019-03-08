package com.ruoyi.yishengxin.mapper.vipUser;

import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslSale;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 挂卖SSL 数据层
 *
 * @author ruoyi
 * @date 2019-03-06
 */
@Repository
public interface VipTradeSslSaleMapper {
    /**
     * 查询挂卖SSL信息
     *
     * @param id 挂卖SSLID
     * @return 挂卖SSL信息
     */
    public VipTradeSslSale selectVipTradeSaleById(Integer id);

    /**
     * 查询挂卖SSL列表
     *
     * @param vipTradeSslSale 挂卖SSL信息
     * @return 挂卖SSL集合
     */
    public List<VipTradeSslSale> selectVipTradeSaleList(VipTradeSslSale vipTradeSslSale);

    /**
     * 新增挂卖SSL
     *
     * @param vipTradeSslSale 挂卖SSL信息
     * @return 结果
     */
    public int insertVipTradeSale(VipTradeSslSale vipTradeSslSale);

    /**
     * 修改挂卖SSL
     *
     * @param vipTradeSslSale 挂卖SSL信息
     * @return 结果
     */
    public int updateVipTradeSale(VipTradeSslSale vipTradeSslSale);

    /**
     * 删除挂卖SSL
     *
     * @param id 挂卖SSLID
     * @return 结果
     */
    public int deleteVipTradeSaleById(Integer id);

    /**
     * 批量删除挂卖SSL
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteVipTradeSaleByIds(String[] ids);

    /**
     * 查询该用户今天交易了多少ssl
     *
     * @param id
     * @return
     */
    @Select("select ifnull(sum(sale_number),0) from ysx_vip_trade_ssl_sale where to_days(sale_time) <= to_days(now())")
    public double selectSslMaxNumberByDay(Integer id);

}