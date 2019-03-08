package com.ruoyi.web.controller.ysxfront.goods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.goods.GoodsShare;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.yishengxin.service.IGoodsShareService;
import com.ruoyi.common.base.AjaxResult;


/**
 * 商品分享 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-05
 */
@Controller
@RequestMapping("/front/goodsShare")
public class GoodsShareController extends BaseFrontController
{

	
	@Autowired
	private IGoodsShareService goodsShareService;

	@Autowired
	private IVipUserService iVipUserService ;
	


	/**
	 * 新增保存商品分享
	 */

	@PostMapping("/add")
	@ResponseBody
	public ResponseResult addSave(GoodsShare goodsShare){
			goodsShare.setCreateTime(new Date());
			goodsShare.setUpdateTime(new Date());
		int i = goodsShareService.insertGoodsShare(goodsShare);

		if (i > 0 ) {
			return ResponseResult.responseResult(ResponseEnum.SUCCESS);
		}
		return ResponseResult.responseResult(ResponseEnum.GOODS_SHARE_ADDERROR);
	}

	@PostMapping("/share")
	@ResponseBody
	public ResponseResult addSave(String token,GoodsShare goodsShare){
		// 校验登录状态

		if (null == token || "".equals(token)) {
			return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
		}
		VipUser vipUser = userExist(token);

		if (vipUser == null){
			return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);

		}
		//校验传参
		Integer id = vipUser.getId();
		List<GoodsShare> goodsShares = goodsShareService.selectGoodsShareList(goodsShare);
		VipUser vipUser1 = iVipUserService.selectVipUserById(id);
		String sslMoney = vipUser1.getSslMoney();

		String ssl = goodsShares.get(0).getBounty();

		BigDecimal bigDecimal = new BigDecimal(sslMoney);
		BigDecimal bigDecimal1 = new BigDecimal(ssl);
		BigDecimal addSsl = bigDecimal.add(bigDecimal1);

		String sslMony = addSsl.toString();
		vipUser1.setSslMoney(sslMoney);
		int i = iVipUserService.updateVipUser(vipUser1);
		if ( i > 0){
			return ResponseResult.responseResult(ResponseEnum.SUCCESS);
		}
		return ResponseResult.responseResult(ResponseEnum.GOODS_SHARE_SSLEERROR);
	}


	/**
	 * 修改保存商品分享
	 */

	@PostMapping("/edit")
	@ResponseBody
	public ResponseResult editSave(GoodsShare goodsShare){

		goodsShare.setUpdateTime(new Date());
		int i = goodsShareService.updateGoodsShare(goodsShare);

		if (i > 0 ) {
			return ResponseResult.responseResult(ResponseEnum.SUCCESS);
		}
		return ResponseResult.responseResult(ResponseEnum.GOODS_SHARE_UPLODEERROR);

	}

	/**
	 * 删除商品分享
	 */

	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return null;
	}
	
}
