package com.ruoyi.web.controller.ysxfront.goods;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.order.Order;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.VipUser;
import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.yishengxin.service.IGoodsOrderService;


/**
 * 商品订单 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-02
 */
@Controller
@RequestMapping("/front/goodsOrder")
public class GoodsOrderController extends BaseFrontController {
    @Autowired
    private IGoodsOrderService goodsOrderService;



    /**
     * 新增保存商品订单
     */

    @PostMapping("/add")
    @ResponseBody
    public ResponseResult addSave(String token, GoodsOrder goodsOrder) {

        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);

        }
        //校验传参
        if (null == token || "".equals(token) || goodsOrder == null) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }
        //生成订单号
        String orderIdByTime = Order.getOrderIdByTime();
        goodsOrder.setOrderNumber(orderIdByTime);
        goodsOrder.setGoodsStatus("待付款");

        int i = goodsOrderService.insertGoodsOrder(goodsOrder);

        if (i > 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }

        return ResponseResult.responseResult(ResponseEnum.GOODS_ORDER_ADDERROR);
    }



    /**
     * 删除商品订单
     */

    @PostMapping("/remove")
    @ResponseBody
    public ResponseResult remove(String token, String ids) {
        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        //校验传参
        if (null == token || "".equals(token) || ids.length() == 0 || null == token) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        int i = goodsOrderService.deleteGoodsOrderByIds(ids);

        if (i > 0) {

            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }

        return ResponseResult.responseResult(ResponseEnum.GOODS_ORDER_REMOVEERROR);
    }

}
