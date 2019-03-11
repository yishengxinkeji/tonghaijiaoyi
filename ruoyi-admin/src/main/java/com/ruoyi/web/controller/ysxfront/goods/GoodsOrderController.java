package com.ruoyi.web.controller.ysxfront.goods;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.order.Order;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.yishengxin.service.IGoodsOrderService;

import java.util.Date;
import java.util.List;


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
    public ResponseResult addSave(@RequestHeader("token")String token, GoodsOrder goodsOrder) {

        //校验传参
        if (null == token || "".equals(token) || goodsOrder == null) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }
        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);

        }
        //生成订单号
        String orderIdByTime = Order.getOrderIdByTime();
        goodsOrder.setOrderNumber(orderIdByTime);
        goodsOrder.setGoodsStatus("待付款");
        goodsOrder.setCreateTime(new Date());
        goodsOrder.setUid(vipUser.getId());
        int i = goodsOrderService.insertGoodsOrder(goodsOrder);

        if (i > 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,i);
        }

        return ResponseResult.responseResult(ResponseEnum.GOODS_ORDER_ADDERROR);
    }



    /**
     * 删除商品订单
     */

    @PostMapping("/remove")
    @ResponseBody
    public ResponseResult remove(@RequestHeader("token")String token, String ids) {
        //校验传参
        if (null == token || "".equals(token) || ids.length() == 0 || null == token) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        int i = goodsOrderService.deleteGoodsOrderByIds(ids);

        if (i > 0) {

            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }

        return ResponseResult.responseResult(ResponseEnum.GOODS_ORDER_REMOVEERROR);
    }


    /**
     * 客户端查询各种订单状态接口
     * @param token
     * @param goodsOrder
     * @return
     */

    @PostMapping("/selectOrader")
    @ResponseBody
    public ResponseResult selectOrader(@RequestHeader("token")String token, GoodsOrder goodsOrder) {

        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (null == vipUser ) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        goodsOrder.setUid(vipUser.getId());
        List<GoodsOrder> goodsOrders = goodsOrderService.selectGoodsOrderList(goodsOrder);

        return ResponseResult.responseResult(ResponseEnum.SUCCESS,goodsOrders);

    }

    @PostMapping("/selectOraderStatus")
    @ResponseBody
    public ResponseResult selectOrader( GoodsOrder goodsOrder) {

        //校验传参
        List<GoodsOrder> goodsOrders = goodsOrderService.selectGoodsOrderList(goodsOrder);

        return ResponseResult.responseResult(ResponseEnum.SUCCESS,goodsOrders);

    }



    /**
     * 各种订单状态修改的接口，退货的审批，订单状态的修改
     * @param goodsOrder
     * @return
     */
    @PostMapping("/uploadOrader")
    @ResponseBody
    public ResponseResult uploadOrader(GoodsOrder goodsOrder) {



        return null;
    }

    /**
     * 商品的发货
     * @param goodsOrder  物流公司 快递号 订单id
     * @return
     */

    @PostMapping("/deliveryOrader")
    @ResponseBody
    public ResponseResult deliveryOrader(GoodsOrder goodsOrder) {
        Integer id = goodsOrder.getId();
        GoodsOrder goodsOrder1 = goodsOrderService.selectGoodsOrderById(id);
        goodsOrder1.setCourierCompany(goodsOrder.getCourierCompany());
        goodsOrder1.setCourierNumber(goodsOrder.getCourierNumber());
        goodsOrder1.setGoodsStatus("待收货");
        int i = goodsOrderService.updateGoodsOrder(goodsOrder1);
        if (i > 0 ) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }

        return ResponseResult.responseResult(ResponseEnum. GOODS_DELIVERYERROR);
    }

    /**
     * 确定收货
     * @param goodsOrder   订单 id
     * @return
     */
    @PostMapping("/getGoodsOrader")
    @ResponseBody
    public ResponseResult getGoodsOrader(@RequestHeader("token")String token, GoodsOrder goodsOrder) {
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (null == vipUser ) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        Integer id = goodsOrder.getId();
        GoodsOrder goodsOrder1 = goodsOrderService.selectGoodsOrderById(id);
        goodsOrder1.setGoodsStatus("待评价");
        int i = goodsOrderService.updateGoodsOrder(goodsOrder1);
        if (i > 0 ) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }

        return ResponseResult.responseResult(ResponseEnum. GOODS_DELIVERYERROR);
    }

}
