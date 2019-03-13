package com.ruoyi.web.controller.ysxback;

import java.util.List;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.PlatData;
import com.ruoyi.yishengxin.domain.Trade;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.yishengxin.service.IPlatDataService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 平台基本配置 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-04
 */
@Controller
@RequestMapping("/yishengxin/platData")
public class PlatDataController extends BaseController
{
    private String prefix = "yishengxin/platData";
	
	@Autowired
	private IPlatDataService platDataService;
	
	@RequiresPermissions("yishengxin:platData:view")
	@GetMapping()
	public String platData(){
	    return prefix + "/platData";
	}
	
	/**
	 * 查询平台基本配置列表
	 */
	@RequiresPermissions("yishengxin:platData:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(PlatData platData){
		startPage();
        List<PlatData> list = platDataService.selectPlatDataList(platData);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出平台基本配置列表
	 */
	@RequiresPermissions("yishengxin:platData:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PlatData platData){
    	List<PlatData> list = platDataService.selectPlatDataList(platData);
        ExcelUtil<PlatData> util = new ExcelUtil<PlatData>(PlatData.class);
        return util.exportExcel(list, "platData");
    }
	
	/**
	 * 新增平台基本配置
	 */
	@GetMapping("/add")
	public String add(){
		List<PlatData> platData = platDataService.selectPlatDataList(new PlatData());
		if(platData.size() > 0){
			return prefix + "/message";
		}
		return prefix + "/add";
	}
	
	/**
	 * 新增保存平台基本配置
	 */
	@RequiresPermissions("yishengxin:platData:add")
	@Log(title = "平台基本配置", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(PlatData platData){
	    platData.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(platDataService.insertPlatData(platData));
	}

	/**
	 * 修改平台基本配置
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		PlatData platData = platDataService.selectPlatDataById(id);
		mmap.put("platData", platData);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存平台基本配置
	 */
	@RequiresPermissions("yishengxin:platData:edit")
	@Log(title = "平台基本配置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(PlatData platData){
	    platData.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(platDataService.updatePlatData(platData));
	}
	
	/**
	 * 删除平台基本配置
	 */
	@RequiresPermissions("yishengxin:platData:remove")
	@Log(title = "平台基本配置", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(platDataService.deletePlatDataByIds(ids));
	}

	/**
	 * 图片上传
	 */
	@Log(title = "图片上传", businessType = BusinessType.UPDATE)
	@PostMapping("/upload")
	@ResponseBody
	public AjaxResult updateAvatar(@RequestParam("file") MultipartFile file) {
		try {
			if (!file.isEmpty()) {
				String avatar = FileUploadUtils.upload(Global.getAvatarPath(), file);
				return AjaxResult.success(Global.getAvatarPath() + avatar);
			}
			return error();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

}
