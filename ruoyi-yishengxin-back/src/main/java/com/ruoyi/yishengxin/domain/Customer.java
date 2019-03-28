package com.ruoyi.yishengxin.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 客服设置表 ysx_customer
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Data
@ToString
public class Customer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;
    /**
     * 客服类型
     */
    private String customerType;
    /**
     * 客服电话
     */
    private String phone;
    /** 联系地址 */
    private String address;
    /** 邮箱 */
    private String email;

}
