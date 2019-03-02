package com.ruoyi.web.controller.ysxfront.vipuser;

import java.util.List;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.VipUser;
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
import com.ruoyi.yishengxin.domain.VipAddress;
import com.ruoyi.yishengxin.service.IVipAddressService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 会员地址 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-01
 */
@Controller
@RequestMapping("//vipAddress")
public class VipAddressController extends BaseFrontController
{

	@Autowired
	private IVipAddressService vipAddressService;

	
	/**
	 * 查询会员地址列表
	 */

	@PostMapping("/list")
	@ResponseBody
	public ResponseResult list(String token){
		// 校验登录状态
		VipUser vipUser = userExist(token);

		if (vipUser == null){
			return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);

		}
		//校验传参
		if (null == token || "".equals(token)) {
			return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
		}

		Integer id = vipUser.getId();
		VipAddress vipAddress = new VipAddress();
		vipAddress.setVipId(id);
		List<VipAddress> list = vipAddressService.selectVipAddressList(vipAddress);
//		for (int i = 0; i < list.size(); i++) {
//			VipAddress vipAddress1 = list.get(i);
//			String isDefault = vipAddress.getIsDefault();
//			 if(isDefault.toLowerCase().equals("y")){
//
//			 }
//
//		}
		if (list.size() == 0){
			return ResponseResult.responseResult(ResponseEnum.SUCCESS, "数据为空");
		}

		return ResponseResult.responseResult(ResponseEnum.SUCCESS, list);
	}
	
	/**
	 * 新增保存会员地址
	 */

	@PostMapping("/add")
	@ResponseBody
	public ResponseResult addSave(String token,VipAddress vipAddress){
		// 校验登录状态
		VipUser vipUser = userExist(token);

		if (vipUser == null){
			return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);

		}
		//校验传参
		if (null == token || "".equals(token)) {
			return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
		}

		int i = vipAddressService.insertVipAddress(vipAddress);

		if ( i >  0){
			return ResponseResult.responseResult(ResponseEnum.SUCCESS);
		}

		return null;
	}


	/**
	 * 修改保存会员地址
	 */

	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipAddress vipAddress){
	   return null;
	}
	
	/**
	 * 删除会员地址
	 */

	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){


		return null;
	}
	
}
