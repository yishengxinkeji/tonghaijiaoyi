package com.ruoyi.web.controller.ysxback;

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
import com.ruoyi.yishengxin.domain.Notice;
import com.ruoyi.yishengxin.service.INoticeService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 公告中心 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-12
 */
@Controller
@RequestMapping("/yishengxin/notice")
public class NoticeController extends BaseController
{
    private String prefix = "yishengxin/notice";
	
	@Autowired
	private INoticeService noticeService;
	
	@RequiresPermissions("yishengxin:notice:view")
	@GetMapping()
	public String notice(){
	    return prefix + "/notice";
	}
	
	/**
	 * 查询公告中心列表
	 */
	@RequiresPermissions("yishengxin:notice:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Notice notice){
		startPage();
        List<Notice> list = noticeService.selectNoticeList(notice);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出公告中心列表
	 */
	@RequiresPermissions("yishengxin:notice:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Notice notice){
    	List<Notice> list = noticeService.selectNoticeList(notice);
        ExcelUtil<Notice> util = new ExcelUtil<Notice>(Notice.class);
        return util.exportExcel(list, "notice");
    }
	
	/**
	 * 新增公告中心
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存公告中心
	 */
	@RequiresPermissions("yishengxin:notice:add")
	@Log(title = "公告中心", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Notice notice){
	    notice.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(noticeService.insertNotice(notice));
	}

	/**
	 * 修改公告中心
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		Notice notice = noticeService.selectNoticeById(id);
		mmap.put("notice", notice);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存公告中心
	 */
	@RequiresPermissions("yishengxin:notice:edit")
	@Log(title = "公告中心", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Notice notice){
	    notice.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(noticeService.updateNotice(notice));
	}
	
	/**
	 * 删除公告中心
	 */
	@RequiresPermissions("yishengxin:notice:remove")
	@Log(title = "公告中心", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(noticeService.deleteNoticeByIds(ids));
	}
	
}
