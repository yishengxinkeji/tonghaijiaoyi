package com.ruoyi.yishengxin.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

import java.util.Date;

/**
 * 交易设置表 ysx_trade
 *
 * @author ruoyi
 * @date 2019-03-13
 */
@Data
@ToString
@Getter
@Setter
public class Trade extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;
    /**
     * 跌幅谷值
     */
    private String low;
    /**
     * 涨幅峰值
     */
    private String high;
    /**
     * 每天ssl最大互转量
     */
    private String maxSslDeliverDay;
    /**
     * 每笔ssl最低互转量
     */
    private String minSslDeliverTime;
    /**
     * 每天ssl最大交易量
     */
    private String maxSslTradeDay;
    /**
     * 每笔ssl最大交易额
     */
    private String maxSslTradeTime;
    /**
     * 卖HKD手续费
     */
    private String hkdCharge;
    /**
     * 卖SSL手续费
     */
    private String sslCharge;
    /**
     * 锁仓最低值
     */
    private String minLockPosition;
    /**
     * 锁仓整数倍限制
     */
    private String lockMultipleNumber;
    /**
     * 中途中断锁仓手续费
     */
    private String lockBreakCharge;
    /**
     * 1个月利率
     */
    private String oneRate;
    /**
     * 3个月利率
     */
    private String threeRate;
    /**
     * 6个月利率
     */
    private String sixRate;
    /**
     * 12个月利率
     */
    private String twelveRate;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 每天hdk最大互转量
     */
    private String maxHdkDeliverDay;
    /**
     * 每次hdk最低互转量
     */
    private String minHdkDeliverTime;
    /**
     * 每次HDK最大交易量
     */
    private String maxHdkTradeTime;
    /**
     * 每天hdk最大交易量
     */
    private String maxHdkTradeDay;
    /**
     * 交易失败卖家责任扣除手续费
     */
    private String saleDutyCharge;


}
