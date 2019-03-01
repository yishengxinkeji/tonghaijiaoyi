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
    VIP_USER_INVITE("1008","邀请码不存在"),
    VIP_USER_AVATER("1009","头像修改失败"),
    VIP_USER_AVATER_TYPE("1010","头像格式不正确"),

    FILE_NAME_LENGTH("1011","上传文件名过长"),
    FILE_TOO_MAX("1012","文件过大"),

    VIP_TOKEN_FAIL("9999","需重新登陆"),














    GOODS_NULL("2001","数据为空"),
    COODS_COLLECTION_PARAMETER("2002","传参有误"),
    COODS_COLLECTION_ADD("2003","收藏添加失败"),
    COODS_COLLECTION_DELECT("2004","收藏删除失败"),
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
