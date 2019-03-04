package com.ruoyi.common.enums;

/**
 * 级别
 *
 * @author ruoyi
 */
public enum ProfitType {
    SELF("0", "自己"), ONE("1", "一级会员"), TWO("2", "二级会员");

    private final String code;
    private final String info;

    ProfitType(String code, String info) {
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
