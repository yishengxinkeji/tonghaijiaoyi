package com.ruoyi.yishengxin.Vo;

import com.ruoyi.yishengxin.domain.vipUser.VipUser;

public class VipUserEvaluation {
    /**
     * 用户信息
     */
    VipUser vipUser;

    /**
     * 商品评价
     */
    GoodsEvalutionVo goodsEvalutionVo;

    public VipUser getVipUser() {
        return vipUser;
    }

    public void setVipUser(VipUser vipUser) {
        this.vipUser = vipUser;
    }

    public GoodsEvalutionVo getGoodsEvalutionVo() {
        return goodsEvalutionVo;
    }

    public void setGoodsEvalutionVo(GoodsEvalutionVo goodsEvalutionVo) {
        this.goodsEvalutionVo = goodsEvalutionVo;
    }
}
