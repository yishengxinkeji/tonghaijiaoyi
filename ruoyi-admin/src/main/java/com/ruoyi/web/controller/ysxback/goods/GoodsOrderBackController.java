package com.ruoyi.web.controller.ysxback.goods;

import java.util.List;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
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
import com.ruoyi.yishengxin.service.IGoodsOrderService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品订单 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-19
 */
@Controller
@RequestMapping("/yishengxin/goodsOrder")
public class GoodsOrderBackController extends BaseController
{
    private String prefix = "yishengxin/goodsOrder";
	
	@Autowired
	private IGoodsOrderService goodsOrderService;
	
	@RequiresPermissions("yishengxin:goodsOrder:view")
	@GetMapping()
	public String goodsOrder(){
	    return prefix + "/goodsOrder";
	}
	
	/**
	 * 查询商品订单列表
	 */
	@RequiresPermissions("yishengxin:goodsOrder:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsOrder goodsOrder){
		startPage();
        List<GoodsOrder> list = goodsOrderService.selectGoodsOrderList(goodsOrder);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品订单列表
	 */
	@RequiresPermissions("yishengxin:goodsOrder:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsOrder goodsOrder){
    	List<GoodsOrder> list = goodsOrderService.selectGoodsOrderList(goodsOrder);
        ExcelUtil<GoodsOrder> util = new ExcelUtil<GoodsOrder>(GoodsOrder.class);
        return util.exportExcel(list, "goodsOrder");
    }
	
	/**
	 * 新增商品订单
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品订单
	 */
	@RequiresPermissions("yishengxin:goodsOrder:add")
	@Log(title = "商品订单", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsOrder goodsOrder){
	    goodsOrder.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(goodsOrderService.insertGoodsOrder(goodsOrder));
	}

	/**
	 * 修改商品订单
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		GoodsOrder goodsOrder = goodsOrderService.selectGoodsOrderById(id);
		mmap.put("goodsOrder", goodsOrder);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品订单
	 */
	@RequiresPermissions("yishengxin:goodsOrder:edit")
	@Log(title = "商品订单", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsOrder goodsOrder){
	    goodsOrder.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(goodsOrderService.updateGoodsOrder(goodsOrder));
	}
	
	/**
	 * 删除商品订单
	 */
	@RequiresPermissions("yishengxin:goodsOrder:remove")
	@Log(title = "商品订单", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(goodsOrderService.deleteGoodsOrderByIds(ids));
	}
	
}
