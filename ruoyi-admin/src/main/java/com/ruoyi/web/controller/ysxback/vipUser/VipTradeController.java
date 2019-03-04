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
import com.ruoyi.yishengxin.domain.vipUser.VipTrade;
import com.ruoyi.yishengxin.service.IVipTradeService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 交易记录 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-04
 */
@Controller
@RequestMapping("/yishengxin/vipTrade")
public class VipTradeController extends BaseController {
    private String prefix = "yishengxin/vipTrade";

    @Autowired
    private IVipTradeService vipTradeService;

    @RequiresPermissions("yishengxin:vipTrade:view")
    @GetMapping()
    public String vipTrade() {
        return prefix + "/vipTrade";
    }

    /**
     * 查询交易记录列表
     */
    @RequiresPermissions("yishengxin:vipTrade:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VipTrade vipTrade) {
        startPage();
        List<VipTrade> list = vipTradeService.selectVipTradeList(vipTrade);
        return getDataTable(list);
    }


    /**
     * 导出交易记录列表
     */
    @RequiresPermissions("yishengxin:vipTrade:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipTrade vipTrade) {
        List<VipTrade> list = vipTradeService.selectVipTradeList(vipTrade);
        ExcelUtil<VipTrade> util = new ExcelUtil<VipTrade>(VipTrade.class);
        return util.exportExcel(list, "vipTrade");
    }

    /**
     * 新增交易记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存交易记录
     */
    @RequiresPermissions("yishengxin:vipTrade:add")
    @Log(title = "交易记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VipTrade vipTrade) {
        vipTrade.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(vipTradeService.insertVipTrade(vipTrade));
    }

    /**
     * 修改交易记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        VipTrade vipTrade = vipTradeService.selectVipTradeById(id);
        mmap.put("vipTrade", vipTrade);
        return prefix + "/edit";
    }

    /**
     * 修改保存交易记录
     */
    @RequiresPermissions("yishengxin:vipTrade:edit")
    @Log(title = "交易记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(VipTrade vipTrade) {
        vipTrade.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(vipTradeService.updateVipTrade(vipTrade));
    }

    /**
     * 删除交易记录
     */
    @RequiresPermissions("yishengxin:vipTrade:remove")
    @Log(title = "交易记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(vipTradeService.deleteVipTradeByIds(ids));
    }

}
