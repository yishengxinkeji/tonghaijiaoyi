package com.ruoyi.web.controller.ysxfront.goods;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.order.Order;
import com.ruoyi.common.utils.DateConversion;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.Vo.OraderVo;
import com.ruoyi.yishengxin.domain.goods.Goods;
import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import com.ruoyi.yishengxin.domain.goods.GoodsSalesreturn;
import com.ruoyi.yishengxin.domain.vipUser.VipAddress;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IGoodsSalesreturnService;
import com.ruoyi.yishengxin.service.IGoodsService;
import com.ruoyi.yishengxin.service.IVipAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private IGoodsSalesreturnService goodsSalesreturnService;

    @Autowired
    private IVipAddressService vipAddressService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 新增保存商品订单
     */

    @PostMapping("/add")
    @ResponseBody
    public ResponseResult addSave(@RequestHeader("token")String token, GoodsOrder goodsOrder) {

        //校验传参
        if (null == token || "".equals(token) || null == goodsOrder) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }
        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (null==vipUser) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);

        }
        //生成订单号
        String orderIdByTime = Order.getOrderIdByTime();
        goodsOrder.setOrderNumber(orderIdByTime);
        goodsOrder.setGoodsStatus("待付款");
        goodsOrder.setCreateTime(new Date());
        goodsOrder.setUid(vipUser.getId());
        VipAddress vipAddress = vipAddressService.selectVipAddressById(goodsOrder.getShippingAddress());
        String phone = vipAddress.getPhone();
        String receivUser = vipAddress.getReceivUser();
        String addressDetail = vipAddress.getAddressDetail();
        String province = vipAddress.getProvince();
        String city = vipAddress.getCity();
        String district = vipAddress.getDistrict();
        String address = receivUser+ " - "+ phone+" - "+province +" - "+city +" - " +district + " - " +addressDetail;
        goodsOrder.setRemark(address);

        int i = goodsOrderService.insertGoodsOrder(goodsOrder);

        if (i > 0) {
            String goodsName = goodsOrder.getGoodsName();
            Goods goods = goodsService.selectGoodsByGoodsName(goodsName);
            Integer goodsSoldNumber = goods.getGoodsSoldNumber();
            goods.setGoodsSoldNumber(goodsSoldNumber + 1);
            goodsService.updateGoods(goods);
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,i);
        }

        return ResponseResult.responseResult(ResponseEnum.GOODS_ORDER_ADDERROR);
    }


//
//    /**
//     * 删除商品订单
//     */
//
//    @PostMapping("/remove")
//    @ResponseBody
//    public ResponseResult remove(@RequestHeader("token")String token, String ids,String goodsName) {
//        //校验传参
//        if (null == token || "".equals(token) || ids.length() == 0 || null == token) {
//            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
//        }
//
//        // 校验登录状态
//        VipUser vipUser = userExist(token);
//
//        if (vipUser == null) {
//            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
//        }
//
//        int i = goodsOrderService.deleteGoodsOrderByIds(ids);
//
//        if (i > 0) {
//
//            Goods goods = goodsService.selectGoodsByGoodsName(goodsName);
//            Integer goodsSoldNumber = goods.getGoodsSoldNumber();
//            goods.setGoodsSoldNumber(goodsSoldNumber - 1);
//            goodsService.updateGoods(goods);
//            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
//        }
//
//        return ResponseResult.responseResult(ResponseEnum.GOODS_ORDER_REMOVEERROR);
//    }
//
//
//    /**
//     * 客户端查询各种订单状态接口
//     * @param token
//     * @param goodsOrder
//     * @return
//     */
//
//    @PostMapping("/selectOrader")
//    @ResponseBody
//    public ResponseResult selectOrader(@RequestHeader("token")String token, GoodsOrder goodsOrder) {
//
//        //校验传参
//        if (null == token || "".equals(token)) {
//            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
//        }
//
//        // 校验登录状态
//        VipUser vipUser = userExist(token);
//
//        if (null == vipUser ) {
//            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
//        }
//        if(null != goodsOrder.getId()){
//            goodsOrder.setUid(vipUser.getId());
//            GoodsOrder goodsOrder1 = goodsOrderService.selectGoodsOrderById(goodsOrder.getId());
//            Integer shippingAddress = goodsOrder1.getShippingAddress();
//
//            VipAddress vipAddress = vipAddressService.selectVipAddressById(shippingAddress);
//            OraderVo oraderVo = new OraderVo();
//            oraderVo.setGoodsOrder(goodsOrder1);
//            oraderVo.setVipAddress(vipAddress);
//            return ResponseResult.responseResult(ResponseEnum.SUCCESS,oraderVo);
//        }
//
//        goodsOrder.setUid(vipUser.getId());
//
//        List<GoodsOrder> goodsOrders = goodsOrderService.selectGoodsOrderList(goodsOrder);
//        if(goodsOrders.size() > 0){
//
//
//
//            for (int i = 0; i < goodsOrders.size(); i++) {
//                if (goodsOrders.get(i).getGoodsStatus().equals("退款/售后")) {
//
//                    String orderNumber = goodsOrders.get(i).getOrderNumber();
//                    GoodsSalesreturn goodsSalesreturn = goodsSalesreturnService.selectGoodsSalesreturnByOrderNumber(orderNumber);
//
//
//                    String refundStatus = goodsSalesreturn.getRefundStatus();
//
//                    goodsOrders.get(i).setGoodsStatus(refundStatus);
//                }
//
//
//            }
//            return ResponseResult.responseResult(ResponseEnum.SUCCESS,goodsOrders);
//        }
//        return ResponseResult.responseResult(ResponseEnum.SUCCESS,goodsOrders);
//
//    }
    /**
     * 删除商品订单
     */

    @PostMapping("/remove")
    @ResponseBody
    public ResponseResult remove(@RequestHeader("token")String token,@RequestParam("oid")int oid) {
        //校验传参
        if (null == token || "".equals(token) || null == token) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (null == vipUser) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        GoodsOrder goodsOrder = goodsOrderService.selectGoodsOrderById(oid);
        goodsOrder.setGoodsStatus("删除交易");
        int i = goodsOrderService.updateGoodsOrder(goodsOrder);
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
        if(null != goodsOrder.getId()){
            goodsOrder.setUid(vipUser.getId());
            GoodsOrder goodsOrder1 = goodsOrderService.selectGoodsOrderById(goodsOrder.getId());
            String remark = goodsOrder1.getRemark();
            String[] split = remark.split(" - ");
            VipAddress vipAddress = new VipAddress ();

            vipAddress.setReceivUser(split[0]);
            vipAddress.setPhone(split[1]);
            vipAddress.setProvince(split[2]);
            vipAddress.setCity(split[3]);
            vipAddress.setDistrict(split[4]);
            vipAddress.setAddressDetail(split[5]);

            OraderVo oraderVo = new OraderVo();
            oraderVo.setGoodsOrder(goodsOrder1);
            oraderVo.setVipAddress(vipAddress);
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,oraderVo);
        }

        goodsOrder.setUid(vipUser.getId());

        List<GoodsOrder> goodsOrders = goodsOrderService.selectGoodsOrderList(goodsOrder);

        if (goodsOrders.size()  > 0) {
            for (int i = 0; i < goodsOrders.size(); i++) {
                if (goodsOrders.get(i).getGoodsStatus().equals("删除交易")){
                    goodsOrders.remove(goodsOrders.get(i));
                }
            }
        }

        if(null != goodsOrder && null != goodsOrder.getGoodsStatus() ){
            if (goodsOrder.getGoodsStatus().equals("退款/售后")){
                goodsOrder.setGoodsStatus("交易完成");
                List<GoodsOrder> goodsOrders1 = goodsOrderService.selectGoodsOrderList(goodsOrder);
                for (int i = 0; i <goodsOrders1.size() ; i++) {
                    goodsOrders.add( goodsOrders1.get(i));
                }
            }
        }

        if(goodsOrders.size() > 0){
            for (int i = 0; i < goodsOrders.size(); i++) {
                if (goodsOrders.get(i).getGoodsStatus().equals("退款/售后")) {
                    String orderNumber = goodsOrders.get(i).getOrderNumber();
                    GoodsSalesreturn goodsSalesreturn = goodsSalesreturnService.selectGoodsSalesreturnByOrderNumber(orderNumber);
                    String refundStatus = goodsSalesreturn.getRefundStatus();
                    goodsOrders.get(i).setGoodsStatus(refundStatus);
                }
            }
            if (goodsOrders.size()  > 0) {
                for (int i = 0; i < goodsOrders.size(); i++) {
                    if (goodsOrders.get(i).getGoodsStatus().equals("删除交易")){
                        goodsOrders.remove(goodsOrders.get(i));
                    }
                }
            }

            return ResponseResult.responseResult(ResponseEnum.SUCCESS,goodsOrders);
        }
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


    @PostMapping("/statistical")
    @ResponseBody
    public int[] selectSoleNumber(String goodsName,Date years,Date month,Date day) throws Exception {

            if(null != day) {
                int[] soleNumbers = new int[24];
                String dayTime = DateConversion.dateToString(day, "yyyy-MM-dd HH:mm:ss");
                String dd = dayTime.substring(0, 11);
                Date staHHO = DateConversion.stringToDate(dd + "00:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH0 = DateConversion.stringToDate(dd + "00:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH1 = DateConversion.stringToDate(dd + "01:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH1 = DateConversion.stringToDate(dd + "01:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH2 = DateConversion.stringToDate(dd + "02:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH2 = DateConversion.stringToDate(dd + "02:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH3 = DateConversion.stringToDate(dd + "03:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH3 = DateConversion.stringToDate(dd + "03:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH4 = DateConversion.stringToDate(dd + "04:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH4 = DateConversion.stringToDate(dd + "04:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH5 = DateConversion.stringToDate(dd + "05:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH5 = DateConversion.stringToDate(dd + "05:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH6 = DateConversion.stringToDate(dd + "06:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH6 = DateConversion.stringToDate(dd + "06:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH7 = DateConversion.stringToDate(dd + "07:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH7 = DateConversion.stringToDate(dd + "07:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH8 = DateConversion.stringToDate(dd + "08:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH8 = DateConversion.stringToDate(dd + "08:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH9 = DateConversion.stringToDate(dd + "09:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH9 = DateConversion.stringToDate(dd + "09:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH10 = DateConversion.stringToDate(dd + "10:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH10 = DateConversion.stringToDate(dd + "10:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH11 = DateConversion.stringToDate(dd + "11:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH11 = DateConversion.stringToDate(dd + "11:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH12 = DateConversion.stringToDate(dd + "12:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH12 = DateConversion.stringToDate(dd + "12:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH13 = DateConversion.stringToDate(dd + "13:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH13 = DateConversion.stringToDate(dd + "13:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH14 = DateConversion.stringToDate(dd + "14:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH14 = DateConversion.stringToDate(dd + "14:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH15 = DateConversion.stringToDate(dd + "15:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH15 = DateConversion.stringToDate(dd + "15:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH16 = DateConversion.stringToDate(dd + "16:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH16 = DateConversion.stringToDate(dd + "16:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH17 = DateConversion.stringToDate(dd + "17:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH17 = DateConversion.stringToDate(dd + "17:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH18 = DateConversion.stringToDate(dd + "18:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH18 = DateConversion.stringToDate(dd + "18:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH19 = DateConversion.stringToDate(dd + "19:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH19 = DateConversion.stringToDate(dd + "19:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH2O = DateConversion.stringToDate(dd + "20:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH20 = DateConversion.stringToDate(dd + "20:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH21 = DateConversion.stringToDate(dd + "21:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH21 = DateConversion.stringToDate(dd + "21:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH22 = DateConversion.stringToDate(dd + "22:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH22 = DateConversion.stringToDate(dd + "22:59:59", "yyyy-MM-dd HH:mm:ss");
                Date staHH23 = DateConversion.stringToDate(dd + "23:00:00", "yyyy-MM-dd HH:mm:ss");
                Date stopHH23 = DateConversion.stringToDate(dd + "23:59:59", "yyyy-MM-dd HH:mm:ss");
                soleNumbers[0] = goodsOrderService.selectSoleNumber(goodsName,staHHO,stopHH0 );
                soleNumbers[1] = goodsOrderService.selectSoleNumber(goodsName,staHH1,stopHH1 );
                soleNumbers[2] = goodsOrderService.selectSoleNumber(goodsName,staHH2,stopHH2 );
                soleNumbers[3] = goodsOrderService.selectSoleNumber(goodsName,staHH3,stopHH3 );
                soleNumbers[4] = goodsOrderService.selectSoleNumber(goodsName,staHH4,stopHH4 );
                soleNumbers[5] = goodsOrderService.selectSoleNumber(goodsName,staHH5,stopHH5 );
                soleNumbers[6] = goodsOrderService.selectSoleNumber(goodsName,staHH6,stopHH6 );
                soleNumbers[7] = goodsOrderService.selectSoleNumber(goodsName,staHH7,stopHH7 );
                soleNumbers[8] = goodsOrderService.selectSoleNumber(goodsName,staHH8,stopHH8 );
                soleNumbers[9] = goodsOrderService.selectSoleNumber(goodsName,staHH9,stopHH9 );
                soleNumbers[10] = goodsOrderService.selectSoleNumber(goodsName,staHH10,stopHH10 );
                soleNumbers[11] = goodsOrderService.selectSoleNumber(goodsName,staHH11,stopHH11 );
                soleNumbers[12] = goodsOrderService.selectSoleNumber(goodsName,staHH12,stopHH12 );
                soleNumbers[13] = goodsOrderService.selectSoleNumber(goodsName,staHH13,stopHH13 );
                soleNumbers[14] = goodsOrderService.selectSoleNumber(goodsName,staHH14,stopHH14 );
                soleNumbers[15] = goodsOrderService.selectSoleNumber(goodsName,staHH15,stopHH15 );
                soleNumbers[16] = goodsOrderService.selectSoleNumber(goodsName,staHH16,stopHH16 );
                soleNumbers[17] = goodsOrderService.selectSoleNumber(goodsName,staHH17,stopHH17 );
                soleNumbers[18] = goodsOrderService.selectSoleNumber(goodsName,staHH18,stopHH18 );
                soleNumbers[19] = goodsOrderService.selectSoleNumber(goodsName,staHH19,stopHH19 );
                soleNumbers[20] = goodsOrderService.selectSoleNumber(goodsName,staHH2O,stopHH20 );
                soleNumbers[21] = goodsOrderService.selectSoleNumber(goodsName,staHH21,stopHH21 );
                soleNumbers[22] = goodsOrderService.selectSoleNumber(goodsName,staHH2,stopHH22 );
                soleNumbers[23] = goodsOrderService.selectSoleNumber(goodsName,staHH23,stopHH23 );
                return soleNumbers;
            }

        if(null == day && month != null ) {


        }



      return null;
    }
}
