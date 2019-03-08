package com.ruoyi.web.controller.ysxback.vipUser;

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
import com.ruoyi.yishengxin.domain.vipUser.VipAppeal;
import com.ruoyi.yishengxin.service.IVipAppealService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 我的申诉 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-08
 */
@Controller
@RequestMapping("/yishengxin/vipAppeal")
public class VipAppealController extends BaseController {
    private String prefix = "yishengxin/vipAppeal";

    @Autowired
    private IVipAppealService vipAppealService;

    @RequiresPermissions("yishengxin:vipAppeal:view")
    @GetMapping()
    public String vipAppeal() {
        return prefix + "/vipAppeal";
    }

    /**
     * 查询我的申诉列表
     */
    @RequiresPermissions("yishengxin:vipAppeal:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VipAppeal vipAppeal) {
        startPage();
        List<VipAppeal> list = vipAppealService.selectVipAppealList(vipAppeal);
        return getDataTable(list);
    }


    /**
     * 导出我的申诉列表
     */
    @RequiresPermissions("yishengxin:vipAppeal:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipAppeal vipAppeal) {
        List<VipAppeal> list = vipAppealService.selectVipAppealList(vipAppeal);
        ExcelUtil<VipAppeal> util = new ExcelUtil<VipAppeal>(VipAppeal.class);
        return util.exportExcel(list, "vipAppeal");
    }

    /**
     * 新增我的申诉
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存我的申诉
     */
    @RequiresPermissions("yishengxin:vipAppeal:add")
    @Log(title = "我的申诉", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VipAppeal vipAppeal) {
        vipAppeal.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(vipAppealService.insertVipAppeal(vipAppeal));
    }

    /**
     * 修改我的申诉
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        VipAppeal vipAppeal = vipAppealService.selectVipAppealById(id);
        mmap.put("vipAppeal", vipAppeal);
        return prefix + "/edit";
    }

    /**
     * 修改保存我的申诉
     */
    @RequiresPermissions("yishengxin:vipAppeal:edit")
    @Log(title = "我的申诉", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(VipAppeal vipAppeal) {
        vipAppeal.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(vipAppealService.updateVipAppeal(vipAppeal));
    }

    /**
     * 删除我的申诉
     */
    @RequiresPermissions("yishengxin:vipAppeal:remove")
    @Log(title = "我的申诉", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(vipAppealService.deleteVipAppealByIds(ids));
    }

}
