package com.ruoyi.common.enums;

/**
 * 交易级别
 *
 * @author ruoyi
 */
public enum CustomerLogType {
    RECHARGE("1", "充值"),
    REDUCE("2", "扣除");

    private final String code;
    private final String info;

    CustomerLogType(String code, String info) {
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
