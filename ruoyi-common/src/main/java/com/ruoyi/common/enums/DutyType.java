package com.ruoyi.common.enums;

/**
 * 交易失败责任归属
 *
 * @author ruoyi
 */
public enum DutyType {
    NOBODY("1", "无"), SALER("2", "卖家"), BUY("3", "买家");

    private final String code;
    private final String info;

    DutyType(String code, String info) {
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
