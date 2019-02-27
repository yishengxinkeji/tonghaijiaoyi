package com.ruoyi.yishengxin.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 会员基本表 ysx_vip_user
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Data
@ToString
public class VipUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 登陆密码
     */
    private String loginPassword;
    /**
     * 交易密码
     */
    private String tradePassword;
    /**
     * 盐
     */
    private String salt;
    /**
     * 头像
     */
    private String avater;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 推广码
     */
    private String extensionCode;
    /**
     * 推荐码
     */
    private String recommendCode;
    /**
     * 钱包地址
     */
    private String moneyCode;
    /**
     * 邀请链接
     */
    private String inviteLink;
    /**
     * SSL资产
     */
    private String sslMoney;
    /**
     * HKD资产
     */
    private String hkdMoney;
    /**
     * 上级会员ID
     */
    private String parentId;
    /**
     * 是否冻结 Y 是 N 否
     */
    private String isFrozen;
    /**
     * 每日最大交易量,默认-1
     */
    private String maxTradeDay;


}
