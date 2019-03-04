package com.ruoyi.yishengxin.Vo;

import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import com.ruoyi.yishengxin.domain.vipUser.VipAddress;

public class OraderDetails {

    /**
     * 收货地址
     */
    VipAddress vipAddress;

    /**
     * 订单
     */

    GoodsOrder goodsOrders;

    public VipAddress getVipAddress() {
        return vipAddress;
    }

    public void setVipAddress(VipAddress vipAddress) {
        this.vipAddress = vipAddress;
    }

    public GoodsOrder getGoodsOrders() {
        return goodsOrders;
    }

    public void setGoodsOrders(GoodsOrder goodsOrders) {
        this.goodsOrders = goodsOrders;
    }

    @Override
    public String toString() {
        return "OraderDetails{" +
                "vipAddress=" + vipAddress +
                ", goodsOrders=" + goodsOrders +
                '}';
    }
}
