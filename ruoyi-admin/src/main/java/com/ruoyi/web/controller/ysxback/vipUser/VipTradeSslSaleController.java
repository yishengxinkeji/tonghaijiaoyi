package com.ruoyi.web.controller.ysxback.vipUser;

import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslBuy;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslSale;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IVipTradeHkdBuyService;
import com.ruoyi.yishengxin.service.IVipTradeHkdSaleService;
import com.ruoyi.yishengxin.service.IVipTradeSslBuyService;
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
import com.ruoyi.yishengxin.service.IVipTradeSslSaleService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 挂卖SSL 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-06
 */
@Controller
@RequestMapping("/yishengxin/vipTradeSslSale")
public class VipTradeSslSaleController extends BaseController
{
    private String prefix = "yishengxin/vipTradeSslSale";
	
	@Autowired
	private IVipTradeSslSaleService vipTradeSaleService;
	@Autowired
	private IVipTradeSslBuyService vipTradeSslBuyService;
	@Autowired
	private IVipTradeHkdSaleService vipTradeHkdSaleService;
	@Autowired
	private IVipTradeSslSaleService vipTradeSslSaleService;
	@Autowired
	private IVipTradeHkdBuyService vipTradeHkdBuyService;
	
	@RequiresPermissions("yishengxin:vipTradeSale:view")
	@GetMapping()
	public String vipTradeSale(){
	    return prefix + "/vipTradeSslSale";
	}
	
	/**
	 * 查询挂卖SSL列表
	 */
	@RequiresPermissions("yishengxin:vipTradeSslSale:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VipTradeSslSale vipTradeSslSale){
		startPage();
        List<VipTradeSslSale> list = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出挂卖SSL列表
	 */
	@RequiresPermissions("yishengxin:vipTradeSslSale:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipTradeSslSale vipTradeSslSale){
    	List<VipTradeSslSale> list = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);
        ExcelUtil<VipTradeSslSale> util = new ExcelUtil<VipTradeSslSale>(VipTradeSslSale.class);
        return util.exportExcel(list, "vipTradeSslSale");
    }
	
	/**
	 * 新增挂卖SSL
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存挂卖SSL
	 */
	@RequiresPermissions("yishengxin:vipTradeSslSale:add")
	@Log(title = "挂卖SSL", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VipTradeSslSale vipTradeSslSale){
	    vipTradeSslSale.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(vipTradeSaleService.insertVipTradeSale(vipTradeSslSale));
	}

	/**
	 * 修改挂卖SSL
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		VipTradeSslSale vipTradeSslSale = vipTradeSaleService.selectVipTradeSaleById(id);
		mmap.put("vipTradeSslSale", vipTradeSslSale);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存挂卖SSL
	 */
	@RequiresPermissions("yishengxin:vipTradeSslSale:edit")
	@Log(title = "挂卖SSL", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VipTradeSslSale vipTradeSslSale){
	    vipTradeSslSale.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(vipTradeSaleService.updateVipTradeSale(vipTradeSslSale));
	}
	
	/**
	 * 删除挂卖SSL
	 */
	@RequiresPermissions("yishengxin:vipTradeSale:remove")
	@Log(title = "挂卖SSL", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(vipTradeSaleService.deleteVipTradeSaleByIds(ids));
	}

	/**
	 * 交易条件统计
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	@RequestMapping("/timeSearch")
	@ResponseBody
	public AjaxResult timeSearch(String day,String month,String year){

		VipUser vipUser = new VipUser();

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

		int sslBuy = vipTradeSslBuyService.selectSum(begin,end);
		int sslSale = vipTradeSslSaleService.selectSum(begin,end);
		int hkdBuy = vipTradeHkdBuyService.selectSum(begin,end);
		int hkdSale = vipTradeHkdSaleService.selectSum(begin,end);

		//由于买和卖其实交易成功的订单是一样的,所以只用统计一种就行(单价和 / 条数)
		double avg = vipTradeSslBuyService.selectAvgByDay(DateUtil.beginOfDay(new Date()),DateUtil.endOfDay(new Date()));

		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.put("sslBuy",sslBuy);
		ajaxResult.put("sslSale",sslSale);
		ajaxResult.put("hkdBuy",hkdBuy);
		ajaxResult.put("hkdSale",hkdSale);
		ajaxResult.put("avg",avg);
		return ajaxResult;
	}


}
