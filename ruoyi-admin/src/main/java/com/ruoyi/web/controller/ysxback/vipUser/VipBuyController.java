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
import com.ruoyi.yishengxin.domain.vipUser.VipBuy;
import com.ruoyi.yishengxin.service.IVipBuyService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 个人购买 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
@Controller
@RequestMapping("/yishengxin/vipBuy")
public class VipBuyController extends BaseController
{
    private String prefix = "yishengxin/vipBuy";
	
	@Autowired
	private IVipBuyService vipBuyService;
	
	@RequiresPermissions("yishengxin:vipBuy:view")
	@GetMapping()
	public String vipBuy(){
	    return prefix + "/vipBuy";
	}
	
	/**
	 * 查询个人购买列表
	 */
	@RequiresPermissions("yishengxin:vipBuy:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VipBuy vipBuy){
		startPage();
        List<VipBuy> list = vipBuyService.selectVipBuyList(vipBuy);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出个人购买列表
	 */
	@RequiresPermissions("yishengxin:vipBuy:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipBuy vipBuy){
    	List<VipBuy> list = vipBuyService.selectVipBuyList(vipBuy);
        ExcelUtil<VipBuy> util = new ExcelUtil<VipBuy>(VipBuy.class);
        return util.exportExcel(list, "vipBuy");
    }
	
	/**
	 * 新增个人购买
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存个人购买
	 */
	@RequiresPermissions("yishengxin:vipBuy:add")
	@Log(title = "个人购买", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VipBuy vipBuy){
	    vipBuy.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(vipBuyService.insertVipBuy(vipBuy));
	}

	/**
	 * 修改个人购买
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		VipBuy vipBuy = vipBuyService.selectVipBuyById(id);
		mmap.put("vipBuy", vipBuy);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存个人购买
	 */
	@RequiresPermissions("yishengxin:vipBuy:edit")
	@Log(title = "个人购买", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipBuy vipBuy){
	    vipBuy.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(vipBuyService.updateVipBuy(vipBuy));
	}
	
	/**
	 * 删除个人购买
	 */
	@RequiresPermissions("yishengxin:vipBuy:remove")
	@Log(title = "个人购买", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(vipBuyService.deleteVipBuyByIds(ids));
	}
	
}
