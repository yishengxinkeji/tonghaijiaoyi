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

    //邀请链接前缀,后面加上个人的推广码
    public static final String PRE_INVI_LINK = IpUtils.getHostIp() + ":" + Global.getConfig("server.port") + "/"+"front/vipUser/invite/";

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



}
