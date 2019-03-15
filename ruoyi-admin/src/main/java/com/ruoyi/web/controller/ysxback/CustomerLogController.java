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
import com.ruoyi.yishengxin.domain.CustomerLog;
import com.ruoyi.yishengxin.service.ICustomerLogService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 客服操作会员记录 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-14
 */
@Controller
@RequestMapping("/yishengxin/customerLog")
public class CustomerLogController extends BaseController {
    private String prefix = "yishengxin/customerLog";

    @Autowired
    private ICustomerLogService customerLogService;

    @RequiresPermissions("yishengxin:customerLog:view")
    @GetMapping()
    public String customerLog() {
        return prefix + "/customerLog";
    }

    /**
     * 查询客服操作会员记录列表
     */
    @RequiresPermissions("yishengxin:customerLog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CustomerLog customerLog) {
        startPage();
        List<CustomerLog> list = customerLogService.selectCustomerLogList(customerLog);
        return getDataTable(list);
    }


    /**
     * 导出客服操作会员记录列表
     */
    @RequiresPermissions("yishengxin:customerLog:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CustomerLog customerLog) {
        List<CustomerLog> list = customerLogService.selectCustomerLogList(customerLog);
        ExcelUtil<CustomerLog> util = new ExcelUtil<CustomerLog>(CustomerLog.class);
        return util.exportExcel(list, "customerLog");
    }

    /**
     * 新增客服操作会员记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存客服操作会员记录
     */
    @RequiresPermissions("yishengxin:customerLog:add")
    @Log(title = "客服操作会员记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CustomerLog customerLog) {
        customerLog.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(customerLogService.insertCustomerLog(customerLog));
    }

    /**
     * 修改客服操作会员记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        CustomerLog customerLog = customerLogService.selectCustomerLogById(id);
        mmap.put("customerLog", customerLog);
        return prefix + "/edit";
    }

    /**
     * 修改保存客服操作会员记录
     */
    @RequiresPermissions("yishengxin:customerLog:edit")
    @Log(title = "客服操作会员记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CustomerLog customerLog) {
        customerLog.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(customerLogService.updateCustomerLog(customerLog));
    }

    /**
     * 删除客服操作会员记录
     */
    @RequiresPermissions("yishengxin:customerLog:remove")
    @Log(title = "客服操作会员记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(customerLogService.deleteCustomerLogByIds(ids));
    }

}
