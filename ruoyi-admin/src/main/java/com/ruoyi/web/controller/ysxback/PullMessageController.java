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
import com.ruoyi.yishengxin.domain.PullMessage;
import com.ruoyi.yishengxin.service.IPullMessageService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 推送消息 信息操作处理
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Controller
@RequestMapping("/yishengxin/pullMessage")
public class PullMessageController extends BaseController {
    private String prefix = "yishengxin/pullMessage";

    @Autowired
    private IPullMessageService pullMessageService;

    @RequiresPermissions("yishengxin:pullMessage:view")
    @GetMapping()
    public String pullMessage() {
        return prefix + "/pullMessage";
    }

    /**
     * 查询推送消息列表
     */
    @RequiresPermissions("yishengxin:pullMessage:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PullMessage pullMessage) {
        startPage();
        List<PullMessage> list = pullMessageService.selectPullMessageList(pullMessage);
        return getDataTable(list);
    }


    /**
     * 导出推送消息列表
     */
    @RequiresPermissions("yishengxin:pullMessage:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PullMessage pullMessage) {
        List<PullMessage> list = pullMessageService.selectPullMessageList(pullMessage);
        ExcelUtil<PullMessage> util = new ExcelUtil<PullMessage>(PullMessage.class);
        return util.exportExcel(list, "pullMessage");
    }

    /**
     * 新增推送消息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存推送消息
     */
    @RequiresPermissions("yishengxin:pullMessage:add")
    @Log(title = "推送消息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PullMessage pullMessage) {
        pullMessage.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(pullMessageService.insertPullMessage(pullMessage));
    }

    /**
     * 修改推送消息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        PullMessage pullMessage = pullMessageService.selectPullMessageById(id);
        mmap.put("pullMessage", pullMessage);
        return prefix + "/edit";
    }

    /**
     * 修改保存推送消息
     */
    @RequiresPermissions("yishengxin:pullMessage:edit")
    @Log(title = "推送消息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PullMessage pullMessage) {
        pullMessage.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(pullMessageService.updatePullMessage(pullMessage));
    }

    /**
     * 删除推送消息
     */
    @RequiresPermissions("yishengxin:pullMessage:remove")
    @Log(title = "推送消息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(pullMessageService.deletePullMessageByIds(ids));
    }

}
