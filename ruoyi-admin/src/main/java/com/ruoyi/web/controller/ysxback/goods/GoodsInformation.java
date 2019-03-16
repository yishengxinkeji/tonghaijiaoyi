package com.ruoyi.web.controller.ysxback.goods;

import java.util.List;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.goods.Goods;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;

import com.ruoyi.yishengxin.service.IGoodsService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商品 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-13
 */
@Controller
@RequestMapping("/yishengxin/goods")
public class GoodsInformation extends BaseController
{
    private String prefix = "yishengxin/goods";

    @Autowired
    private IGoodsService goodsService;

    @RequiresPermissions("yishengxin:goods:view")
    @GetMapping()
    public String goods(){
        return prefix + "/goods";
    }

    /**
     * 查询商品列表
     */
    @RequiresPermissions("yishengxin:goods:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Goods goods){
        startPage();
        List<Goods> list = goodsService.selectGoodsList(goods);
        return getDataTable(list);
    }


    /**
     * 导出商品列表
     */
    @RequiresPermissions("yishengxin:goods:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Goods goods){
        List<Goods> list = goodsService.selectGoodsList(goods);
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        return util.exportExcel(list, "goods");
    }

    /**
     * 新增商品
     */
    @GetMapping("/add")
    public String add(){
        return prefix + "/add";
    }

    /**
     * 新增保存商品
     */
    @RequiresPermissions("yishengxin:goods:add")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Goods goods,String figure2,String figure3,String figure4,String figure5,String figure6,String figure7,String figure8,String figure9,String figure10,String figure11,String figure12){

      goods.setSmallPicture(figure2+","+figure3+","+figure4);
      goods.setCenterPicture(figure5+","+figure6+","+figure7+","+figure8);
      goods.setBigPicture(figure9+","+figure10+","+figure11+","+figure12);


        goods.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(goodsService.insertGoods(goods));
    }

    /**
     * 修改商品
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap){
        Goods goods = goodsService.selectGoodsById(id);
        mmap.put("goods", goods);
        return prefix + "/edit";
    }

    /**
     * 修改保存商品
     */
    @RequiresPermissions("yishengxin:goods:edit")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Goods goods){
        goods.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(goodsService.updateGoods(goods));
    }

    /**
     * 删除商品
     */
    @RequiresPermissions("yishengxin:goods:remove")
    @Log(title = "商品", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids){
        return toAjax(goodsService.deleteGoodsByIds(ids));
    }

    /**
     * 商品图片上传
     */
    @Log(title = "商品图片", businessType = BusinessType.UPDATE)
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
}
