package com.ruoyi.common.constant;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.IpUtils;

/**
 * 自定义常量
 */
public class CustomerConstants {

    /**
     * 是
     */
    public static final String YES = "Y";

    /**
     * 否
     */
    public static final String NO = "N";

    /**
     * 服务器地址
     */
    public static final String SERVER_LINK = IpUtils.getHostIp() + ":" + Global.getConfig("server.port");

    //邀请链接前缀,后面加上个人的推广码
    public static final String PRE_INVI_LINK = SERVER_LINK + "/front/vipUser/invite/";

    /**
     * SSL
     */
    public static final String SSL = "SSL";

    /**
     * HKD
     */
    public static final String HKD = "HKD";

    /**
     * 收益描述模版  给我带来3个SSL资产的收益
     */
    public static final String PROFIT_TEMPLATE = "给我带来: %s 个 %s 资产的收益";

    //用户中心客服中的兑换与购买
    //等待兑换,已完成
    public static final String EXCHANGE_BUY_STATUS_WAIT = "1";
    public static final String EXCHANGE_BUY_STATUS_DEAL = "2";

    //交易的两种币
    public static final String TRADE_TYPE_SSL = "SSL";
    public static final String TRADE_TYPE_HKD = "HKD";

    //redis的任务状态 Y:任务执行中
    public static final String TASK_STATUS_KEY = "ISCANCEL";





}
