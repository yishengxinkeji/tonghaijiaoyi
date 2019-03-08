package com.ruoyi.common.enums;

/**
 * 交易级别
 *
 * @author ruoyi
 */
public enum TradeType {
    BUY("1", "购买商品"),
    SALE_HKD("2", "挂卖HKD"),
    BUY_HKD("3", "挂买HKD"),
    IN_HKD("4", "转入HKD"),
    OUT_HKD("5", "转出HKD"),
    SALE_SSL("6", "挂卖SSL"),
    BUY_SSL("7", "挂买SSL"),
    IN_SSL("8", "转入SSL"),
    OUT_SSL("9", "转出SSL"),
    BACK_EXCHANGE("10", "平台交换"),
    BACK_BUY("11", "平台购买");

    private final String code;
    private final String info;

    TradeType(String code, String info) {
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
