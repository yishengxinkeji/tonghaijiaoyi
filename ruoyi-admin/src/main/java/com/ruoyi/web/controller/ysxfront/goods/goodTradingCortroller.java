package com.ruoyi.web.controller.ysxfront.goods;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IGoodsOrderService;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
@RequestMapping("/front/goodsTrading")
public class goodTradingCortroller extends BaseFrontController{

    @Autowired
    private IVipUserService iVipUserService ;

    @Autowired
    IGoodsOrderService iGoodsOrderService;

    /**
     *
     * @param token 令牌
     * @param orderTotalAmount  商品订单总额
     * @param tradePassword   支付密码
     * @param orderId 订单ID
     * @return
     */
    @PostMapping("/payment")
    @ResponseBody
    public ResponseResult payment(@RequestHeader("token")String token, String orderTotalAmount, String tradePassword, int orderId) {
        //校验传参
        if (null == token || "".equals(token) || null == orderTotalAmount || orderTotalAmount.length() == 0 || null == tradePassword || tradePassword.length() == 0 || orderId <= 0) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (null == vipUser){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);

        }

        Integer id = vipUser.getId();

        VipUser vipUser1 = iVipUserService.selectVipUserById(id);
        if (vipUser1 == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_NULL);
        }
        String tradePassword1 = vipUser1.getTradePassword();
        tradePassword = DigestUtils.md5Hex(tradePassword + vipUser1.getSalt());
        if (tradePassword1.equals(tradePassword)){
            String sslMoney = vipUser1.getSslMoney();

            BigDecimal bigDecimal = new BigDecimal(sslMoney);
            BigDecimal bigDecimal1 = new BigDecimal(orderTotalAmount);
            BigDecimal subtract = bigDecimal.subtract(bigDecimal1);
            int i = subtract.compareTo(new BigDecimal(0));
            if (i < 0){
                ResponseResult.responseResult(ResponseEnum.VIP_USER_SSLINSUFFICIENT);
            }
            String ssh = subtract.toString();
            vipUser1.setSslMoney(ssh);
            int i1 = iVipUserService.updateVipUser(vipUser1);

            if (i1 >  0) {
                GoodsOrder goodsOrder = iGoodsOrderService.selectGoodsOrderById(orderId);
                goodsOrder.setGoodsStatus("待发货");
                int i2 = iGoodsOrderService.updateGoodsOrder(goodsOrder);
                if (i2 > 0){

                    //交易记录没写
                    //TODO
                    return ResponseResult.responseResult(ResponseEnum.SUCCESS);
                }
                return ResponseResult.responseResult(ResponseEnum.GOODS_ORDER_UPLOADORDERSTATUSERROR);
            }

            return ResponseResult.responseResult(ResponseEnum.VIP_USER_TRADPUPLOADSSLERROR);

        }

        return ResponseResult.responseResult(ResponseEnum.VIP_USER_TRADPASSWORDERROR);
    }


}
