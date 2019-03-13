package com.ruoyi.web.controller.ysxfront.goods;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.goods.GoodsShare;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.yishengxin.service.IGoodsShareService;

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
     * 分享商品转赏金
     *
     * @param token
     * @return
     */

	@PostMapping("/share")
	@ResponseBody
	public  ResponseResult share(@RequestHeader("token")String token){

        // 校验登录状态

        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        if (null == userExist(token)) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipUser vipUser = userExist(token);
        //校验传参
        Integer id = vipUser.getId();
        //用户首次分享
        if (null == goodsShareService.selectGoodsShareByUid(id)) {
            GoodsShare goodsShare1 = goodsShareService.selectGoodsShareById(1);
            VipUser vipUser1 = iVipUserService.selectVipUserById(id);
            String sslMoney = vipUser1.getSslMoney();

            String ssl = goodsShare1.getBounty();

            BigDecimal bigDecimal = new BigDecimal(sslMoney);
            BigDecimal bigDecimal1 = new BigDecimal(ssl);
            BigDecimal addSsl = bigDecimal.add(bigDecimal1);

            String sslMony = addSsl.toString();
            vipUser1.setSslMoney(sslMony);
            int i = iVipUserService.updateVipUser(vipUser1);
            if (i > 0) {
                GoodsShare goodsShare = new GoodsShare();
                goodsShare.setUid(id);
                goodsShare.setCreateTime(new Date());
                goodsShareService.insertGoodsShare(goodsShare);

                return ResponseResult.responseResult(ResponseEnum.SUCCESS);
            }
            return ResponseResult.responseResult(ResponseEnum.GOODS_SHARE_SSLEERROR);
        }else {

            GoodsShare goodsShare = goodsShareService.selectGoodsShareByUid(id);
            Date createTime = goodsShare.getCreateTime();

            String s = new SimpleDateFormat("yyyy-MM-dd").format(createTime);
            String s1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            if (s.equals(s1)){
                //今日已经分享过
                return ResponseResult.responseResult(ResponseEnum.GOODS_SHARE_SHARE);
            }else{
                //今日未分享
                GoodsShare goodsShare1 = goodsShareService.selectGoodsShareById(1);
                VipUser vipUser1 = iVipUserService.selectVipUserById(id);
                String sslMoney = vipUser1.getSslMoney();

                String ssl = goodsShare1.getBounty();

                BigDecimal bigDecimal = new BigDecimal(sslMoney);
                BigDecimal bigDecimal1 = new BigDecimal(ssl);
                BigDecimal addSsl = bigDecimal.add(bigDecimal1);

                String sslMony = addSsl.toString();
                vipUser1.setSslMoney(sslMony);
                int i = iVipUserService.updateVipUser(vipUser1);
                if ( i > 0){
                    GoodsShare goodsShare3 = new GoodsShare();
                    goodsShare3.setUid(id);
                    goodsShare3.setCreateTime(new Date());
                    goodsShareService.insertGoodsShare(goodsShare3);
                    return ResponseResult.responseResult(ResponseEnum.SUCCESS);
                }
                return ResponseResult.responseResult(ResponseEnum.GOODS_SHARE_SSLEERROR);
            }
        }
	}


	/**
	 *后台修改保存商品分享
	 */

	@PostMapping("/edit")
	@ResponseBody
	public ResponseResult editSave(GoodsShare goodsShare){
		goodsShare.setId(1);
		goodsShare.setUpdateTime(new Date());
		int i = goodsShareService.updateGoodsShare(goodsShare);

		if (i > 0 ) {
			return ResponseResult.responseResult(ResponseEnum.SUCCESS);
		}
		return ResponseResult.responseResult(ResponseEnum.GOODS_SHARE_UPLODEERROR);

	}


}