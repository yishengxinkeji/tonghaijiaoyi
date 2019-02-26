package com.ruoyi.web.controller.ysxback;

import java.util.List;

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
import com.ruoyi.yishengxin.domain.Customer;
import com.ruoyi.yishengxin.service.ICustomerService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 客服设置 信息操作处理
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Controller
@RequestMapping("/yishengxin/customer")
public class CustomerController extends BaseController {
    private String prefix = "yishengxin/customer";

    @Autowired
    private ICustomerService customerService;

    @RequiresPermissions("yishengxin:customer:view")
    @GetMapping()
    public String customer() {
        return prefix + "/customer";
    }

    /**
     * 查询客服设置列表
     */
    @RequiresPermissions("yishengxin:customer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Customer customer) {
        startPage();
        List<Customer> list = customerService.selectCustomerList(customer);
        return getDataTable(list);
    }


    /**
     * 导出客服设置列表
     */
    @RequiresPermissions("yishengxin:customer:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Customer customer) {
        List<Customer> list = customerService.selectCustomerList(customer);
        ExcelUtil<Customer> util = new ExcelUtil<Customer>(Customer.class);
        return util.exportExcel(list, "customer");
    }

    /**
     * 新增客服设置
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存客服设置
     */
    @RequiresPermissions("yishengxin:customer:add")
    @Log(title = "客服设置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Customer customer) {

        return toAjax(customerService.insertCustomer(customer));
    }

    /**
     * 修改客服设置
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Customer customer = customerService.selectCustomerById(id);
        mmap.put("customer", customer);
        return prefix + "/edit";
    }

    /**
     * 修改保存客服设置
     */
    @RequiresPermissions("yishengxin:customer:edit")
    @Log(title = "客服设置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Customer customer) {
        return toAjax(customerService.updateCustomer(customer));
    }

    /**
     * 删除客服设置
     */
    @RequiresPermissions("yishengxin:customer:remove")
    @Log(title = "客服设置", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(customerService.deleteCustomerByIds(ids));
    }

}
