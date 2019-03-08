package com.ruoyi.common.enums;

/**
 * 申诉状态
 *
 * @author ruoyi
 */
public enum AppealType {
    APPEALING("1", "申诉中"),
    SUCCESS("2", "申诉成功"),
    FAIL("3", "申诉失败");



    private final String code;
    private final String info;

    AppealType(String code, String info) {
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
