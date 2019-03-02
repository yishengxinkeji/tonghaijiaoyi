package com.ruoyi.web.controller.ysxback.vipUser;

import java.math.BigDecimal;
import java.util.List;

import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IVipUserService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 会员基本 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-02-26
 */
@Controller
@RequestMapping("/yishengxin/vipUser")
public class VipUserController extends BaseController
{
    private String prefix = "yishengxin/vipUser";
	
	@Autowired
	private IVipUserService vipUserService;
	
	@RequiresPermissions("yishengxin:vipUser:view")
	@GetMapping()
	public String vipUser(){
	    return prefix + "/vipUser";
	}
	
	/**
	 * 查询会员基本列表
	 */
	@RequiresPermissions("yishengxin:vipUser:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VipUser vipUser){
		startPage();
        List<VipUser> list = vipUserService.selectVipUserList(vipUser);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出会员基本列表
	 */
	@RequiresPermissions("yishengxin:vipUser:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipUser vipUser){
    	List<VipUser> list = vipUserService.selectVipUserList(vipUser);
        ExcelUtil<VipUser> util = new ExcelUtil<VipUser>(VipUser.class);
        return util.exportExcel(list, "vipUser");
    }
	
	/**
	 * 新增会员基本
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存会员基本
	 */
	@RequiresPermissions("yishengxin:vipUser:add")
	@Log(title = "会员基本", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VipUser vipUser){
	    vipUser.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(vipUserService.insertVipUser(vipUser));
	}

	/**
	 * 修改会员基本
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		VipUser vipUser = vipUserService.selectVipUserById(id);
		mmap.put("vipUser", vipUser);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存会员基本
	 */
	@RequiresPermissions("yishengxin:vipUser:edit")
	@Log(title = "会员基本", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipUser vipUser){
	    vipUser.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(vipUserService.updateVipUser(vipUser));
	}
	
	/**
	 * 删除会员基本
	 */
	@RequiresPermissions("yishengxin:vipUser:remove")
	@Log(title = "会员基本", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(vipUserService.deleteVipUserByIds(ids));
	}

	/**
	 * 冻结/解冻会员
	 * @param
	 * @return
	 */
	@RequiresPermissions("yishengxin:vipUser:frozen")
	@Log(title = "会员基本", businessType = BusinessType.FROZEN)
	@GetMapping("/frozen/{id}")
	@ResponseBody
	public AjaxResult frozen(@PathVariable String id){

		VipUser vipUser_new = vipUserService.selectVipUserById(Integer.parseInt(id));
		String isFrozen = vipUser_new.getIsFrozen();
		if(isFrozen == null){
			vipUser_new.setIsFrozen(Constants.NO);
		}else {
			if(isFrozen.equals(Constants.YES)){
				vipUser_new.setIsFrozen(Constants.NO);
			}
			if(isFrozen.equals(Constants.NO)){
				vipUser_new.setIsFrozen(Constants.YES);
			}
		}
		return toAjax(vipUserService.updateVipUser(vipUser_new));

	}


	/**
	 * 充值跳转
	 */
	@GetMapping("/recharge/{id}")
	public String recharge(@PathVariable String id, ModelMap modelMap){
		modelMap.put("id",id);
		return prefix + "/recharge";
	}

	/**
	 * 会员充值
	 */
	@RequiresPermissions("yishengxin:vipUser:recharge")
	@Log(title = "会员基本", businessType = BusinessType.RECHARGE)
	@PostMapping("/recharge")
	@ResponseBody
	public AjaxResult addRecharge(VipUser vipUser){
		Integer id = vipUser.getId();
		VipUser oldVip = vipUserService.selectVipUserById(id);
		String sslMoney = vipUser.getSslMoney();
		String hkdMoney = vipUser.getHkdMoney();
		if(sslMoney != null){
			if(!NumberUtil.isNumber(sslMoney) && !NumberUtil.isDouble(sslMoney) ){
				return error("请填写正确的数值");
			}
			BigDecimal ssl = NumberUtil.add(new String[]{sslMoney,oldVip.getSslMoney()});
			oldVip.setSslMoney(ssl.toString());
		}
		if(hkdMoney != null){
			if(!NumberUtil.isNumber(hkdMoney) && !NumberUtil.isDouble(hkdMoney)){
				return error("请填写正确的数值");
			}
			BigDecimal hkd = NumberUtil.add(new String[]{hkdMoney,oldVip.getHkdMoney()});
			oldVip.setHkdMoney(hkd.toString());
		}
		return toAjax(vipUserService.updateVipUser(oldVip));
	}


	/**
	 * 查询地址明细
	 */
	@RequiresPermissions("yishengxin:vipUser:address")
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Integer id, ModelMap mmap) {
		mmap.put("vipId",id);
		return "yishengxin/vipAddress/vipAddress";
	}
}
