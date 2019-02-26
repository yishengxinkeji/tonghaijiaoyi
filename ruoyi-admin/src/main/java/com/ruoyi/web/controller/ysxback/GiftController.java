package com.ruoyi.web.controller.ysxback;

import java.util.List;

import com.ruoyi.yishengxin.domain.Gift;
import com.ruoyi.yishengxin.service.IGiftService;
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
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 礼包设置 信息操作处理
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Controller
@RequestMapping("/yishengxin/gift")
public class GiftController extends BaseController {
    private String prefix = "yishengxin/gift";

    @Autowired
    private IGiftService giftService;

    @RequiresPermissions("yishengxin:gift:view")
    @GetMapping()
    public String gift() {
        return prefix + "/gift";
    }

    /**
     * 查询礼包设置列表
     */
    @RequiresPermissions("yishengxin:gift:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Gift gift) {
        startPage();
        List<Gift> list = giftService.selectGiftList(gift);
        return getDataTable(list);
    }


    /**
     * 导出礼包设置列表
     */
    @RequiresPermissions("yishengxin:gift:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Gift gift) {
        List<Gift> list = giftService.selectGiftList(gift);
        ExcelUtil<Gift> util = new ExcelUtil<Gift>(Gift.class);
        return util.exportExcel(list, "gift");
    }

    /**
     * 新增礼包设置
     */
    @GetMapping("/add")
    public String add() {
        List<Gift> gifts = giftService.selectGiftList(new Gift());
        if(gifts.size() > 0){
            return prefix + "/message";
        }

        return prefix + "/add";
    }

    /**
     * 新增保存礼包设置
     */
    @RequiresPermissions("yishengxin:gift:add")
    @Log(title = "礼包设置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Gift gift) {
        return toAjax(giftService.insertGift(gift));
    }

    /**
     * 修改礼包设置
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Gift gift = giftService.selectGiftById(id);
        mmap.put("gift", gift);
        return prefix + "/edit";
    }

    /**
     * 修改保存礼包设置
     */
    @RequiresPermissions("yishengxin:gift:edit")
    @Log(title = "礼包设置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Gift gift) {
        return toAjax(giftService.updateGift(gift));
    }

    /**
     * 删除礼包设置
     */
    @RequiresPermissions("yishengxin:gift:remove")
    @Log(title = "礼包设置", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(giftService.deleteGiftByIds(ids));
    }

}
