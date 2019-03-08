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
import com.ruoyi.yishengxin.domain.vipUser.VipLock;
import com.ruoyi.yishengxin.service.IVipLockService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * SSL锁仓 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-08
 */
@Controller
@RequestMapping("/yishengxin/vipLock")
public class VipLockController extends BaseController
{
    private String prefix = "yishengxin/vipLock";
	
	@Autowired
	private IVipLockService vipLockService;
	
	@RequiresPermissions("yishengxin:vipLock:view")
	@GetMapping()
	public String vipLock(){
	    return prefix + "/vipLock";
	}
	
	/**
	 * 查询SSL锁仓列表
	 */
	@RequiresPermissions("yishengxin:vipLock:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VipLock vipLock){
		startPage();
        List<VipLock> list = vipLockService.selectVipLockList(vipLock);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出SSL锁仓列表
	 */
	@RequiresPermissions("yishengxin:vipLock:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipLock vipLock){
    	List<VipLock> list = vipLockService.selectVipLockList(vipLock);
        ExcelUtil<VipLock> util = new ExcelUtil<VipLock>(VipLock.class);
        return util.exportExcel(list, "vipLock");
    }
	
	/**
	 * 新增SSL锁仓
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存SSL锁仓
	 */
	@RequiresPermissions("yishengxin:vipLock:add")
	@Log(title = "SSL锁仓", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VipLock vipLock){
	    vipLock.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(vipLockService.insertVipLock(vipLock));
	}

	/**
	 * 修改SSL锁仓
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		VipLock vipLock = vipLockService.selectVipLockById(id);
		mmap.put("vipLock", vipLock);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存SSL锁仓
	 */
	@RequiresPermissions("yishengxin:vipLock:edit")
	@Log(title = "SSL锁仓", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipLock vipLock){
	    vipLock.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(vipLockService.updateVipLock(vipLock));
	}
	
	/**
	 * 删除SSL锁仓
	 */
	@RequiresPermissions("yishengxin:vipLock:remove")
	@Log(title = "SSL锁仓", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(vipLockService.deleteVipLockByIds(ids));
	}
	
}
