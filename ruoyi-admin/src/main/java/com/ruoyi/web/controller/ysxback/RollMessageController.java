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
import com.ruoyi.yishengxin.domain.RollMessage;
import com.ruoyi.yishengxin.service.IRollMessageService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 滚动消息 信息操作处理
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Controller
@RequestMapping("/yishengxin/rollMessage")
public class RollMessageController extends BaseController {
    private String prefix = "yishengxin/rollMessage";

    @Autowired
    private IRollMessageService rollMessageService;

    @RequiresPermissions("yishengxin:rollMessage:view")
    @GetMapping()
    public String rollMessage() {
        return prefix + "/rollMessage";
    }

    /**
     * 查询滚动消息列表
     */
    @RequiresPermissions("yishengxin:rollMessage:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RollMessage rollMessage) {
        startPage();
        List<RollMessage> list = rollMessageService.selectRollMessageList(rollMessage);
        return getDataTable(list);
    }


    /**
     * 导出滚动消息列表
     */
    @RequiresPermissions("yishengxin:rollMessage:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RollMessage rollMessage) {
        List<RollMessage> list = rollMessageService.selectRollMessageList(rollMessage);
        ExcelUtil<RollMessage> util = new ExcelUtil<RollMessage>(RollMessage.class);
        return util.exportExcel(list, "rollMessage");
    }

    /**
     * 新增滚动消息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存滚动消息
     */
    @RequiresPermissions("yishengxin:rollMessage:add")
    @Log(title = "滚动消息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RollMessage rollMessage) {
        rollMessage.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(rollMessageService.insertRollMessage(rollMessage));
    }

    /**
     * 修改滚动消息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        RollMessage rollMessage = rollMessageService.selectRollMessageById(id);
        mmap.put("rollMessage", rollMessage);
        return prefix + "/edit";
    }

    /**
     * 修改保存滚动消息
     */
    @RequiresPermissions("yishengxin:rollMessage:edit")
    @Log(title = "滚动消息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RollMessage rollMessage) {
        rollMessage.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(rollMessageService.updateRollMessage(rollMessage));
    }

    /**
     * 删除滚动消息
     */
    @RequiresPermissions("yishengxin:rollMessage:remove")
    @Log(title = "滚动消息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(rollMessageService.deleteRollMessageByIds(ids));
    }

}
