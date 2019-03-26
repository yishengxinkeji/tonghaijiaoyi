package com.ruoyi.web.controller.ysxback.goods;

import java.util.List;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.goods.GoodsShare;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IVipUserService;
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
import com.ruoyi.yishengxin.service.IGoodsShareService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品分享 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-11
 */
@Controller
@RequestMapping("/yishengxin/goodsShare")
public class GoodsShareBackController extends BaseController
{
    private String prefix = "yishengxin/goodsShare";
	
	@Autowired
	private IGoodsShareService goodsShareService;

	@Autowired
	private IVipUserService vipUserService;

	@RequiresPermissions("yishengxin:goodsShare:view")
	@GetMapping()
	public String goodsShare(){
	    return prefix + "/goodsShare";
	}
	
	/**
	 * 查询商品分享列表
	 */
	@RequiresPermissions("yishengxin:goodsShare:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsShare goodsShare){
		startPage();
        List<GoodsShare> list = goodsShareService.selectGoodsShareList(goodsShare);

		if (list.size() > 0) {
			for (int i = 0; i < list.size() ; i++) {
				Integer id = list.get(i).getUid();
				VipUser vipUser = vipUserService.selectVipUserById(id);
				String phone = vipUser.getPhone();
				list.get(i).setRemark(phone);
			}

		}
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品分享列表
	 */
	@RequiresPermissions("yishengxin:goodsShare:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsShare goodsShare){
    	List<GoodsShare> list = goodsShareService.selectGoodsShareList(goodsShare);



        ExcelUtil<GoodsShare> util = new ExcelUtil<GoodsShare>(GoodsShare.class);
        return util.exportExcel(list, "goodsShare");
    }
	
	/**
	 * 新增商品分享
	 */
	@GetMapping("/add")
	public String add(){
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品分享
	 */
	@RequiresPermissions("yishengxin:goodsShare:add")
	@Log(title = "商品分享", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsShare goodsShare){
	    goodsShare.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(goodsShareService.insertGoodsShare(goodsShare));
	}

	/**
	 * 修改商品分享
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		GoodsShare goodsShare = goodsShareService.selectGoodsShareById(id);
		mmap.put("goodsShare", goodsShare);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品分享
	 */
	@RequiresPermissions("yishengxin:goodsShare:edit")
	@Log(title = "商品分享", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsShare goodsShare){
	    goodsShare.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(goodsShareService.updateGoodsShare(goodsShare));
	}
	
	/**
	 * 删除商品分享
	 */
	@RequiresPermissions("yishengxin:goodsShare:remove")
	@Log(title = "商品分享", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(goodsShareService.deleteGoodsShareByIds(ids));
	}
	
}
