package com.ruoyi.common.enums;

/**
 * 锁仓时间段
 *
 * @author ruoyi
 */
public enum LockType {
    ONE("1", "一个月"),
    THREE("3", "三个月"),
    SIX("6", "六个月"),
    TWELVE("12", "十二个月");


    private final String code;
    private final String info;

    LockType(String code, String info) {
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
