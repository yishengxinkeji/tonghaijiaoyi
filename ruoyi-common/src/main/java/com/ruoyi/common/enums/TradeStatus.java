package com.ruoyi.common.enums;

/**
 * 级别
 *
 * @author ruoyi
 */
public enum TradeStatus {
    WAITING("0", "等待交易"),
    TRADING("1", "交易中"),
    SUCCESS("2", "交易成功"),
    FAIL("3", "交易失败"),
    CANCEL("4", "交易取消"),
    WAIT_BUY_SEND("5","等待买家打款"),
    WAIT_SALE_CONFIRM("6","等待卖家确认");


    private final String code;
    private final String info;

    TradeStatus(String code, String info) {
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
