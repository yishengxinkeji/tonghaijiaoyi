package com.ruoyi.web.controller.ysxback.vipUser;

import java.util.List;

import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslSale;
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
import com.ruoyi.yishengxin.service.IVipTradeSslSaleService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 挂卖SSL 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-06
 */
@Controller
@RequestMapping("/yishengxin/vipTradeSslSale")
public class VipTradeSslSaleController extends BaseController
{
    private String prefix = "yishengxin/vipTradeSslSale";
	
	@Autowired
	private IVipTradeSslSaleService vipTradeSaleService;
	
	@RequiresPermissions("yishengxin:vipTradeSale:view")
	@GetMapping()
	public String vipTradeSale(){
	    return prefix + "/vipTradeSslSale";
	}
	
	/**
	 * 查询挂卖SSL列表
	 */
	@RequiresPermissions("yishengxin:vipTradeSslSale:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VipTradeSslSale vipTradeSslSale){
		startPage();
        List<VipTradeSslSale> list = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出挂卖SSL列表
	 */
	@RequiresPermissions("yishengxin:vipTradeSslSale:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipTradeSslSale vipTradeSslSale){
    	List<VipTradeSslSale> list = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);
        ExcelUtil<VipTradeSslSale> util = new ExcelUtil<VipTradeSslSale>(VipTradeSslSale.class);
        return util.exportExcel(list, "vipTradeSslSale");
    }
	
	/**
	 * 新增挂卖SSL
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存挂卖SSL
	 */
	@RequiresPermissions("yishengxin:vipTradeSslSale:add")
	@Log(title = "挂卖SSL", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VipTradeSslSale vipTradeSslSale){
	    vipTradeSslSale.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(vipTradeSaleService.insertVipTradeSale(vipTradeSslSale));
	}

	/**
	 * 修改挂卖SSL
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		VipTradeSslSale vipTradeSslSale = vipTradeSaleService.selectVipTradeSaleById(id);
		mmap.put("vipTradeSslSale", vipTradeSslSale);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存挂卖SSL
	 */
	@RequiresPermissions("yishengxin:vipTradeSslSale:edit")
	@Log(title = "挂卖SSL", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipTradeSslSale vipTradeSslSale){
	    vipTradeSslSale.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(vipTradeSaleService.updateVipTradeSale(vipTradeSslSale));
	}
	
	/**
	 * 删除挂卖SSL
	 */
	@RequiresPermissions("yishengxin:vipTradeSale:remove")
	@Log(title = "挂卖SSL", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(vipTradeSaleService.deleteVipTradeSaleByIds(ids));
	}
	
}
