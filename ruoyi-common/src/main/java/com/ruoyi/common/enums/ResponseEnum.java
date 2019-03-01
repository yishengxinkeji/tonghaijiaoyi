package com.ruoyi.common.enums;

public enum ResponseEnum {

    SUCCESS("1000","成功"),
    PHONE_ERROR("1001","手机号码不正确"),
    PHONE_EXIST_ERROR("1002","该号码已注册"),
    PHONE_DIFFERENT_ERROR("1003","两次密码不相同"),
    PHONE_NOTEXIST_ERROR("1004","两次密码不相同"),

    VIP_USER_FROZEN("1005","该邀请码对应用户已经被冻结,无法邀请"),
    VIP_PASSWORD_ERROR("1006","密码错误"),
    VIP_GIFT_RECEIVE("1007","礼包已领取"),
    VIP_USER_INVITE("1008","该邀请码不存在"),
    VIP_TOKEN_FAIL("9999","需重新登陆"),



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
