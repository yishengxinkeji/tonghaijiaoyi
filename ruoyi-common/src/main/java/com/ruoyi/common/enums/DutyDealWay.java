package com.ruoyi.common.enums;

/**
 * 申诉处理方式
 *
 * @author ruoyi
 */
public enum DutyDealWay {
    NO("1", "双方无责任"),
    BUY("2", "买家未打款,不处理"),
    SALE_ONE("3", "买家打款,卖家未确认,交易失败,扣除卖家责任手续费");
    //SALE_TWO("4", "买家打款,卖家未确认,修改订单,扣除卖家hkd,转入买家ssl");

    private final String code;
    private final String info;

    DutyDealWay(String code, String info) {
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
