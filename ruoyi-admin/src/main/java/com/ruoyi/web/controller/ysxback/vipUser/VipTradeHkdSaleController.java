package com.ruoyi.web.controller.ysxback.vipUser;

import java.util.List;

import com.ruoyi.framework.util.ShiroUtils;
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
import com.ruoyi.yishengxin.domain.vipUser.VipTradeHkdSale;
import com.ruoyi.yishengxin.service.IVipTradeHkdSaleService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 挂卖HKD 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-07
 */
@Controller
@RequestMapping("/yishengxin/vipTradeHkdSale")
public class VipTradeHkdSaleController extends BaseController
{
    private String prefix = "yishengxin/vipTradeHkdSale";
	
	@Autowired
	private IVipTradeHkdSaleService vipTradeHkdSaleService;
	
	@RequiresPermissions("yishengxin:vipTradeHkdSale:view")
	@GetMapping()
	public String vipTradeHkdSale(){
	    return prefix + "/vipTradeHkdSale";
	}
	
	/**
	 * 查询挂卖HKD列表
	 */
	@RequiresPermissions("yishengxin:vipTradeHkdSale:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VipTradeHkdSale vipTradeHkdSale){
		startPage();
        List<VipTradeHkdSale> list = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出挂卖HKD列表
	 */
	@RequiresPermissions("yishengxin:vipTradeHkdSale:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipTradeHkdSale vipTradeHkdSale){
    	List<VipTradeHkdSale> list = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);
        ExcelUtil<VipTradeHkdSale> util = new ExcelUtil<VipTradeHkdSale>(VipTradeHkdSale.class);
        return util.exportExcel(list, "vipTradeHkdSale");
    }
	
	/**
	 * 新增挂卖HKD
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存挂卖HKD
	 */
	@RequiresPermissions("yishengxin:vipTradeHkdSale:add")
	@Log(title = "挂卖HKD", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VipTradeHkdSale vipTradeHkdSale){
	    vipTradeHkdSale.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(vipTradeHkdSaleService.insertVipTradeHkdSale(vipTradeHkdSale));
	}

	/**
	 * 修改挂卖HKD
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		VipTradeHkdSale vipTradeHkdSale = vipTradeHkdSaleService.selectVipTradeHkdSaleById(id);
		mmap.put("vipTradeHkdSale", vipTradeHkdSale);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存挂卖HKD
	 */
	@RequiresPermissions("yishengxin:vipTradeHkdSale:edit")
	@Log(title = "挂卖HKD", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipTradeHkdSale vipTradeHkdSale){
	    vipTradeHkdSale.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(vipTradeHkdSaleService.updateVipTradeHkdSale(vipTradeHkdSale));
	}
	
	/**
	 * 删除挂卖HKD
	 */
	@RequiresPermissions("yishengxin:vipTradeHkdSale:remove")
	@Log(title = "挂卖HKD", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(vipTradeHkdSaleService.deleteVipTradeHkdSaleByIds(ids));
	}
	
}
