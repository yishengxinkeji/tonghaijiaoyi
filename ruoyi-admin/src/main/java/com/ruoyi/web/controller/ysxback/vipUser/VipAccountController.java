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
import com.ruoyi.yishengxin.domain.vipUser.VipAccount;
import com.ruoyi.yishengxin.service.IVipAccountService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 收款账户 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
@Controller
@RequestMapping("/yishengxin/vipAccount")
public class VipAccountController extends BaseController
{
    private String prefix = "yishengxin/vipAccount";
	
	@Autowired
	private IVipAccountService vipAccountService;
	
	@RequiresPermissions("yishengxin:vipAccount:view")
	@GetMapping()
	public String vipAccount(){
	    return prefix + "/vipAccount";
	}
	
	/**
	 * 查询收款账户列表
	 */
	@RequiresPermissions("yishengxin:vipAccount:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VipAccount vipAccount){
		startPage();
        List<VipAccount> list = vipAccountService.selectVipAccountList(vipAccount);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出收款账户列表
	 */
	@RequiresPermissions("yishengxin:vipAccount:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipAccount vipAccount){
    	List<VipAccount> list = vipAccountService.selectVipAccountList(vipAccount);
        ExcelUtil<VipAccount> util = new ExcelUtil<VipAccount>(VipAccount.class);
        return util.exportExcel(list, "vipAccount");
    }
	
	/**
	 * 新增收款账户
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存收款账户
	 */
	@RequiresPermissions("yishengxin:vipAccount:add")
	@Log(title = "收款账户", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VipAccount vipAccount){
	    vipAccount.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(vipAccountService.insertVipAccount(vipAccount));
	}

	/**
	 * 修改收款账户
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		VipAccount vipAccount = vipAccountService.selectVipAccountById(id);
		mmap.put("vipAccount", vipAccount);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存收款账户
	 */
	@RequiresPermissions("yishengxin:vipAccount:edit")
	@Log(title = "收款账户", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipAccount vipAccount){
	    vipAccount.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(vipAccountService.updateVipAccount(vipAccount));
	}
	
	/**
	 * 删除收款账户
	 */
	@RequiresPermissions("yishengxin:vipAccount:remove")
	@Log(title = "收款账户", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(vipAccountService.deleteVipAccountByIds(ids));
	}
	
}
