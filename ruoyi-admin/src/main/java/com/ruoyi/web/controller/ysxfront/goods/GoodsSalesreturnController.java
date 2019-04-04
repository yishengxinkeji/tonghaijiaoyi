package com.ruoyi.web.controller.ysxfront.goods;

import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.order.Order;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.goods.Goods;
import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import com.ruoyi.yishengxin.domain.goods.GoodsSalesreturn;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IGoodsOrderService;
import com.ruoyi.yishengxin.service.IGoodsService;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.yishengxin.service.IGoodsSalesreturnService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 商品退货 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-05
 */
@Controller
@RequestMapping("/front/goodsSalesreturn")
public class GoodsSalesreturnController extends BaseFrontController {

    @Autowired
    private IGoodsSalesreturnService goodsSalesreturnService;

    @Autowired
    private IGoodsOrderService goodsOrderService;

    @Autowired
    private IVipUserService vipUserService;

    @Autowired
    private IGoodsService goodsService;


    /**
     * 上传文件
     *
     * @param file
     * @return 图片路径
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public ResponseResult avaterUpload(@RequestHeader("token") String token, @RequestParam("file") MultipartFile file) {

        VipUser vipUser = userExist(token);
        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        try {
            if (!file.isEmpty()) {
                //图片地址
                String path = uploadFile(file);
                vipUser.setAvater(Global.getFrontPath() + path);
                if (true) {
                    return ResponseResult.responseResult(ResponseEnum.SUCCESS, Global.getFrontPath() + path);
                }
            }
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_AVATER);
        } catch (FileSizeLimitExceededException e) {

            return ResponseResult.responseResult(ResponseEnum.FILE_TOO_MAX);
        } catch (FileNameLengthLimitExceededException e2) {

            return ResponseResult.responseResult(ResponseEnum.FILE_NAME_LENGTH);
        } catch (IOException e3) {
            return ResponseResult.error();
        }
    }


    /**
     * 添加退货物流信息
     */

    @PostMapping("/addRefundLogistics")
    @ResponseBody
    public ResponseResult addRefundLogistics(@RequestParam("orderNumber") String orderNumber, @RequestParam("refundCompany") String refundCompany, @RequestParam("refundLogistics") String refundLogistics) {

        try{
            GoodsSalesreturn goodsSalesreturn1 = goodsSalesreturnService.selectGoodsSalesreturnByOrderNumber(orderNumber);
            goodsSalesreturn1.setRefundCompany(refundCompany);
            goodsSalesreturn1.setRefundLogistics(refundLogistics);
            goodsSalesreturn1.setRefundStatus("8");
            int i = goodsSalesreturnService.updateGoodsSalesreturn(goodsSalesreturn1);
            if (i > 0) {
                return ResponseResult.responseResult(ResponseEnum.SUCCESS);
            }
            return ResponseResult.responseResult(ResponseEnum.FAIL);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }
    }

    @PostMapping("/list")
    @ResponseBody
    public ResponseResult list(GoodsSalesreturn goodsSalesreturn) {
        List<GoodsSalesreturn> goodsSalesreturns = goodsSalesreturnService.selectGoodsSalesreturnList(goodsSalesreturn);
        return ResponseResult.responseResult(ResponseEnum.SUCCESS, goodsSalesreturns);
    }

    /**
     * 订单退款
     *
     * @param token
     * @param orderId
     * @param
     * @param
     * @return @RequestParam("phone")
     * @throws IOException
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseResult addSave(@RequestHeader("token") String token, int orderId, @RequestParam("ploadDocuments") String ploadDocuments, @RequestParam("refundWay") String refundWay, @RequestParam("refundReason") String refundReason, @RequestParam("efundInstructions") String efundInstructions) throws IOException {
        try{
// 校验登录状态
            GoodsSalesreturn goodsSalesreturn = new GoodsSalesreturn();
            goodsSalesreturn.setPloadDocuments(ploadDocuments);
            goodsSalesreturn.setRefundWay(refundWay);
            goodsSalesreturn.setEfundInstructions(efundInstructions);
            goodsSalesreturn.setRefundReason(refundReason);
            VipUser vipUser = userExist(token);

            if (vipUser == null) {
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }
            //校验传参
            if (null == token || "".equals(token)) {
                return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
            }
            Integer uid = vipUser.getId();
            goodsSalesreturn.setUid(uid);
            GoodsOrder goodsOrder = goodsOrderService.selectGoodsOrderById(orderId);
            String orderNumber = goodsOrder.getOrderNumber();
            goodsSalesreturn.setOrderNumber(orderNumber);
            Integer goodsSoldNumber = goodsOrder.getGoodsSoldNumber();
            goodsSalesreturn.setRefundNumber(goodsSoldNumber);
            String goodsOrderTotalAmount = goodsOrder.getGoodsOrderTotalAmount();
            goodsSalesreturn.setRefundAmount(goodsOrderTotalAmount + "");
            String goodsName = goodsOrder.getGoodsName();
            goodsSalesreturn.setGoodsName(goodsName);
            String goodsDetails = goodsOrder.getGoodsDetails();
            goodsSalesreturn.setGoodsIntroduce(goodsDetails);
            String goodsPrice = goodsOrder.getGoodsPrice();
            goodsSalesreturn.setGoodsUnitPrice(goodsPrice + "");
            String goodsPicture = goodsOrder.getGoodsPicture();
            goodsSalesreturn.setGoodsImages(goodsPicture);
            Integer goodsSoldNumber1 = goodsOrder.getGoodsSoldNumber();
            goodsSalesreturn.setBuyNumber(goodsSoldNumber1);
            goodsSalesreturn.setRefundTime(new Date());
            Date createTime = goodsOrder.getCreateTime();
            goodsSalesreturn.setOrderTime(createTime);
            //生成退单号
            String orderIdByTime = Order.getOrderIdByTime();
            goodsSalesreturn.setRefundSerialNumber(orderIdByTime);
            if (goodsSalesreturn.getRefundWay().equals("1")) {
                goodsSalesreturn.setRefundStatus("1");
            } else {
                goodsSalesreturn.setRefundStatus("2");
            }

            int i1 = goodsSalesreturnService.insertGoodsSalesreturn(goodsSalesreturn);

            if (i1 > 0) {
                goodsOrder.setGoodsStatus("退款/售后");
                goodsOrderService.updateGoodsOrder(goodsOrder);
                return ResponseResult.responseResult(ResponseEnum.SUCCESS);
            } else {
                return ResponseResult.responseResult(ResponseEnum.GOODS_SALESRETURN_ADDERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }

    }


    /**
     * 待审核详情
     *
     * @param orderNumber 订单号
     * @return
     */
    @PostMapping("/selectByOrderNumber")
    @ResponseBody
    public ResponseResult selectGoodsSalesreturnByOrderNumber(@RequestHeader("token") String token, String orderNumber) {
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }
        VipUser vipUser = userExist(token);
        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        try{
            GoodsSalesreturn goodsSalesreturn = goodsSalesreturnService.selectGoodsSalesreturnByOrderNumber(orderNumber);
            if (null == goodsSalesreturn) {
                return ResponseResult.responseResult(ResponseEnum.GOODS_SALESRETURN_SELECTBYORADERNUMBERERROR);
            }
            return ResponseResult.responseResult(ResponseEnum.SUCCESS, goodsSalesreturn);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }

    }


    /**
     * 1仅退款
     * 1退款成功
     * <p>
     * 审批保存商品退货
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResponseResult editSave(int orderID, GoodsSalesreturn goodsSalesreturn) {
        //检查是否已经退货
        try{
            GoodsSalesreturn goodsSalesreturn1 = goodsSalesreturnService.selectGoodsSalesreturnById(goodsSalesreturn.getId());
            if (goodsSalesreturn1.getRefundStatus().equals("3") || goodsSalesreturn1.getRefundStatus().equals("4") || goodsSalesreturn1.getRefundStatus().equals("5") || goodsSalesreturn1.getRefundStatus().equals("6")) {
                ResponseResult.responseResult(ResponseEnum.GOODS__OPERARETURNMANY_ERROR);
            }
            if (goodsSalesreturn.getRefundWay().equals("1") && goodsSalesreturn.getRefundStatus().equals("1")) {
                goodsSalesreturn.setRefundStatus("3");
            } else if (goodsSalesreturn.getRefundWay().equals("1") && goodsSalesreturn.getRefundStatus().equals("2")) {
                goodsSalesreturn.setRefundStatus("4");
            } else if (goodsSalesreturn.getRefundWay().equals("2") && goodsSalesreturn.getRefundStatus().equals("1")) {
                goodsSalesreturn.setRefundStatus("5");
            } else if (goodsSalesreturn.getRefundWay().equals("2") && goodsSalesreturn.getRefundStatus().equals("2")) {
                goodsSalesreturn.setRefundStatus("6");
            }
            int i = goodsSalesreturnService.updateGoodsSalesreturn(goodsSalesreturn);

            if (i > 0) {
                if (goodsSalesreturn.getRefundStatus().equals("1")) {

                    GoodsOrder goodsOrder = goodsOrderService.selectGoodsOrderById(orderID);

                    String goodsOrderTotalAmount = goodsOrder.getGoodsOrderTotalAmount();
                    //todo

                    VipUser vipUser1 = vipUserService.selectVipUserById(goodsOrder.getUid());
                    String sslMoney = vipUser1.getSslMoney();

                    BigDecimal bigDecimal = new BigDecimal(sslMoney);
                    BigDecimal bigDecimal1 = new BigDecimal(goodsOrderTotalAmount);
                    BigDecimal addSsl = bigDecimal.add(bigDecimal1);

                    String sslMony = addSsl.toString();
                    vipUser1.setSslMoney(sslMony);
                    int i1 = vipUserService.updateVipUser(vipUser1);
                    if (i1 > 0) {
                        String goodsName = goodsOrder.getGoodsName();
                        Goods goods = goodsService.selectGoodsByGoodsName(goodsName);
                        Integer goodsSoldNumber = goods.getGoodsSoldNumber();
                        goods.setGoodsSoldNumber(goodsSoldNumber - 1);
                        goodsService.updateGoods(goods);
                        return ResponseResult.responseResult(ResponseEnum.SUCCESS);
                    }
                    return ResponseResult.responseResult(ResponseEnum.GOODS__RETURNMANY_ERROR);

                }

                return ResponseResult.responseResult(ResponseEnum.SUCCESS);
            }

            return ResponseResult.responseResult(ResponseEnum.GOODS__SALES_AUDITERROR);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }

    }

    /**
     * 删除商品退货
     */
    @PostMapping("/remove")
    @ResponseBody
    public ResponseResult remove(String ids) {

        return null;
    }

}
