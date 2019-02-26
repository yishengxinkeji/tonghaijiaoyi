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
import com.ruoyi.yishengxin.domain.Distribution;
import com.ruoyi.yishengxin.service.IDistributionService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 分销设置 信息操作处理
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Controller
@RequestMapping("/yishengxin/distribution")
public class DistributionController extends BaseController {
    private String prefix = "yishengxin/distribution";

    @Autowired
    private IDistributionService distributionService;

    @RequiresPermissions("yishengxin:distribution:view")
    @GetMapping()
    public String distribution() {
        return prefix + "/distribution";
    }

    /**
     * 查询分销设置列表
     */
    @RequiresPermissions("yishengxin:distribution:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Distribution distribution) {
        startPage();
        List<Distribution> list = distributionService.selectDistributionList(distribution);
        return getDataTable(list);
    }


    /**
     * 导出分销设置列表
     */
    @RequiresPermissions("yishengxin:distribution:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Distribution distribution) {
        List<Distribution> list = distributionService.selectDistributionList(distribution);
        ExcelUtil<Distribution> util = new ExcelUtil<Distribution>(Distribution.class);
        return util.exportExcel(list, "distribution");
    }

    /**
     * 新增分销设置
     */
    @GetMapping("/add")
    public String add() {
        List<Distribution> distributions = distributionService.selectDistributionList(new Distribution());
        if(distributions.size() > 0){
            return prefix + "/message";
        }
        return prefix + "/add";
    }

    /**
     * 新增保存分销设置
     */
    @RequiresPermissions("yishengxin:distribution:add")
    @Log(title = "分销设置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Distribution distribution) {
        distribution.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(distributionService.insertDistribution(distribution));
    }

    /**
     * 修改分销设置
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Distribution distribution = distributionService.selectDistributionById(id);
        mmap.put("distribution", distribution);
        return prefix + "/edit";
    }

    /**
     * 修改保存分销设置
     */
    @RequiresPermissions("yishengxin:distribution:edit")
    @Log(title = "分销设置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Distribution distribution) {
        distribution.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(distributionService.updateDistribution(distribution));
    }

    /**
     * 删除分销设置
     */
    @RequiresPermissions("yishengxin:distribution:remove")
    @Log(title = "分销设置", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(distributionService.deleteDistributionByIds(ids));
    }

}
