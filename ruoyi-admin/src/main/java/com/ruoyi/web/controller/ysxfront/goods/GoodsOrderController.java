package com.ruoyi.web.controller.ysxfront.goods;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.order.Order;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.Vo.OraderDetails;
import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import com.ruoyi.yishengxin.domain.vipUser.VipAddress;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IVipAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.yishengxin.service.IGoodsOrderService;

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

    @Autowired
    private IVipAddressService iVipAddressService;

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

    /**
     * 查询代付款订单列表
     */
    @PostMapping("/selectNotPay")
    @ResponseBody
    public ResponseResult selectNotPay(String token) {
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        Integer id = vipUser.getId();
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setUid(id);
        goodsOrder.setGoodsStatus("未付款");
        List<GoodsOrder> goodsOrders = goodsOrderService.selectGoodsOrderList(goodsOrder);

        if (goodsOrders.size() == 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS, "数据为空");
        }

        return ResponseResult.responseResult(ResponseEnum.SUCCESS, goodsOrders);
    }

    /**
     * 查询全部订单列表
     */
    @PostMapping("/selectAllOrader")
    @ResponseBody
    public ResponseResult selectAllOrader(String token) {
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        Integer id = vipUser.getId();
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setUid(id);
        List<GoodsOrder> goodsOrders = goodsOrderService.selectGoodsOrderList(goodsOrder);

        if (goodsOrders.size() == 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS, "数据为空");
        }

        return ResponseResult.responseResult(ResponseEnum.SUCCESS, goodsOrders);
    }
    /**
     * 查询代发货订单列表
     */
    @PostMapping("/selectDropShipping")
    @ResponseBody
    public ResponseResult selectDropShipping(String token) {
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        Integer id = vipUser.getId();
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setUid(id);
        goodsOrder.setGoodsStatus("代发货");
        List<GoodsOrder> goodsOrders = goodsOrderService.selectGoodsOrderList(goodsOrder);

        if (goodsOrders.size() == 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS, "数据为空");
        }

        return ResponseResult.responseResult(ResponseEnum.SUCCESS, goodsOrders);
    }
//wait for receiving
//查看完整结果>>
    /**
     * 查询代收货订单列表
     */
    @PostMapping("/selectWaitReceiving")
    @ResponseBody
    public ResponseResult selectWaitReceiving(String token) {
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        Integer id = vipUser.getId();
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setUid(id);
        goodsOrder.setGoodsStatus("代收货");
        List<GoodsOrder> goodsOrders = goodsOrderService.selectGoodsOrderList(goodsOrder);

        if (goodsOrders.size() == 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS, "数据为空");
        }

        return ResponseResult.responseResult(ResponseEnum.SUCCESS, goodsOrders);
    }
    /**
     * 查询代收货订单列表
     */
    @PostMapping("/selectStayGoods")
    @ResponseBody
    public ResponseResult selectStayGoods(String token) {
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        Integer id = vipUser.getId();
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setUid(id);
        goodsOrder.setGoodsStatus("代收货");
        List<GoodsOrder> goodsOrders = goodsOrderService.selectGoodsOrderList(goodsOrder);

        if (goodsOrders.size() == 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS, "数据为空");
        }

        return ResponseResult.responseResult(ResponseEnum.SUCCESS, goodsOrders);
    }

    /**
     * 查询代待评价订单列表
     */
    @PostMapping("/selectStayEvaluation")
    @ResponseBody
    public ResponseResult selectStayEvaluation(String token) {
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        Integer id = vipUser.getId();
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setUid(id);
        goodsOrder.setGoodsStatus("代评价");
        List<GoodsOrder> goodsOrders = goodsOrderService.selectGoodsOrderList(goodsOrder);

        if (goodsOrders.size() == 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS, "数据为空");
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS, goodsOrders);
    }
    /**
     * 查询售后订单列表
     */
    @PostMapping("/selectAfterSales")
    @ResponseBody
    public ResponseResult selectStayAfterSales(String token) {
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        Integer id = vipUser.getId();
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setUid(id);
        goodsOrder.setGoodsStatus("退款/售后");
        List<GoodsOrder> goodsOrders = goodsOrderService.selectGoodsOrderList(goodsOrder);

        if (goodsOrders.size() == 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS, "数据为空");
        }

        return ResponseResult.responseResult(ResponseEnum.SUCCESS, goodsOrders);
    }

    /**
     * 查询订单详情
     */
    @PostMapping("/selectNotPayDetail")
    @ResponseBody
    public ResponseResult selectNotPayDetail(String token, int oraderId) {
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        //校验传参
        if (null == token || "".equals(token) || oraderId == 0) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        Integer id = vipUser.getId();
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setUid(id);
        goodsOrder.setId(oraderId);
        List<GoodsOrder> goodsOrders = goodsOrderService.selectGoodsOrderList(goodsOrder);

        OraderDetails oraderDetails1 = new OraderDetails();
        Integer shippingAddress = goodsOrders.get(0).getShippingAddress();
        VipAddress vipAddress = iVipAddressService.selectVipAddressById(shippingAddress);

        oraderDetails1.setVipAddress(vipAddress);
        oraderDetails1.setGoodsOrders(goodsOrders.get(0));

        if (goodsOrders.size() == 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS, "数据为空");
        }

        return ResponseResult.responseResult(ResponseEnum.SUCCESS, oraderDetails1);

    }

}

