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
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.service.ITradeService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 交易设置 信息操作处理
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Controller
@RequestMapping("/yishengxin/trade")
public class TradeController extends BaseController {
    private String prefix = "yishengxin/trade";

    @Autowired
    private ITradeService tradeService;

    @RequiresPermissions("yishengxin:trade:view")
    @GetMapping()
    public String trade() {
        return prefix + "/trade";
    }

    /**
     * 查询交易设置列表
     */
    @RequiresPermissions("yishengxin:trade:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Trade trade) {
        startPage();
        List<Trade> list = tradeService.selectTradeList(trade);
        return getDataTable(list);
    }


    /**
     * 导出交易设置列表
     */
    @RequiresPermissions("yishengxin:trade:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Trade trade) {
        List<Trade> list = tradeService.selectTradeList(trade);
        ExcelUtil<Trade> util = new ExcelUtil<Trade>(Trade.class);
        return util.exportExcel(list, "trade");
    }

    /**
     * 新增交易设置
     */
    @GetMapping("/add")
    public String add() {
        List<Trade> trades = tradeService.selectTradeList(new Trade());
        if(trades.size() > 0){
            return prefix + "/message";
        }
        return prefix + "/add";
    }

    /**
     * 新增保存交易设置
     */
    @RequiresPermissions("yishengxin:trade:add")
    @Log(title = "交易设置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Trade trade) {
        return toAjax(tradeService.insertTrade(trade));
    }

    /**
     * 修改交易设置
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Trade trade = tradeService.selectTradeById(id);
        mmap.put("trade", trade);
        return prefix + "/edit";
    }

    /**
     * 修改保存交易设置
     */
    @RequiresPermissions("yishengxin:trade:edit")
    @Log(title = "交易设置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Trade trade) {
        return toAjax(tradeService.updateTrade(trade));
    }

    /**
     * 删除交易设置
     */
    @RequiresPermissions("yishengxin:trade:remove")
    @Log(title = "交易设置", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(tradeService.deleteTradeByIds(ids));
    }

}
