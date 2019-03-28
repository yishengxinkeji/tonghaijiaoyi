package com.ruoyi.common.enums;

/**
 * 交易级别
 *
 * @author ruoyi
 */
public enum CustomerType {
    PLAT("1", "平台客服"),
    EXCHANGE("2", "兑换区客服"),
    BUY("3", "购买区客服");

    private final String code;
    private final String info;

    CustomerType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
