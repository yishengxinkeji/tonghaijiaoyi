package com.ruoyi.web.controller.ysxback.goods;

import java.util.List;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.goods.GoodsSalesreturn;
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
import com.ruoyi.yishengxin.service.IGoodsSalesreturnService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品退货 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-11
 */
@Controller
@RequestMapping("/yishengxin/goodsSalesreturn")
public class GoodsSalesreturnBackController extends BaseController
{
    private String prefix = "yishengxin/goodsSalesreturn";
	
	@Autowired
	private IGoodsSalesreturnService goodsSalesreturnService;
	
	@RequiresPermissions("yishengxin:goodsSalesreturn:view")
	@GetMapping()
	public String goodsSalesreturn(){
	    return prefix + "/goodsSalesreturn";
	}
	
	/**
	 * 查询商品退货列表
	 */
	@RequiresPermissions("yishengxin:goodsSalesreturn:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsSalesreturn goodsSalesreturn){
		startPage();
        List<GoodsSalesreturn> list = goodsSalesreturnService.selectGoodsSalesreturnList(goodsSalesreturn);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品退货列表
	 */
	@RequiresPermissions("yishengxin:goodsSalesreturn:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsSalesreturn goodsSalesreturn){
    	List<GoodsSalesreturn> list = goodsSalesreturnService.selectGoodsSalesreturnList(goodsSalesreturn);
        ExcelUtil<GoodsSalesreturn> util = new ExcelUtil<GoodsSalesreturn>(GoodsSalesreturn.class);
        return util.exportExcel(list, "goodsSalesreturn");
    }
	
	/**
	 * 新增商品退货
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品退货
	 */
	@RequiresPermissions("yishengxin:goodsSalesreturn:add")
	@Log(title = "商品退货", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsSalesreturn goodsSalesreturn){
	    goodsSalesreturn.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(goodsSalesreturnService.insertGoodsSalesreturn(goodsSalesreturn));
	}

	/**
	 * 修改商品退货
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		GoodsSalesreturn goodsSalesreturn = goodsSalesreturnService.selectGoodsSalesreturnById(id);
		mmap.put("goodsSalesreturn", goodsSalesreturn);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品退货
	 */
	@RequiresPermissions("yishengxin:goodsSalesreturn:edit")
	@Log(title = "商品退货", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsSalesreturn goodsSalesreturn){
	    goodsSalesreturn.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(goodsSalesreturnService.updateGoodsSalesreturn(goodsSalesreturn));
	}
	
	/**
	 * 删除商品退货
	 */
	@RequiresPermissions("yishengxin:goodsSalesreturn:remove")
	@Log(title = "商品退货", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(goodsSalesreturnService.deleteGoodsSalesreturnByIds(ids));
	}
	
}
