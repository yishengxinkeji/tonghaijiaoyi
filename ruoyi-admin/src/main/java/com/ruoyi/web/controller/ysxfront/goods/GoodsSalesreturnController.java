package com.ruoyi.web.controller.ysxfront.goods;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.order.Order;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.yishengxin.service.IGoodsSalesreturnService;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

	@Autowired
	private IVipUserService vipUserService;

	@Autowired
	private IGoodsService goodsService;
	/**
	 * 查询商品退货列表
	 */

	@PostMapping("/list")
	@ResponseBody
	public ResponseResult list(GoodsSalesreturn goodsSalesreturn){


		List<GoodsSalesreturn> goodsSalesreturns = goodsSalesreturnService.selectGoodsSalesreturnList(goodsSalesreturn);

		return ResponseResult.responseResult(ResponseEnum.SUCCESS,goodsSalesreturns);
	}




	/**
	 *  订单退款
	 * @param token
	 * @param orderId
	 * @param goodsSalesreturn
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/add")
	@ResponseBody
	public ResponseResult addSave(@RequestHeader("token")String token , int orderId, GoodsSalesreturn goodsSalesreturn, MultipartFile[] filename) throws IOException {
		// 校验登录状态

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

		String goodsPrice = goodsOrder.getGoodsPrice();
		goodsSalesreturn.setGoodsUnitPrice(goodsPrice+"");

		String goodsPicture = goodsOrder.getGoodsPicture();
		goodsSalesreturn.setGoodsImages(goodsPicture);

		Integer goodsSoldNumber = goodsOrder.getGoodsSoldNumber();
		goodsSalesreturn.setBuyNumber(goodsSoldNumber);

		goodsSalesreturn.setRefundTime(new Date());



		Date createTime = goodsOrder.getCreateTime();
		goodsSalesreturn.setOrderTime(createTime);
		//生成退单号
		String orderIdByTime = Order.getOrderIdByTime();
		goodsSalesreturn.setRefundSerialNumber(orderIdByTime);

		String imagesPath = "";

		if (null == filename) {
			goodsSalesreturn.setRefundStatus("待审批");
			int i1 = goodsSalesreturnService.insertGoodsSalesreturn(goodsSalesreturn);

			if (i1 > 0) {
				goodsOrder.setGoodsStatus("退款/售后");
				goodsOrderService.updateGoodsOrder(goodsOrder);


				return ResponseResult.responseResult(ResponseEnum.SUCCESS);
			}
			return ResponseResult.responseResult(ResponseEnum.GOODS_SALESRETURN_ADDERROR);
		}

		for (int i = 0; i < filename.length; i++) {
			String images = "e:/" + filename[i].getOriginalFilename() + ",";
			filename[i].transferTo(new File(images));
			imagesPath = imagesPath + images;
		}
		goodsSalesreturn.setPloadDocuments(imagesPath);
		goodsSalesreturn.setRefundStatus("待审批");
		int i = goodsSalesreturnService.insertGoodsSalesreturn(goodsSalesreturn);

		if (i > 0 ){
			goodsOrder.setGoodsStatus("退款/售后");
			goodsOrderService.updateGoodsOrder(goodsOrder);
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
	public ResponseResult selectGoodsSalesreturnByOrderNumber(@RequestHeader("token")String token,String oraderNumber) {
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
	 * 审批保存商品退货
	 */
	@PostMapping("/edit")
	@ResponseBody
	public ResponseResult editSave(int orderID,GoodsSalesreturn goodsSalesreturn){
		//检查是否已经退货

		GoodsSalesreturn goodsSalesreturn1 = goodsSalesreturnService.selectGoodsSalesreturnById(goodsSalesreturn.getId());


		if (goodsSalesreturn1.getRefundStatus().equals("同意") || goodsSalesreturn1.getRefundStatus().equals("不同意")){
				ResponseResult.responseResult(ResponseEnum.GOODS__OPERARETURNMANY_ERROR);

			}


		int i = goodsSalesreturnService.updateGoodsSalesreturn(goodsSalesreturn);

		if ( i > 0 ){
				if(goodsSalesreturn.getRefundStatus().equals("同意")){

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
					if (i1 > 0){
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
