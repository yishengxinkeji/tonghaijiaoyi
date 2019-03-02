package com.ruoyi.web.controller.ysxback.vipUser;

import java.util.List;

import com.ruoyi.common.constant.CustomerConstants;
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
import com.ruoyi.yishengxin.domain.vipUser.VipAddress;
import com.ruoyi.yishengxin.service.IVipAddressService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 会员地址 信息操作处理
 *
 * @author ruoyi
 * @date 2019-02-27
 */
@Controller
@RequestMapping("/yishengxin/vipAddress")
public class VipAddressController extends BaseController {
    private String prefix = "yishengxin/vipAddress";

    @Autowired
    private IVipAddressService vipAddressService;

    @RequiresPermissions("yishengxin:vipAddress:view")
    @GetMapping()
    public String vipAddress() {
        return prefix + "/vipAddress";
    }

    /**
     * 查询会员地址列表
     */
    @RequiresPermissions("yishengxin:vipAddress:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VipAddress vipAddress) {
        startPage();
        List<VipAddress> list = vipAddressService.selectVipAddressList(vipAddress);
        return getDataTable(list);
    }


    /**
     * 导出会员地址列表
     */
    @RequiresPermissions("yishengxin:vipAddress:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VipAddress vipAddress) {
        List<VipAddress> list = vipAddressService.selectVipAddressList(vipAddress);
        ExcelUtil<VipAddress> util = new ExcelUtil<VipAddress>(VipAddress.class);
        return util.exportExcel(list, "vipAddress");
    }

    /**
     * 新增会员地址
     */
    @GetMapping("/add/{vipId}")
    public String add(@PathVariable String vipId,ModelMap modelMap) {
        modelMap.put("vipId",vipId);
        return prefix + "/add";
    }

    /**
     * 新增保存会员地址
     */
    @RequiresPermissions("yishengxin:vipAddress:add")
    @Log(title = "会员地址", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VipAddress vipAddress) {

        if(vipAddress.getIsDefault().equalsIgnoreCase(CustomerConstants.YES)){
            vipAddressService.updateDefaultAddress(vipAddress.getVipId());
        }
        vipAddress.setCreateBy(ShiroUtils.getLoginName());
        vipAddress.setIsDefault(vipAddress.getIsDefault().toUpperCase());
        return toAjax(vipAddressService.insertVipAddress(vipAddress));
    }

    /**
     * 修改会员地址
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        VipAddress vipAddress = vipAddressService.selectVipAddressById(id);
        mmap.put("vipAddress", vipAddress);
        return prefix + "/edit";
    }

    /**
     * 修改保存会员地址
     */
    @RequiresPermissions("yishengxin:vipAddress:edit")
    @Log(title = "会员地址", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(VipAddress vipAddress) {
        if(vipAddress.getIsDefault().equalsIgnoreCase(CustomerConstants.YES)){
            vipAddressService.updateDefaultAddress(vipAddress.getVipId());
        }
        vipAddress.setUpdateBy(ShiroUtils.getLoginName());
        vipAddress.setIsDefault(vipAddress.getIsDefault().toUpperCase());
        return toAjax(vipAddressService.updateVipAddress(vipAddress));
    }

    /**
     * 删除会员地址
     */
    @RequiresPermissions("yishengxin:vipAddress:remove")
    @Log(title = "会员地址", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(vipAddressService.deleteVipAddressByIds(ids));
    }

}
