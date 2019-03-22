package com.ruoyi.web.controller.ysxback.goods;

import java.util.List;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.goods.GoodsEvaluation;
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

import com.ruoyi.yishengxin.service.IGoodsEvaluationService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品评价 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-19
 */
@Controller
@RequestMapping("/yishengxin/goodsEvaluation")
public class GoodsEvaluationBackController extends BaseController
{
    private String prefix = "yishengxin/goodsEvaluation";
	
	@Autowired
	private IGoodsEvaluationService goodsEvaluationService;
	
	@RequiresPermissions("yishengxin:goodsEvaluation:view")
	@GetMapping()
	public String goodsEvaluation(){
	    return prefix + "/goodsEvaluation";
	}
	
	/**
	 * 查询商品评价列表
	 */
	@RequiresPermissions("yishengxin:goodsEvaluation:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsEvaluation goodsEvaluation){
		startPage();
        List<GoodsEvaluation> list = goodsEvaluationService.selectGoodsEvaluationList(goodsEvaluation);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品评价列表
	 */
	@RequiresPermissions("yishengxin:goodsEvaluation:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsEvaluation goodsEvaluation){
    	List<GoodsEvaluation> list = goodsEvaluationService.selectGoodsEvaluationList(goodsEvaluation);
        ExcelUtil<GoodsEvaluation> util = new ExcelUtil<GoodsEvaluation>(GoodsEvaluation.class);
        return util.exportExcel(list, "goodsEvaluation");
    }
	
	/**
	 * 新增商品评价
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品评价
	 */
	@RequiresPermissions("yishengxin:goodsEvaluation:add")
	@Log(title = "商品评价", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsEvaluation goodsEvaluation){
	    goodsEvaluation.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(goodsEvaluationService.insertGoodsEvaluation(goodsEvaluation));
	}

	/**
	 * 修改商品评价
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		GoodsEvaluation goodsEvaluation = goodsEvaluationService.selectGoodsEvaluationById(id);
	//	goodsEvaluation.getParams().put()
		mmap.put("goodsEvaluation", goodsEvaluation);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品评价
	 */
	@RequiresPermissions("yishengxin:goodsEvaluation:edit")
	@Log(title = "商品评价", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsEvaluation goodsEvaluation){
	    goodsEvaluation.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(goodsEvaluationService.updateGoodsEvaluation(goodsEvaluation));
	}
	
	/**
	 * 删除商品评价
	 */
	@RequiresPermissions("yishengxin:goodsEvaluation:remove")
	@Log(title = "商品评价", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(goodsEvaluationService.deleteGoodsEvaluationByIds(ids));
	}
	
}
