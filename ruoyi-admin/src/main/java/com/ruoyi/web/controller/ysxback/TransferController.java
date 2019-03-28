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
import com.ruoyi.yishengxin.domain.Transfer;
import com.ruoyi.yishengxin.service.ITransferService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 转入转出说明 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-28
 */
@Controller
@RequestMapping("/yishengxin/transfer")
public class TransferController extends BaseController {
    private String prefix = "yishengxin/transfer";

    @Autowired
    private ITransferService transferService;

    @RequiresPermissions("yishengxin:transfer:view")
    @GetMapping()
    public String transfer() {
        return prefix + "/transfer";
    }

    /**
     * 查询转入转出说明列表
     */
    @RequiresPermissions("yishengxin:transfer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Transfer transfer) {
        startPage();
        List<Transfer> list = transferService.selectTransferList(transfer);
        return getDataTable(list);
    }


    /**
     * 导出转入转出说明列表
     */
    @RequiresPermissions("yishengxin:transfer:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Transfer transfer) {
        List<Transfer> list = transferService.selectTransferList(transfer);
        ExcelUtil<Transfer> util = new ExcelUtil<Transfer>(Transfer.class);
        return util.exportExcel(list, "transfer");
    }

    /**
     * 新增转入转出说明
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存转入转出说明
     */
    @RequiresPermissions("yishengxin:transfer:add")
    @Log(title = "转入转出说明", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Transfer transfer) {
        transfer.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(transferService.insertTransfer(transfer));
    }

    /**
     * 修改转入转出说明
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Transfer transfer = transferService.selectTransferById(id);
        mmap.put("transfer", transfer);
        return prefix + "/edit";
    }

    /**
     * 修改保存转入转出说明
     */
    @RequiresPermissions("yishengxin:transfer:edit")
    @Log(title = "转入转出说明", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Transfer transfer) {
        transfer.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(transferService.updateTransfer(transfer));
    }

    /**
     * 删除转入转出说明
     */
    @RequiresPermissions("yishengxin:transfer:remove")
    @Log(title = "转入转出说明", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(transferService.deleteTransferByIds(ids));
    }

}
