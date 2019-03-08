package com.ruoyi.yishengxin.Vo;

import com.ruoyi.yishengxin.domain.goods.Goods;

public class GoodsCollectionVo {
    /**
     * 商品信息
     */
    private Goods goods;

    /**
     * 收藏表 id
     */
    private int goodsCollectionId;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getGoodsCollectionId() {
        return goodsCollectionId;
    }

    public void setGoodsCollectionId(int goodsCollectionId) {
        this.goodsCollectionId = goodsCollectionId;
    }
}
