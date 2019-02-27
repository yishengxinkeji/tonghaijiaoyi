package com.ruoyi.common.enums;

public enum ResponseEnum {

    SUCCESS("1000","成功"),
    PHONE_ERROR("1001","手机号码不正确"),
    PHONE_EXIST_ERROR("1002","该号码已注册"),

    FAIL("500","失败");




    private final String code;
    private final String info;

    ResponseEnum(String code, String info) {
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
