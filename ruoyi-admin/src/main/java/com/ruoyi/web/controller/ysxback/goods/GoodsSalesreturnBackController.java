package com.ruoyi.web.controller.ysxback.goods;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.goods.Goods;
import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import com.ruoyi.yishengxin.domain.goods.GoodsSalesreturn;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IGoodsOrderService;
import com.ruoyi.yishengxin.service.IGoodsService;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.yishengxin.service.IGoodsSalesreturnService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品退货 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-11
 */
@Controller
@RequestMapping("/yishengxin/goodsSalesreturn")
public class GoodsSalesreturnBackController extends BaseController
{
    private String prefix = "yishengxin/goodsSalesreturn";
	
	@Autowired
	private IGoodsSalesreturnService goodsSalesreturnService;

	@Autowired
	private IGoodsOrderService goodsOrderService;

	@Autowired
	private IVipUserService vipUserService;

	@Autowired
	private IGoodsService goodsService;
	
	@RequiresPermissions("yishengxin:goodsSalesreturn:view")
	@GetMapping()
	public String goodsSalesreturn(){
	    return prefix + "/goodsSalesreturn";
	}
	
	/**
	 * 查询商品退货列表
	 */
	@RequiresPermissions("yishengxin:goodsSalesreturn:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsSalesreturn goodsSalesreturn){
		startPage();
        List<GoodsSalesreturn> list = goodsSalesreturnService.selectGoodsSalesreturnList(goodsSalesreturn);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品退货列表
	 */
	@RequiresPermissions("yishengxin:goodsSalesreturn:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsSalesreturn goodsSalesreturn){
    	List<GoodsSalesreturn> list = goodsSalesreturnService.selectGoodsSalesreturnList(goodsSalesreturn);
        ExcelUtil<GoodsSalesreturn> util = new ExcelUtil<GoodsSalesreturn>(GoodsSalesreturn.class);
        return util.exportExcel(list, "goodsSalesreturn");
    }
	
	/**
	 * 新增商品退货
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品退货
	 */
	@RequiresPermissions("yishengxin:goodsSalesreturn:add")
	@Log(title = "商品退货", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsSalesreturn goodsSalesreturn){
	    goodsSalesreturn.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(goodsSalesreturnService.insertGoodsSalesreturn(goodsSalesreturn));
	}

	/**
	 * 修改商品退货
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		GoodsSalesreturn goodsSalesreturn = goodsSalesreturnService.selectGoodsSalesreturnById(id);
		mmap.put("goodsSalesreturn", goodsSalesreturn);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品退货
	 */
	@RequiresPermissions("yishengxin:goodsSalesreturn:edit")
	@Log(title = "商品退货", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsSalesreturn goodsSalesreturn){
	    goodsSalesreturn.setUpdateBy(ShiroUtils.getLoginName());
		String orderNumber = goodsSalesreturn.getOrderNumber();
		GoodsOrder goodsOrder1 = goodsOrderService.selectByOraderNumber(orderNumber);
		Integer orderID = goodsOrder1.getId();

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
				}

			}

			return toAjax(goodsSalesreturnService.updateGoodsSalesreturn(goodsSalesreturn));
		}

		return toAjax(goodsSalesreturnService.updateGoodsSalesreturn(goodsSalesreturn));
	}
	
	/**
	 * 删除商品退货
	 */
	@RequiresPermissions("yishengxin:goodsSalesreturn:remove")
	@Log(title = "商品退货", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(goodsSalesreturnService.deleteGoodsSalesreturnByIds(ids));
	}
	
}
