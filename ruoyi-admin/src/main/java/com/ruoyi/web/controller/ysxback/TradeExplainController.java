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
import com.ruoyi.yishengxin.domain.TradeExplain;
import com.ruoyi.yishengxin.service.ITradeExplainService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 交易说明 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-05
 */
@Controller
@RequestMapping("/yishengxin/tradeExplain")
public class TradeExplainController extends BaseController
{
    private String prefix = "yishengxin/tradeExplain";
	
	@Autowired
	private ITradeExplainService tradeExplainService;
	
	@RequiresPermissions("yishengxin:tradeExplain:view")
	@GetMapping()
	public String tradeExplain(){
	    return prefix + "/tradeExplain";
	}
	
	/**
	 * 查询交易说明列表
	 */
	@RequiresPermissions("yishengxin:tradeExplain:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(TradeExplain tradeExplain){
		startPage();
        List<TradeExplain> list = tradeExplainService.selectTradeExplainList(tradeExplain);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出交易说明列表
	 */
	@RequiresPermissions("yishengxin:tradeExplain:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TradeExplain tradeExplain){
    	List<TradeExplain> list = tradeExplainService.selectTradeExplainList(tradeExplain);
        ExcelUtil<TradeExplain> util = new ExcelUtil<TradeExplain>(TradeExplain.class);
        return util.exportExcel(list, "tradeExplain");
    }
	
	/**
	 * 新增交易说明
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存交易说明
	 */
	@RequiresPermissions("yishengxin:tradeExplain:add")
	@Log(title = "交易说明", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(TradeExplain tradeExplain){
	    tradeExplain.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(tradeExplainService.insertTradeExplain(tradeExplain));
	}

	/**
	 * 修改交易说明
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		TradeExplain tradeExplain = tradeExplainService.selectTradeExplainById(id);
		mmap.put("tradeExplain", tradeExplain);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存交易说明
	 */
	@RequiresPermissions("yishengxin:tradeExplain:edit")
	@Log(title = "交易说明", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TradeExplain tradeExplain){
	    tradeExplain.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(tradeExplainService.updateTradeExplain(tradeExplain));
	}
	
	/**
	 * 删除交易说明
	 */
	@RequiresPermissions("yishengxin:tradeExplain:remove")
	@Log(title = "交易说明", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(tradeExplainService.deleteTradeExplainByIds(ids));
	}
	
}
