package com.ruoyi.web.controller.ysxback.goods;

import java.util.List;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import com.ruoyi.yishengxin.domain.goods.GoodsOrder2;
import com.ruoyi.yishengxin.service.IGoodsOrderService;
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
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品订单2 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-25
 */
@Controller
@RequestMapping("/yishengxin/goodsOrder2")
public class GoodsOrder2Controller extends BaseController
{
    private String prefix = "yishengxin/goodsOrder2";

	@Autowired
	private IGoodsOrderService goodsOrderService;
	
	@RequiresPermissions("yishengxin:goodsOrder2:view")
	@GetMapping()
	public String goodsOrder2(){
	    return prefix + "/goodsOrder2";
	}
	
	/**
	 * 查询商品订单2列表
	 */
	@RequiresPermissions("yishengxin:goodsOrder2:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsOrder goodsOrder){
		startPage();
		goodsOrder.setGoodsStatus("待发货");
		List<GoodsOrder> list = goodsOrderService.selectGoodsOrderList(goodsOrder);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品订单2列表
	 */
	@RequiresPermissions("yishengxin:goodsOrder2:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsOrder goodsOrder){
		goodsOrder.setGoodsStatus("待发货");
		List<GoodsOrder> list = goodsOrderService.selectGoodsOrderList(goodsOrder);
		ExcelUtil<GoodsOrder> util = new ExcelUtil<GoodsOrder>(GoodsOrder.class);
        return util.exportExcel(list, "goodsOrder2");
    }
	
	/**
	 * 新增商品订单2
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品订单2
	 */
	@RequiresPermissions("yishengxin:goodsOrder2:add")
	@Log(title = "商品订单2", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsOrder2 goodsOrder2){
	    goodsOrder2.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(1);
	}

	/**
	 * 修改商品订单2
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		GoodsOrder goodsOrder2 = goodsOrderService.selectGoodsOrderById(id);
		mmap.put("goodsOrder2", goodsOrder2);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品订单2
	 */
	@RequiresPermissions("yishengxin:goodsOrder2:edit")
	@Log(title = "商品订单2", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsOrder goodsOrder){
		goodsOrder.setGoodsStatus("待收货");
		goodsOrder.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(goodsOrderService.updateGoodsOrder(goodsOrder));
	}
	
	/**
	 * 删除商品订单2
	 */
	@RequiresPermissions("yishengxin:goodsOrder2:remove")
	@Log(title = "商品订单2", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(1);
	}
	
}
