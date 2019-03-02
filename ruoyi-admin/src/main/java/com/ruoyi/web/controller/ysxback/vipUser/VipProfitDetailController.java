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
import com.ruoyi.yishengxin.domain.vipUser.VipProfitDetail;
import com.ruoyi.yishengxin.service.IVipProfitDetailService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 会员收益明细 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-02-28
 */
@Controller
@RequestMapping("/yishengxin/vipProfitDetail")
public class VipProfitDetailController extends BaseController
{
    private String prefix = "yishengxin/vipProfitDetail";
	
	@Autowired
	private IVipProfitDetailService vipProfitDetailService;
	
	@RequiresPermissions("yishengxin:vipProfitDetail:view")
	@GetMapping()
	public String vipProfitDetail(){
	    return prefix + "/vipProfitDetail";
	}
	
	/**
	 * 查询会员收益明细列表
	 */
	@RequiresPermissions("yishengxin:vipProfitDetail:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VipProfitDetail vipProfitDetail){
		startPage();
        List<VipProfitDetail> list = vipProfitDetailService.selectVipProfitDetailList(vipProfitDetail);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出会员收益明细列表
	 */
	@RequiresPermissions("yishengxin:vipProfitDetail:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipProfitDetail vipProfitDetail){
    	List<VipProfitDetail> list = vipProfitDetailService.selectVipProfitDetailList(vipProfitDetail);
        ExcelUtil<VipProfitDetail> util = new ExcelUtil<VipProfitDetail>(VipProfitDetail.class);
        return util.exportExcel(list, "vipProfitDetail");
    }
	
	/**
	 * 新增会员收益明细
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存会员收益明细
	 */
	@RequiresPermissions("yishengxin:vipProfitDetail:add")
	@Log(title = "会员收益明细", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VipProfitDetail vipProfitDetail){
	    vipProfitDetail.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(vipProfitDetailService.insertVipProfitDetail(vipProfitDetail));
	}

	/**
	 * 修改会员收益明细
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		VipProfitDetail vipProfitDetail = vipProfitDetailService.selectVipProfitDetailById(id);
		mmap.put("vipProfitDetail", vipProfitDetail);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存会员收益明细
	 */
	@RequiresPermissions("yishengxin:vipProfitDetail:edit")
	@Log(title = "会员收益明细", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipProfitDetail vipProfitDetail){
	    vipProfitDetail.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(vipProfitDetailService.updateVipProfitDetail(vipProfitDetail));
	}
	
	/**
	 * 删除会员收益明细
	 */
	@RequiresPermissions("yishengxin:vipProfitDetail:remove")
	@Log(title = "会员收益明细", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(vipProfitDetailService.deleteVipProfitDetailByIds(ids));
	}
	
}
