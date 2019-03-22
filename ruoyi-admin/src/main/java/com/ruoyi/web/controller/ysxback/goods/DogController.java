package com.ruoyi.web.controller.ysxback.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.goods.Dog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 狗 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-22
 */
@Controller
@RequestMapping("/yishengxin/dog")
public class DogController extends BaseController
{
    private String prefix = "yishengxin/dog";
	@RequiresPermissions("yishengxin:dog:view")
	@GetMapping()
	public String dog(){
	    return prefix + "/dog";
	}
	
	/**
	 * 查询狗列表
	 */
	@RequiresPermissions("yishengxin:dog:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Dog dog){
		startPage();
        List<Dog> list = null;
		return getDataTable(list);
	}
	
	
	/**
	 * 导出狗列表
	 */
	@RequiresPermissions("yishengxin:dog:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Dog dog){
    	List<Dog> list = null;


        ExcelUtil<Dog> util = new ExcelUtil<Dog>(Dog.class);
        return util.exportExcel(list, "dog");
    }
	
	/**
	 * 新增狗
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存狗
	 */
	@RequiresPermissions("yishengxin:dog:add")
	@Log(title = "狗", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Dog dog){
	    dog.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(1);
	}

	/**
	 * 修改狗
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){



		Dog dog1 = new Dog();
		dog1.setId(id);
		dog1.setName(id+"");
		dog1.setBetdfa(id +"");
		mmap.put("dog", dog1);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存狗
	 */
	@RequiresPermissions("yishengxin:dog:edit")
	@Log(title = "狗", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Dog dog){
	    dog.setUpdateBy(ShiroUtils.getLoginName());
		System.out.println(dog.getName());
		return toAjax(1);
	}
	
	/**
	 * 删除狗
	 */
	@RequiresPermissions("yishengxin:dog:remove")
	@Log(title = "狗", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(1);
	}
	
}
