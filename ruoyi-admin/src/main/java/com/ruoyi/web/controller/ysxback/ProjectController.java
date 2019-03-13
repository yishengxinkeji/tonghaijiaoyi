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
import com.ruoyi.yishengxin.domain.Project;
import com.ruoyi.yishengxin.service.IProjectService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 项目 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-12
 */
@Controller
@RequestMapping("/yishengxin/project")
public class ProjectController extends BaseController
{
    private String prefix = "yishengxin/project";
	
	@Autowired
	private IProjectService projectService;
	
	@RequiresPermissions("yishengxin:project:view")
	@GetMapping()
	public String project(){
	    return prefix + "/project";
	}
	
	/**
	 * 查询项目列表
	 */
	@RequiresPermissions("yishengxin:project:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Project project){
		startPage();
        List<Project> list = projectService.selectProjectList(project);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出项目列表
	 */
	@RequiresPermissions("yishengxin:project:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Project project){
    	List<Project> list = projectService.selectProjectList(project);
        ExcelUtil<Project> util = new ExcelUtil<Project>(Project.class);
        return util.exportExcel(list, "project");
    }
	
	/**
	 * 新增项目
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存项目
	 */
	@RequiresPermissions("yishengxin:project:add")
	@Log(title = "项目", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Project project){
	    project.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(projectService.insertProject(project));
	}

	/**
	 * 修改项目
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		Project project = projectService.selectProjectById(id);
		mmap.put("project", project);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存项目
	 */
	@RequiresPermissions("yishengxin:project:edit")
	@Log(title = "项目", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Project project){
	    project.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(projectService.updateProject(project));
	}
	
	/**
	 * 删除项目
	 */
	@RequiresPermissions("yishengxin:project:remove")
	@Log(title = "项目", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(projectService.deleteProjectByIds(ids));
	}

	/**
	 * 新闻资讯凭证
	 */
	@Log(title = "项目", businessType = BusinessType.UPDATE)
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
