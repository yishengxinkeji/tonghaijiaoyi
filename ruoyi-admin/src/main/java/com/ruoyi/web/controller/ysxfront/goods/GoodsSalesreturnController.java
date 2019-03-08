package com.ruoyi.web.controller.ysxfront.goods;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.order.Order;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import com.ruoyi.yishengxin.domain.goods.GoodsSalesreturn;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IGoodsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.yishengxin.service.IGoodsSalesreturnService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 商品退货 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-05
 */
@Controller
@RequestMapping("/front/goodsSalesreturn")
public class GoodsSalesreturnController extends BaseFrontController
{
	
	@Autowired
	private IGoodsSalesreturnService goodsSalesreturnService;

	@Autowired
	private IGoodsOrderService goodsOrderService;

	/**
	 * 查询商品退货列表
	 */

	@PostMapping("/list")
	@ResponseBody
	public ResponseResult list(String token , GoodsSalesreturn goodsSalesreturn){

		return null;
	}


	/**
	 * 新增保存商品仅退款退货
	 */
	@PostMapping("/onlySalesreturnAdd")
	@ResponseBody
	public ResponseResult onlySalesreturnAdd(String token , int orderId, GoodsSalesreturn goodsSalesreturn, MultipartFile[] filename) throws IOException {
		// 校验登录状态
		goodsSalesreturn.setRefundStatus("仅退款");
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

		String goodsName = goodsOrder.getGoodsName();
		goodsSalesreturn.setGoodsName(goodsName);

		String goodsDetails = goodsOrder.getGoodsDetails();
		goodsSalesreturn.setGoodsIntroduce(goodsDetails);

		Integer goodsPrice = goodsOrder.getGoodsPrice();
		goodsSalesreturn.setGoodsUnitPrice(goodsPrice+"");

		String goodsPicture = goodsOrder.getGoodsPicture();
		goodsSalesreturn.setGoodsImages(goodsPicture);

		Integer goodsSoldNumber = goodsOrder.getGoodsSoldNumber();
		goodsSalesreturn.setBuyNumber(goodsSoldNumber);

		Integer goodsOrderTotalAmount = goodsOrder.getGoodsOrderTotalAmount();
		goodsSalesreturn.setRefundAmount(goodsOrderTotalAmount+"");

		Date createTime = goodsOrder.getCreateTime();
		goodsSalesreturn.setOrderTime(createTime);
		//生成退单号
		String orderIdByTime = Order.getOrderIdByTime();
		goodsSalesreturn.setRefundSerialNumber(orderIdByTime);

		String imagesPath = "";
		for (int i = 0; i < filename.length; i++) {

			if (filename.length == 0) {
				int i1 = goodsSalesreturnService.insertGoodsSalesreturn(goodsSalesreturn);

				if (i1 > 0) {
					return ResponseResult.responseResult(ResponseEnum.SUCCESS);
				}
				return ResponseResult.responseResult(ResponseEnum.GOODS_SALESRETURN_ADDERROR);
			}
			String images = "e:/" + filename[i].getOriginalFilename() + ",";

			filename[i].transferTo(new File(images));
			imagesPath = imagesPath + images;
		}
		goodsSalesreturn.setPloadDocuments(imagesPath);
		int i = goodsSalesreturnService.insertGoodsSalesreturn(goodsSalesreturn);

		if (i > 0 ){
			return ResponseResult.responseResult(ResponseEnum.SUCCESS);
		}

		return ResponseResult.responseResult(ResponseEnum.GOODS_SALESRETURN_ADDERROR);
	}
	@PostMapping("/add")
	@ResponseBody
	public ResponseResult addSave(String token , int orderId, GoodsSalesreturn goodsSalesreturn, MultipartFile[] filename) throws IOException {
		// 校验登录状态
		goodsSalesreturn.setRefundStatus("退货退款");
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

		String goodsName = goodsOrder.getGoodsName();
		goodsSalesreturn.setGoodsName(goodsName);

		String goodsDetails = goodsOrder.getGoodsDetails();
		goodsSalesreturn.setGoodsIntroduce(goodsDetails);

		Integer goodsPrice = goodsOrder.getGoodsPrice();
		goodsSalesreturn.setGoodsUnitPrice(goodsPrice+"");

		String goodsPicture = goodsOrder.getGoodsPicture();
		goodsSalesreturn.setGoodsImages(goodsPicture);

		Integer goodsSoldNumber = goodsOrder.getGoodsSoldNumber();
		goodsSalesreturn.setBuyNumber(goodsSoldNumber);

		Integer goodsOrderTotalAmount = goodsOrder.getGoodsOrderTotalAmount();
		goodsSalesreturn.setRefundAmount(goodsOrderTotalAmount+"");

		Date createTime = goodsOrder.getCreateTime();
		goodsSalesreturn.setOrderTime(createTime);
		//生成退单号
		String orderIdByTime = Order.getOrderIdByTime();
		goodsSalesreturn.setRefundSerialNumber(orderIdByTime);

		String imagesPath = "";
		for (int i = 0; i < filename.length; i++) {

			if (filename.length == 0) {
				int i1 = goodsSalesreturnService.insertGoodsSalesreturn(goodsSalesreturn);

				if (i1 > 0) {
					return ResponseResult.responseResult(ResponseEnum.SUCCESS);
				}
				return ResponseResult.responseResult(ResponseEnum.GOODS_SALESRETURN_ADDERROR);
			}
			String images = "e:/" + filename[i].getOriginalFilename() + ",";

			filename[i].transferTo(new File(images));
			imagesPath = imagesPath + images;
		}
		goodsSalesreturn.setPloadDocuments(imagesPath);
		int i = goodsSalesreturnService.insertGoodsSalesreturn(goodsSalesreturn);

		if (i > 0 ){
			return ResponseResult.responseResult(ResponseEnum.SUCCESS);
		}

		return ResponseResult.responseResult(ResponseEnum.GOODS_SALESRETURN_ADDERROR);
	}



	/**
	 * 	待审核详情
	 *
	 * @param oraderNumber 订单号
	 * @return
	 */
	@PostMapping("/selectByOrderNumber")
	@ResponseBody
	public ResponseResult selectGoodsSalesreturnByOrderNumber(String token,String oraderNumber) {
		//校验传参
		if (null == token || "".equals(token)) {
			return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
		}
		VipUser vipUser = userExist(token);

		if (vipUser == null) {
			return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
		}
		GoodsSalesreturn goodsSalesreturn = goodsSalesreturnService.selectGoodsSalesreturnByOrderNumber(oraderNumber);
		if (null == goodsSalesreturn ) {
			return ResponseResult.responseResult(ResponseEnum.GOODS_SALESRETURN_SELECTBYORADERNUMBERERROR);

		}

		return ResponseResult.responseResult(ResponseEnum.SUCCESS,goodsSalesreturn);
	}




	/**
	 * 修改保存商品退货
	 */
	@PostMapping("/edit")
	@ResponseBody
	public ResponseResult editSave(GoodsSalesreturn goodsSalesreturn){

		return null;
	}
	
	/**
	 * 删除商品退货
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public ResponseResult remove(String ids){

		return null;
	}
	
}
