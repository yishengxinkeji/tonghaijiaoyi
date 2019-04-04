package com.ruoyi.web.controller.ysxback.vipUser;

import java.util.List;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.yishengxin.domain.vipUser.VipExchange;
import com.ruoyi.yishengxin.service.IVipExchangeService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 个人兑换 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-02
 */
@Controller
@RequestMapping("/yishengxin/vipExchange")
public class VipExchangeController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(VipExchangeController.class);

    private String prefix = "yishengxin/vipExchange";

    @Autowired
    private IVipExchangeService vipExchangeService;

    @RequiresPermissions("yishengxin:vipExchange:view")
    @GetMapping()
    public String vipExchange() {
        return prefix + "/vipExchange";
    }

    /**
     * 查询个人兑换列表
     */
    @RequiresPermissions("yishengxin:vipExchange:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VipExchange vipExchange) {
        startPage();
        List<VipExchange> list = vipExchangeService.selectVipExchangeList(vipExchange);
        return getDataTable(list);
    }


    /**
     * 导出个人兑换列表
     */
    @RequiresPermissions("yishengxin:vipExchange:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipExchange vipExchange) {
        List<VipExchange> list = vipExchangeService.selectVipExchangeList(vipExchange);
        ExcelUtil<VipExchange> util = new ExcelUtil<VipExchange>(VipExchange.class);
        return util.exportExcel(list, "vipExchange");
    }

    /**
     * 新增个人兑换
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存个人兑换
     */
    @RequiresPermissions("yishengxin:vipExchange:add")
    @Log(title = "个人兑换", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VipExchange vipExchange) {
        vipExchange.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(vipExchangeService.insertVipExchange(vipExchange));
    }

    /**
     * 修改个人兑换
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        VipExchange vipExchange = vipExchangeService.selectVipExchangeById(id);
        mmap.put("vipExchange", vipExchange);
        return prefix + "/edit";
    }

    /**
     * 修改保存个人兑换
     */
    @RequiresPermissions("yishengxin:vipExchange:edit")
    @Log(title = "个人兑换", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(VipExchange vipExchange) {
        vipExchange.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(vipExchangeService.updateVipExchange(vipExchange));
    }

    /**
     * 删除个人兑换
     */
    @RequiresPermissions("yishengxin:vipExchange:remove")
    @Log(title = "个人兑换", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(vipExchangeService.deleteVipExchangeByIds(ids));
    }

    /**
     * 修改个人兑换
     */
    @GetMapping("/exchange/{id}")
    public String exchange(@PathVariable("id") Integer id, ModelMap mmap) {
        VipExchange vipExchange = vipExchangeService.selectVipExchangeById(id);
        mmap.put("vipExchange", vipExchange);
        return prefix + "/exchange";
    }


    //TODO 上传路径修改

    /**
     * 上传兑换凭证
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult updateAvatar(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String avatar = FileUploadUtils.upload(Global.getAvatarPath(), file);
                return AjaxResult.success(Global.getAvatarPath() + avatar);
            }
            return error();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改保存个人兑换
     */
    @RequiresPermissions("yishengxin:vipExchange:edit")
    @Log(title = "个人兑换", businessType = BusinessType.UPDATE)
    @PostMapping("/exchange")
    @ResponseBody
    public AjaxResult exchangeSave(VipExchange vipExchange) {
        vipExchange.setUpdateBy(ShiroUtils.getLoginName());
        vipExchange.setExchangeStatus(CustomerConstants.EXCHANGE_BUY_STATUS_DEAL);

        try {
            return toAjax(vipExchangeService.exchange(vipExchange));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("兑换错误");
            return error();
        }
    }

}
