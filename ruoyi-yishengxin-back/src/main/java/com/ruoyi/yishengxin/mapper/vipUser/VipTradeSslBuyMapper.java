package com.ruoyi.yishengxin.mapper.vipUser;

import cn.hutool.core.date.DateTime;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslBuy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    @Select("select ifNull(sum(buy_number),0) from ysx_vip_trade_ssl_buy where buy_status = 2 and buy_time between #{begin} and #{end}" )
    int selectSum(@Param("begin") DateTime begin,@Param("end") DateTime end);

    @Select("select ifNull(avg(unit_price),0) from ysx_vip_trade_ssl_buy where buy_time between #{beginOfDay} and #{endOfDay}")
    double selectAvgByDay(@Param("beginOfDay") DateTime beginOfDay,@Param("endOfDay") DateTime endOfDay);

    /**
     * 按单价分组统计交易中的数量
     * @return
     */
    @Select("select unit_price,sum(buy_number) as buy_number from ysx_vip_trade_ssl_buy where buy_status='1' group by unit_price order by unit_price desc")
    List<Map<String,String>> selectSumNumberByUnitPrice();
}