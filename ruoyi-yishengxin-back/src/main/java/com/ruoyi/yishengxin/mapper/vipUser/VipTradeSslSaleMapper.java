package com.ruoyi.yishengxin.mapper.vipUser;

import cn.hutool.core.date.DateTime;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslSale;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
    @Select("select ifnull(sum(sale_number),0) from ysx_vip_trade_ssl_sale where vip_id=#{id} and to_days(sale_time) = to_days(now())")
    public double selectSslMaxNumberByDay(Integer id);

    @Select("select unit_price as number,sale_time as time from ysx_vip_trade_ssl_sale where sale_time between #{begin} and #{end} and sale_status=2 order by sale_time asc")
    public List<Map<String,String>> selectSale(@Param("begin") DateTime begin, @Param("end") DateTime end);

    @Select("select ifNull(sum(charge_money),0) from ysx_vip_trade_ssl_sale where sale_time between #{begin} and #{end}" )
    int selectSum(@Param("begin") DateTime begin,@Param("end") DateTime end);

    /**
     * 查询当天最近两条交易成功记录
     * @return
     */
    @Select("SELECT * FROM ysx_vip_trade_ssl_sale where sale_status='2' and  to_days(sale_time)=to_days(now()) order by id DESC limit 2")
    List<Map<String,String>>  selectTwoLeast();
    @Select("select * from ysx_vip_trade_ssl_sale where ID=(select MAX(ID) from ysx_vip_trade_ssl_sale)")
    Map<String,String> selecByMaxId();
}