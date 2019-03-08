package com.ruoyi.common.enums;

/**
 * 锁仓状态
 *
 * @author ruoyi
 */
public enum LockStatus {
    LOCKING("1", "锁仓中"),
    INTERUPT("2", "中断锁仓"),
    FINISH("3", "锁仓完成");

    private final String code;
    private final String info;

    LockStatus(String code, String info) {
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
