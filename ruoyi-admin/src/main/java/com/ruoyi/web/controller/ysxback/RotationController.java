package com.ruoyi.web.controller.ysxback;

import java.util.List;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.yishengxin.domain.Rotation;
import com.ruoyi.yishengxin.service.IRotationService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 轮播图 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-12
 */
@Controller
@RequestMapping("/yishengxin/rotation")
public class RotationController extends BaseController
{
    private String prefix = "yishengxin/rotation";
	
	@Autowired
	private IRotationService rotationService;
	
	@RequiresPermissions("yishengxin:rotation:view")
	@GetMapping()
	public String rotation(){
	    return prefix + "/rotation";
	}
	
	/**
	 * 查询轮播图列表
	 */
	@RequiresPermissions("yishengxin:rotation:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Rotation rotation){
		startPage();
        List<Rotation> list = rotationService.selectRotationList(rotation);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出轮播图列表
	 */
	@RequiresPermissions("yishengxin:rotation:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Rotation rotation){
    	List<Rotation> list = rotationService.selectRotationList(rotation);
        ExcelUtil<Rotation> util = new ExcelUtil<Rotation>(Rotation.class);
        return util.exportExcel(list, "rotation");
    }
	
	/**
	 * 新增轮播图
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存轮播图
	 */
	@RequiresPermissions("yishengxin:rotation:add")
	@Log(title = "轮播图", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Rotation rotation){
	    rotation.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(rotationService.insertRotation(rotation));
	}

	/**
	 * 修改轮播图
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		Rotation rotation = rotationService.selectRotationById(id);
		mmap.put("rotation", rotation);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存轮播图
	 */
	@RequiresPermissions("yishengxin:rotation:edit")
	@Log(title = "轮播图", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Rotation rotation){
	    rotation.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(rotationService.updateRotation(rotation));
	}
	
	/**
	 * 删除轮播图
	 */
	@RequiresPermissions("yishengxin:rotation:remove")
	@Log(title = "轮播图", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(rotationService.deleteRotationByIds(ids));
	}

	/**
	 * 轮播图
	 */
	@Log(title = "轮播图", businessType = BusinessType.UPDATE)
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
