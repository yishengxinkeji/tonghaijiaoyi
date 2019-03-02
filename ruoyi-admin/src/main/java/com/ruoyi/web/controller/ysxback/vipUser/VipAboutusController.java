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
import com.ruoyi.yishengxin.domain.vipUser.VipAboutus;
import com.ruoyi.yishengxin.service.IVipAboutusService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 关于我们 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
@Controller
@RequestMapping("/yishengxin/vipAboutus")
public class VipAboutusController extends BaseController
{
    private String prefix = "yishengxin/vipAboutus";
	
	@Autowired
	private IVipAboutusService vipAboutusService;
	
	@RequiresPermissions("yishengxin:vipAboutus:view")
	@GetMapping()
	public String vipAboutus(){
	    return prefix + "/vipAboutus";
	}
	
	/**
	 * 查询关于我们列表
	 */
	@RequiresPermissions("yishengxin:vipAboutus:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VipAboutus vipAboutus){
		startPage();
        List<VipAboutus> list = vipAboutusService.selectVipAboutusList(vipAboutus);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出关于我们列表
	 */
	@RequiresPermissions("yishengxin:vipAboutus:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipAboutus vipAboutus){
    	List<VipAboutus> list = vipAboutusService.selectVipAboutusList(vipAboutus);
        ExcelUtil<VipAboutus> util = new ExcelUtil<VipAboutus>(VipAboutus.class);
        return util.exportExcel(list, "vipAboutus");
    }
	
	/**
	 * 新增关于我们
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存关于我们
	 */
	@RequiresPermissions("yishengxin:vipAboutus:add")
	@Log(title = "关于我们", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VipAboutus vipAboutus){
	    vipAboutus.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(vipAboutusService.insertVipAboutus(vipAboutus));
	}

	/**
	 * 修改关于我们
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		VipAboutus vipAboutus = vipAboutusService.selectVipAboutusById(id);
		mmap.put("vipAboutus", vipAboutus);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存关于我们
	 */
	@RequiresPermissions("yishengxin:vipAboutus:edit")
	@Log(title = "关于我们", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipAboutus vipAboutus){
	    vipAboutus.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(vipAboutusService.updateVipAboutus(vipAboutus));
	}
	
	/**
	 * 删除关于我们
	 */
	@RequiresPermissions("yishengxin:vipAboutus:remove")
	@Log(title = "关于我们", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(vipAboutusService.deleteVipAboutusByIds(ids));
	}
	
}
