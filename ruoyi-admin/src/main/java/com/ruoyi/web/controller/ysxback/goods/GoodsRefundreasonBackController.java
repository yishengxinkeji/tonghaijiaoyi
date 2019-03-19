package com.ruoyi.web.controller.ysxback.goods;

import java.util.Date;
import java.util.List;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.goods.GoodsRefundreason;
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
import com.ruoyi.yishengxin.service.IGoodsRefundreasonService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 退款原因 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-19
 */
@Controller
@RequestMapping("/yishengxin/goodsRefundreason")
public class GoodsRefundreasonBackController extends BaseController
{
    private String prefix = "yishengxin/goodsRefundreason";
	
	@Autowired
	private IGoodsRefundreasonService goodsRefundreasonService;
	
	@RequiresPermissions("yishengxin:goodsRefundreason:view")
	@GetMapping()
	public String goodsRefundreason(){
	    return prefix + "/goodsRefundreason";
	}
	
	/**
	 * 查询退款原因列表
	 */
	@RequiresPermissions("yishengxin:goodsRefundreason:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsRefundreason goodsRefundreason){
		startPage();
        List<GoodsRefundreason> list = goodsRefundreasonService.selectGoodsRefundreasonList(goodsRefundreason);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出退款原因列表
	 */
	@RequiresPermissions("yishengxin:goodsRefundreason:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsRefundreason goodsRefundreason){
    	List<GoodsRefundreason> list = goodsRefundreasonService.selectGoodsRefundreasonList(goodsRefundreason);
        ExcelUtil<GoodsRefundreason> util = new ExcelUtil<GoodsRefundreason>(GoodsRefundreason.class);
        return util.exportExcel(list, "goodsRefundreason");
    }
	
	/**
	 * 新增退款原因
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存退款原因
	 */
	@RequiresPermissions("yishengxin:goodsRefundreason:add")
	@Log(title = "退款原因", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsRefundreason goodsRefundreason){
		goodsRefundreason.setCreateTime(new Date());
	    goodsRefundreason.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(goodsRefundreasonService.insertGoodsRefundreason(goodsRefundreason));
	}

	/**
	 * 修改退款原因
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		GoodsRefundreason goodsRefundreason = goodsRefundreasonService.selectGoodsRefundreasonById(id);
		mmap.put("goodsRefundreason", goodsRefundreason);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存退款原因
	 */
	@RequiresPermissions("yishengxin:goodsRefundreason:edit")
	@Log(title = "退款原因", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsRefundreason goodsRefundreason){
		goodsRefundreason.setUpdateTime(new Date());
	    goodsRefundreason.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(goodsRefundreasonService.updateGoodsRefundreason(goodsRefundreason));
	}
	
	/**
	 * 删除退款原因
	 */
	@RequiresPermissions("yishengxin:goodsRefundreason:remove")
	@Log(title = "退款原因", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(goodsRefundreasonService.deleteGoodsRefundreasonByIds(ids));
	}
	
}
