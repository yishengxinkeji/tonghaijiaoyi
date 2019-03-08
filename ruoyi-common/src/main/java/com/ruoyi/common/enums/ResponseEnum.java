package com.ruoyi.common.enums;

public enum ResponseEnum {

    SUCCESS("1000","成功"),
    PHONE_ERROR("1001","手机号码不正确"),
    PHONE_EXIST_ERROR("1002","该号码已注册"),
    PHONE_DIFFERENT_ERROR("1003","两次密码不相同"),
    PHONE_NOTEXIST_ERROR("1004","未找到该用户"),
    VIP_USER_FROZEN("1005","该邀请码对应用户已经被冻结,无法邀请"),
    VIP_PASSWORD_ERROR("1006","密码错误"),
    VIP_GIFT_RECEIVE("1007","礼包已领取"),
    VIP_USER_INVITE("1008","邀请码不存在"),
    VIP_USER_AVATER("1009","头像修改失败"),
    VIP_USER_AVATER_TYPE("1010","头像格式不正确"),
    VIP_USER_ADDADDRESS("1099","添加收货地址错误"),
    VIP_USER_EDITADDRESS("1098","修改收货地址错误"),
    VIP_USER_REMOVEADDRESS("1097","删除收货地址错误"),
    VIP_USER_NULLDEFAULTADDRESS("1096","没有默认收货地址错误"),
    VIP_USER_NULL("1095","用户不存在"),
    VIP_USER_TRADPASSWORDERROR("1094","支付密码错误"),
    VIP_USER_SSLINSUFFICIENT("1093","SSL币不足"),
    VIP_USER_TRADPUPLOADSSLERROR("1092","支付修改ssl错误"),




    FILE_NAME_LENGTH("1011","上传文件名过长"),
    FILE_TOO_MAX("1012","文件过大"),
    FILE_UPLOAD_TYPE("1013","缺失文件类型,无法处理"),

    NUMBER_TRANCT_ERROR("1014","填写正确的数字"),
    NUMBER_TOO_MAX("1015","数字太大"),
    NUMBER_TOO_LOSS("1016","数字为负数"),

    VIP_TOKEN_FAIL("9999","需重新登陆"),


    GOODS_NULL("2001","数据为空"),
    COODS_COLLECTION_PARAMETER("2002","传参有误"),
    COODS_COLLECTION_ADD("2003","收藏添加失败"),
    COODS_COLLECTION_DELECT("2004","收藏删除失败"),
    GOODS_ORDER_ADDERROR("2005","商品添加失败"),
    GOODS_ORDER_EDITERROR("2006","商品修改失败"),
    GOODS_ORDER_REMOVEERROR("2007","商品删除失败"),
    GOODS_ORDER_UPLOADORDERSTATUSERROR("2008","修改订单状态失败"),
    GOODS_EVALUATION_ADDERROR("2009","评价添加失败"),
    GOODS_SALESRETURN_ADDERROR("2010","添加退货失败"),
    GOODS_SALESRETURN_SELECTBYORADERNUMBERERROR("2011","通过订单号查询退货详情失败"),
    GOODS_PICTURE_ADDERROR("2012","商品添加图片失败"),
    GOODS_ADDERROR("2013","商品添加失败"),
    GOODS_PICTURE_UPLOADERROR("2014","商品修改图片失败"),
    GOODS_UPLOADERROR("2015","商品修改失败"),
    GOODS_SHARE_ADDERROR("2016","商品新增分享失败"),
    GOODS_SHARE_UPLODEERROR("2016","商品新增分享失败"),
    GOODS_SHARE_SSLEERROR("2017","商品分享SSL获取失败"),
    GOODS_SHARE_SHARE("2018","今天已经已经分享过商品"),
    GOODS_SELECTERROR("2019","商品查询失败"),
    GOODS_DELECTERROR("2020","商品删除失败"),
    GOODS_DELIVERYERROR("2021","商品确定收货失败"),
    GOODS__SALES_AUDITERROR("2022","商品退货失败"),
    GOODS__RETURNMANY_ERROR("2023","商品退货失败"),
    GOODS__OPERARETURNMANY_ERROR("2024","商品退货已完成，请不要重复操作"),
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
