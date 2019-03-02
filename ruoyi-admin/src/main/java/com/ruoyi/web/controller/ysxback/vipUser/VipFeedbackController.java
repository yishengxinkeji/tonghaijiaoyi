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
import com.ruoyi.yishengxin.domain.vipUser.VipFeedback;
import com.ruoyi.yishengxin.service.IVipFeedbackService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 用户反馈 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-02
 */
@Controller
@RequestMapping("/yishengxin/vipFeedback")
public class VipFeedbackController extends BaseController
{
    private String prefix = "yishengxin/vipFeedback";
	
	@Autowired
	private IVipFeedbackService vipFeedbackService;
	
	@RequiresPermissions("yishengxin:vipFeedback:view")
	@GetMapping()
	public String vipFeedback(){
	    return prefix + "/vipFeedback";
	}
	
	/**
	 * 查询用户反馈列表
	 */
	@RequiresPermissions("yishengxin:vipFeedback:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VipFeedback vipFeedback){
		startPage();
        List<VipFeedback> list = vipFeedbackService.selectVipFeedbackList(vipFeedback);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出用户反馈列表
	 */
	@RequiresPermissions("yishengxin:vipFeedback:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipFeedback vipFeedback){
    	List<VipFeedback> list = vipFeedbackService.selectVipFeedbackList(vipFeedback);
        ExcelUtil<VipFeedback> util = new ExcelUtil<VipFeedback>(VipFeedback.class);
        return util.exportExcel(list, "vipFeedback");
    }
	
	/**
	 * 新增用户反馈
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存用户反馈
	 */
	@RequiresPermissions("yishengxin:vipFeedback:add")
	@Log(title = "用户反馈", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VipFeedback vipFeedback){
	    vipFeedback.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(vipFeedbackService.insertVipFeedback(vipFeedback));
	}

	/**
	 * 修改用户反馈
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		VipFeedback vipFeedback = vipFeedbackService.selectVipFeedbackById(id);
		mmap.put("vipFeedback", vipFeedback);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存用户反馈
	 */
	@RequiresPermissions("yishengxin:vipFeedback:edit")
	@Log(title = "用户反馈", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipFeedback vipFeedback){
	    vipFeedback.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(vipFeedbackService.updateVipFeedback(vipFeedback));
	}
	
	/**
	 * 删除用户反馈
	 */
	@RequiresPermissions("yishengxin:vipFeedback:remove")
	@Log(title = "用户反馈", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(vipFeedbackService.deleteVipFeedbackByIds(ids));
	}
	
}
