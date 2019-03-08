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
import com.ruoyi.yishengxin.domain.vipUser.VipTradeHkdBuy;
import com.ruoyi.yishengxin.service.IVipTradeHkdBuyService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 挂买HKD 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-07
 */
@Controller
@RequestMapping("/yishengxin/vipTradeHkdBuy")
public class VipTradeHkdBuyController extends BaseController
{
    private String prefix = "yishengxin/vipTradeHkdBuy";
	
	@Autowired
	private IVipTradeHkdBuyService vipTradeHkdBuyService;
	
	@RequiresPermissions("yishengxin:vipTradeHkdBuy:view")
	@GetMapping()
	public String vipTradeHkdBuy(){
	    return prefix + "/vipTradeHkdBuy";
	}
	
	/**
	 * 查询挂买HKD列表
	 */
	@RequiresPermissions("yishengxin:vipTradeHkdBuy:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VipTradeHkdBuy vipTradeHkdBuy){
		startPage();
        List<VipTradeHkdBuy> list = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出挂买HKD列表
	 */
	@RequiresPermissions("yishengxin:vipTradeHkdBuy:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipTradeHkdBuy vipTradeHkdBuy){
    	List<VipTradeHkdBuy> list = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy);
        ExcelUtil<VipTradeHkdBuy> util = new ExcelUtil<VipTradeHkdBuy>(VipTradeHkdBuy.class);
        return util.exportExcel(list, "vipTradeHkdBuy");
    }
	
	/**
	 * 新增挂买HKD
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存挂买HKD
	 */
	@RequiresPermissions("yishengxin:vipTradeHkdBuy:add")
	@Log(title = "挂买HKD", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VipTradeHkdBuy vipTradeHkdBuy){
	    vipTradeHkdBuy.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(vipTradeHkdBuyService.insertVipTradeHkdBuy(vipTradeHkdBuy));
	}

	/**
	 * 修改挂买HKD
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		VipTradeHkdBuy vipTradeHkdBuy = vipTradeHkdBuyService.selectVipTradeHkdBuyById(id);
		mmap.put("vipTradeHkdBuy", vipTradeHkdBuy);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存挂买HKD
	 */
	@RequiresPermissions("yishengxin:vipTradeHkdBuy:edit")
	@Log(title = "挂买HKD", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipTradeHkdBuy vipTradeHkdBuy){
	    vipTradeHkdBuy.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(vipTradeHkdBuyService.updateVipTradeHkdBuy(vipTradeHkdBuy));
	}
	
	/**
	 * 删除挂买HKD
	 */
	@RequiresPermissions("yishengxin:vipTradeHkdBuy:remove")
	@Log(title = "挂买HKD", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(vipTradeHkdBuyService.deleteVipTradeHkdBuyByIds(ids));
	}
	
}
