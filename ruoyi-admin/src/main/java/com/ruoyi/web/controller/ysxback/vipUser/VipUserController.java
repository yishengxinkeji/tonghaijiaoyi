package com.ruoyi.web.controller.ysxback.vipUser;

import java.math.BigDecimal;
import java.util.*;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.CustomerLogType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.CustomerLog;
import com.ruoyi.yishengxin.service.ICustomerLogService;
import io.swagger.models.auth.In;
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
import org.springframework.web.multipart.MultipartFile;

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
	@Autowired
	private ICustomerLogService iCustomerLogService;
	
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
	 * 特殊会员
	 * @param
	 * @return
	 */
	@Log(title = "特殊会员", businessType = BusinessType.SPECIAL)
	@GetMapping("/special/{id}")
	@ResponseBody
	public AjaxResult special(@PathVariable String id){

		VipUser vipUser_new = vipUserService.selectVipUserById(Integer.parseInt(id));
		String special = vipUser_new.getSpecial();

		if(special.equals(Constants.YES)){
			vipUser_new.setSpecial(Constants.NO);
		}
		if(special.equals(Constants.NO)){
			vipUser_new.setSpecial(Constants.YES);
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
	public AjaxResult addRecharge(String id,String sslMoney,String hkdMoney,String reason){
		VipUser oldVip = vipUserService.selectVipUserById(Integer.parseInt(id));

		if(sslMoney != null && !"".equals(sslMoney)){
			if(!NumberUtil.isNumber(sslMoney) && !NumberUtil.isDouble(sslMoney) ){
				return error("请填写正确的数值");
			}
			oldVip.setSslMoney(String.valueOf(NumberUtil.add(Double.parseDouble(sslMoney),Double.parseDouble(oldVip.getSslMoney()))));
		}
		if(hkdMoney != null && !"".equals(hkdMoney)){
			if(!NumberUtil.isNumber(hkdMoney) && !NumberUtil.isDouble(hkdMoney)){
				return error("请填写正确的数值");
			}
			oldVip.setHkdMoney(String.valueOf(NumberUtil.add(Double.parseDouble(hkdMoney),Double.parseDouble(oldVip.getHkdMoney()))));
		}

		if(vipUserService.updateVipUser(oldVip) > 0){
			CustomerLog customerLog = new CustomerLog();
			customerLog.setCreateBy(ShiroUtils.getLoginName());
			customerLog.setSslMoney(sslMoney);
			customerLog.setHkdMoney(hkdMoney);
			customerLog.setLogType(CustomerLogType.RECHARGE.getCode());	//扣除类型
			customerLog.setNickname(oldVip.getNickname());
			customerLog.setPhone(oldVip.getPhone());
			customerLog.setReason(reason);
			customerLog.setVipId(String.valueOf(oldVip.getId()));

			return toAjax(iCustomerLogService.insertCustomerLog(customerLog));
		}
		return success();
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


	/**
	 * 去扣除该用户金额
	 */
	@GetMapping("/reduce/{id}")
	public String toReduce(@PathVariable("id") Integer id, ModelMap mmap) {
		mmap.put("vipId",id);
		return prefix + "/reduce";
	}

	/**
	 * 扣除该用户金额
	 */
	@RequiresPermissions("yishengxin:vipUser:reduce")
	@PostMapping("/reduce")
	@ResponseBody
	public AjaxResult reduce(String id,String sslMoney,String hkdMoney,String reason) {

		VipUser vipUser = vipUserService.selectVipUserById(Integer.parseInt(id));

		double vsslMoney = Double.parseDouble(vipUser.getSslMoney());
		double vhkdMoney = Double.parseDouble(vipUser.getHkdMoney());

		if(sslMoney != null && !"".equals(sslMoney)){
			if(!NumberUtil.isNumber(sslMoney) && !NumberUtil.isDouble(sslMoney) ){
				return error("请填写正确的数值");
			}
			if(vsslMoney < Double.parseDouble(sslMoney)){
				return error("账户余额不够扣除");
			}

			vipUser.setSslMoney(String.valueOf(NumberUtil.sub(vsslMoney,Double.parseDouble(sslMoney))));
		}
		if(hkdMoney != null && !"".equals(hkdMoney)){
			if(!NumberUtil.isNumber(hkdMoney) && !NumberUtil.isDouble(hkdMoney)){
				return error("请填写正确的数值");
			}

			if(vhkdMoney < Double.parseDouble(hkdMoney)){
				return error("账户余额不够扣除");
			}
			vipUser.setHkdMoney(String.valueOf(NumberUtil.sub(vhkdMoney,Double.parseDouble(hkdMoney))));
		}

		if(vipUserService.updateVipUser(vipUser) > 0){
			CustomerLog customerLog = new CustomerLog();
			customerLog.setCreateBy(ShiroUtils.getLoginName());
			customerLog.setSslMoney(sslMoney);
			customerLog.setHkdMoney(hkdMoney);
			customerLog.setLogType(CustomerLogType.REDUCE.getCode());	//扣除类型
			customerLog.setNickname(vipUser.getNickname());
			customerLog.setPhone(vipUser.getPhone());
			customerLog.setReason(reason);
			customerLog.setVipId(String.valueOf(vipUser.getId()));

			iCustomerLogService.insertCustomerLog(customerLog);
			return success();
		}
		return success();
	}


	/**
	 * 查询记录
	 */
	@RequiresPermissions("yishengxin:vipUser:log")
	@GetMapping("/rechRecord/{id}")
	public String rechRecord(@PathVariable("id") Integer id, ModelMap mmap) {
		mmap.put("vipId",id);
		return "yishengxin/customerLog/customerLog";
	}

	/**
	 * 用户根据时间查询会员注册情况
	 * @param day	权重最高
	 * @param month	次之
	 * @param year	最低
	 * @return
	 */
	@RequestMapping("/timeSearch")
	@ResponseBody
	public AjaxResult timeSearch(String day,String month,String year){

		VipUser vipUser = new VipUser();

		int number = 0;
		DateTime begin = DateUtil.beginOfDay(new Date());
		DateTime end = DateUtil.endOfDay(new Date());
		if(!"".equals(day)){
			begin = DateUtil.beginOfDay(DateUtils.parseDate(day));
			end = DateUtil.endOfDay(DateUtils.parseDate(day));
		}else if("".equals(day) && !"".equals(month)){
			begin = DateUtil.beginOfMonth(DateUtils.parseDate(month));
			end = DateUtil.endOfMonth(DateUtils.parseDate(month));
		}else if(!"".equals(year) && "".equals(month) && "".equals(month)){
			begin = DateUtil.beginOfYear(DateUtils.parseDate(year+"-01"));
			end = DateUtil.endOfYear(DateUtils.parseDate(year+"-01"));
		}

		vipUser.setCreateTime(begin);
		vipUser.setUpdateTime(end);
		number = vipUserService.selectCount(vipUser);

		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.put("number",number);
		return ajaxResult;
	}

	/**
	 * 查询记录
	 */
	@RequiresPermissions("yishengxin:vipUser:recommend")
	@GetMapping("/recommend/{id}")
	public String toRecommend(@PathVariable("id") Integer id, ModelMap mmap) {
		VipUser vipUser = vipUserService.selectVipUserById(id);
		mmap.put("parentCode",vipUser.getParentCode());
		mmap.put("recommendCode",vipUser.getRecommendCode());
		return prefix+"/recommend";
	}

	/**
	 * 会员推荐情况
	 */
	@PostMapping("/recommend")
	@ResponseBody
	public TableDataInfo recommend(VipUser vipUser){
		startPage();

		List<Map> list = new ArrayList();
		List<VipUser> vipUsers = new ArrayList<>();
		if(!vipUser.getParentCode().equalsIgnoreCase("-1")){
			VipUser vipUser2 = new VipUser();
			vipUser2.setRecommendCode(vipUser.getParentCode());
			//查询其父一级
			vipUsers = vipUserService.selectVipUserList(vipUser2);
		}

		VipUser vipUser3 = new VipUser();
		vipUser3.setParentCode(vipUser.getRecommendCode());
		//查询自己下面的人
		List<VipUser> list1 = vipUserService.selectVipUserList(vipUser3);
		if(vipUsers.size() > 0){
			Map map = new HashMap();
			map.put("id",vipUsers.get(0).getId());
			map.put("phone",vipUsers.get(0).getPhone());
			map.put("nickname",vipUsers.get(0).getNickname());
			map.put("recommendCode",vipUsers.get(0).getRecommendCode());
			map.put("invi","推荐人");
			list.add(map);
		}
		for(VipUser vipUser1 : list1){
			Map map = new HashMap();
			map.put("id",vipUser1.getId());
			map.put("phone",vipUser1.getPhone());
			map.put("nickname",vipUser1.getNickname());
			map.put("recommendCode",vipUser1.getRecommendCode());
			map.put("invi","被推荐人");
			list.add(map);
		}


		return getDataTable(list);
	}

	@Log(title = "用户身份上传", businessType = BusinessType.UPDATE)
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
