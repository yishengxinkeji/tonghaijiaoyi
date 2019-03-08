package com.ruoyi.web.controller.ysxback.vipUser;

import java.util.List;

import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslBuy;
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
import com.ruoyi.yishengxin.service.IVipTradeSslBuyService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 挂买SSL 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-06
 */
@Controller
@RequestMapping("/yishengxin/vipTradeSslBuy")
public class VipTradeSslBuyController extends BaseController
{
    private String prefix = "yishengxin/vipTradeSslBuy";
	
	@Autowired
	private IVipTradeSslBuyService vipTradeBuyService;
	
	@RequiresPermissions("yishengxin:vipTradeBuy:view")
	@GetMapping()
	public String vipTradeBuy(){
	    return prefix + "/vipTradeSslBuy";
	}
	
	/**
	 * 查询挂买SSL列表
	 */
	@RequiresPermissions("yishengxin:vipTradeSslBuy:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VipTradeSslBuy vipTradeSslBuy){
		startPage();
        List<VipTradeSslBuy> list = vipTradeBuyService.selectVipTradeBuyList(vipTradeSslBuy);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出挂买SSL列表
	 */
	@RequiresPermissions("yishengxin:vipTradeSslBuy:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipTradeSslBuy vipTradeSslBuy){
    	List<VipTradeSslBuy> list = vipTradeBuyService.selectVipTradeBuyList(vipTradeSslBuy);
        ExcelUtil<VipTradeSslBuy> util = new ExcelUtil<VipTradeSslBuy>(VipTradeSslBuy.class);
        return util.exportExcel(list, "vipTradeSslBuy");
    }
	
	/**
	 * 新增挂买SSL
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存挂买SSL
	 */
	@RequiresPermissions("yishengxin:vipTradeSslBuy:add")
	@Log(title = "挂买SSL", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VipTradeSslBuy vipTradeSslBuy){
	    vipTradeSslBuy.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(vipTradeBuyService.insertVipTradeBuy(vipTradeSslBuy));
	}

	/**
	 * 修改挂买SSL
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		VipTradeSslBuy vipTradeSslBuy = vipTradeBuyService.selectVipTradeBuyById(id);
		mmap.put("vipTradeSslBuy", vipTradeSslBuy);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存挂买SSL
	 */
	@RequiresPermissions("yishengxin:vipTradeSslBuy:edit")
	@Log(title = "挂买SSL", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipTradeSslBuy vipTradeSslBuy){
	    vipTradeSslBuy.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(vipTradeBuyService.updateVipTradeBuy(vipTradeSslBuy));
	}
	
	/**
	 * 删除挂买SSL
	 */
	@RequiresPermissions("yishengxin:vipTradeBuy:remove")
	@Log(title = "挂买SSL", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(vipTradeBuyService.deleteVipTradeBuyByIds(ids));
	}
	
}
