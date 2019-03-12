package com.ruoyi.yishengxin.Vo;

import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import com.ruoyi.yishengxin.domain.vipUser.VipAddress;

public class OraderVo {

    private VipAddress vipAddress;

    private GoodsOrder goodsOrder;

    public VipAddress getVipAddress() {
        return vipAddress;
    }

    public void setVipAddress(VipAddress vipAddress) {
        this.vipAddress = vipAddress;
    }

    public GoodsOrder getGoodsOrder() {
        return goodsOrder;
    }

    public void setGoodsOrder(GoodsOrder goodsOrder) {
        this.goodsOrder = goodsOrder;
    }
}
