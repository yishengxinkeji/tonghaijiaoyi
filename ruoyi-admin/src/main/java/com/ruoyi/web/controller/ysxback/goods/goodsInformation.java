package com.ruoyi.web.controller.ysxback.goods;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.Uuid;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.goods.Goods;
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
import com.ruoyi.yishengxin.service.IGoodsService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import org.springframework.web.multipart.MultipartFile;


/**
 * 商品 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-11
 */
@Controller
@RequestMapping("/yishengxin/goods")
public class goodsInformation extends BaseController {
    private String prefix = "yishengxin/goods";

    @Autowired
    private IGoodsService goodsService;

    @RequiresPermissions("yishengxin:goods:view")
    @GetMapping()
    public String goods() {
        return prefix + "/goods";
    }

    /**
     * 查询商品列表
     */
    @RequiresPermissions("yishengxin:goods:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Goods goods) {
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
    public AjaxResult export(Goods goods) {
        List<Goods> list = goodsService.selectGoodsList(goods);
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        return util.exportExcel(list, "goods");
    }

    /**
     * 新增商品
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存商品
     */

    /*
    @PostMapping("/add")
    @ResponseBody
    public ResponseResult addSave(Goods goods, MultipartFile goodsMainFigure1,MultipartFile[] goodsSlideShow1, MultipartFile[] goodsOfDetailsPicture)  {

        String id1 = Uuid.getId();
        //主图片存放路径，
        String images1 = "e:/" + id1 ;

        try {
            goodsMainFigure1.transferTo(new File(images1));
        } catch (IOException e) {
            return ResponseResult.responseResult(ResponseEnum.GOODS_PICTURE_ADDERROR);
        }
            goods.setGoodsMainFigure(images1);
       //轮播图路径
        String goodsSlideShowPath = "";

        if (goodsSlideShow1.length == 0) {
            goods.setGoodsSlideShow("");
        } else {
            for (int i = 0; i < goodsSlideShow1.length; i++) {
                //生成唯一标识
                String id = Uuid.getId();
                //图片存放路径，
                String images = "e:/" + id + ",";

                try {
                    goodsSlideShow1[i].transferTo(new File(images));
                } catch (IOException e) {
                   return ResponseResult.responseResult(ResponseEnum.GOODS_PICTURE_ADDERROR);
                }
                goodsSlideShowPath = goodsSlideShowPath + images;
            }
        }

        goods.setGoodsSlideShow(goodsSlideShowPath);

        //详情图路径
        String goodsOfDetailsPicturePath = "";
        if (goodsOfDetailsPicture.length == 0) {
            goods.setGoodsDetailsPicture("");
        } else {
            for (int i = 0; i < goodsOfDetailsPicture.length; i++) {
                //生成唯一标识
                String id = Uuid.getId();
                //图片存放路径，第一个是主图，其余的是轮播图
                String images = "e:/" + id + ",";

                try {
                    goodsOfDetailsPicture[i].transferTo(new File(images));
                } catch (IOException e) {
                    return ResponseResult.responseResult(ResponseEnum.GOODS_PICTURE_ADDERROR);
                }
                goodsOfDetailsPicturePath = goodsOfDetailsPicturePath + images;
            }
        }
        goods.setGoodsDetailsPicture(goodsOfDetailsPicturePath);
        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());
        int i = goodsService.insertGoods(goods);

        if (i > 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }

        return ResponseResult.responseResult(ResponseEnum.GOODS_ADDERROR);
    }
     */

    @RequiresPermissions("yishengxin:goods:add")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Goods goods, MultipartFile goodsMainFigure1, MultipartFile[] goodsSlideShow1) {

        String id1 = Uuid.getId();
        //主图片存放路径，
        String images1 = "e:/" + id1 ;

        try {
            goodsMainFigure1.transferTo(new File(images1));
        } catch (IOException e) {
           // return ResponseResult.responseResult(ResponseEnum.GOODS_PICTURE_ADDERROR);
        }
        goods.setGoodsMainFigure(images1);
        //轮播图路径
        String goodsSlideShowPath = "";

        if (goodsSlideShow1.length == 0) {
            goods.setGoodsSlideShow("");
        } else {
            for (int i = 0; i < goodsSlideShow1.length; i++) {
                //生成唯一标识
                String id = Uuid.getId();
                //图片存放路径，
                String images = "e:/" + id + ",";

                try {
                    goodsSlideShow1[i].transferTo(new File(images));
                } catch (IOException e) {
                 //   return ResponseResult.responseResult(ResponseEnum.GOODS_PICTURE_ADDERROR);
                }
                goodsSlideShowPath = goodsSlideShowPath + images;
            }
        }

        goods.setGoodsSlideShow(goodsSlideShowPath);


        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());



        return toAjax(goodsService.insertGoods(goods));
    }

    /**
     * 修改商品
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
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
    public AjaxResult editSave(Goods goods) {
        goods.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(goodsService.updateGoods(goods));
    }

    /**
     * 删除商品
     */
    @RequiresPermissions("yishengxin:goods:remove")
    @Log(title = "商品", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(goodsService.deleteGoodsByIds(ids));
    }

}




