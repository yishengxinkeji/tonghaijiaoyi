package com.ruoyi.web.controller.ysxback.vipUser;

import java.util.List;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.vipUser.VipExchange;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.yishengxin.domain.vipUser.VipBuy;
import com.ruoyi.yishengxin.service.IVipBuyService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

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



	/**
	 * 修改个人兑换
	 */
	@GetMapping("/buy/{id}")
	public String buy(@PathVariable("id") Integer id, ModelMap mmap) {
		VipBuy vipBuy = vipBuyService.selectVipBuyById(id);
		mmap.put("vipBuy", vipBuy);
		return prefix + "/buy";
	}

	//TODO 上传路径修改

	/**
	 * 上传兑换凭证
	 */
	@Log(title = "个人信息", businessType = BusinessType.UPDATE)
	@PostMapping("/upload")
	@ResponseBody
	public AjaxResult updateDetail(@RequestParam("file") MultipartFile file) {
		try {
			if (!file.isEmpty()) {
				String avatar = FileUploadUtils.upload(Global.getFrontPath(), file);
				return AjaxResult.success(avatar);
			}
			return error();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 修改保存个人兑换
	 */
	@RequiresPermissions("yishengxin:vipExchange:edit")
	@Log(title = "个人购买", businessType = BusinessType.UPDATE)
	@PostMapping("/buySave")
	@ResponseBody
	public AjaxResult buySave(VipBuy vipBuy) {
		vipBuy.setUpdateBy(ShiroUtils.getLoginName());
		vipBuy.setBuyStatus(CustomerConstants.EXCHANGE_BUY_STATUS_DEAL);

		try {
			return toAjax(vipBuyService.exchange(vipBuy));
		} catch (Exception e) {
			e.printStackTrace();
			return error();
		}
	}

}
