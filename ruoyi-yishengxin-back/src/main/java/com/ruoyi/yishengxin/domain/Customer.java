package com.ruoyi.yishengxin.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

/**
 * 客服设置表 ysx_customer
 *
 * @author ruoyi
 * @date 2019-02-26
 */
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("customerType", getCustomerType())
                .append("phone", getPhone())
                .toString();
    }
}
