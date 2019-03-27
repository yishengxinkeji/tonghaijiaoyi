package com.ruoyi.web.controller.ysxback.goods;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.Vo.GoodsBackVo;
import com.ruoyi.yishengxin.domain.goods.Goods;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
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
    public AjaxResult addSave(Goods goods,String figure1,String figure2,String figure3,String figure4,String figure6,String figure7,String figure8,String figure9,String figure10,String figure11,String figure12){
            goods.setGoodsSoldNumber(0);
            goods.setCreateTime(new Date());
      goods.setSmallPicture(figure1+" ,"+figure2+" ,"+figure3+" ,"+figure4+" ");
      goods.setCenterPicture(goods.getGoodsMainFigure()+" ,"+figure6+" ,"+figure7+" ,"+figure8+" ");
      goods.setBigPicture(figure9+" ,"+figure10+" ,"+figure11+" ,"+figure12+" ");
                goods.setStandUpAndDown("下架");
        String goodsPrice = goods.getGoodsPrice();
        if (null != goodsPrice) {
            int length = goodsPrice.length();
            String s = goodsPrice.charAt(length - 1) + "";
            if (s.equals(".")){
                goodsPrice= goodsPrice.substring(0, length - 1);
              goods.setGoodsPrice(goodsPrice);
            }
        }
        goods.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(goodsService.insertGoods(goods));
    }

    /**
     * 修改商品
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap){
        Goods goods1 = goodsService.selectGoodsById(id);
        GoodsBackVo goods = new GoodsBackVo();
        String[] smallPictures = goods1.getSmallPicture().split(",");
        String figure1 = smallPictures[0];
        goods.setFigure1(figure1);
        String figure2 = smallPictures[1];
        goods.setFigure2(figure2);
        String figure3 = smallPictures[2];
        goods.setFigure3(figure3);
        String figure4 = smallPictures[3];
        goods.setFigure4(figure4);
        String[] centerPicture = goods1.getCenterPicture().split(",");

        String figure6 = centerPicture[1];
        goods.setFigure6(figure6);
        String figure7 = centerPicture[2];
        goods.setFigure7(figure7);
        String figure8 = centerPicture[3];
        goods.setFigure8(figure8);
        String[] bigPicture = goods1.getBigPicture().split(",");
        String figure9 = bigPicture[0];
        goods.setFigure9(figure9);
        String figure10 = bigPicture[1];
        goods.setFigure10(figure10);
        String figure11 = bigPicture[2];
        goods.setFigure11(figure11);
        String figure12 = bigPicture[3];
        goods.setFigure12(figure12);

        String goodsName = goods1.getGoodsName();
        goods.setGoodsName(goodsName);
        /**  详情富文本 */
        String goodsDetailsPicture = goods1.getGoodsDetailsPicture();
        goods.setGoodsDetailsPicture(goodsDetailsPicture);
        /**  商品介绍 */
        String goodsIntroduce = goods1.getGoodsIntroduce();
        goods.setGoodsIntroduce(goodsIntroduce);
        String standUpAndDown = goods1.getStandUpAndDown();
        goods.setStandUpAndDown(standUpAndDown);
        /**  客服电话 */
        String serviceTel = goods1.getServiceTel();
        goods.setServiceTel(serviceTel);
        Integer id1 = goods1.getId();
        goods.setId(id1);
        /**  价格 */
        String goodsPrice = goods1.getGoodsPrice();
        goods.setGoodsPrice(goodsPrice);
        /**库存 */
        Integer goodsInventory = goods1.getGoodsInventory();
        goods.setGoodsInventory(goodsInventory);
        /**主图 */
        String goodsMainFigure = goods1.getGoodsMainFigure();
        goods.setGoodsMainFigure(goodsMainFigure);
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
    public AjaxResult editSave(Goods goods,String figure1,String figure2,String figure3,String figure4,String figure6,String figure7,String figure8,String figure9,String figure10,String figure11,String figure12){
        goods.setGoodsSoldNumber(0);
        goods.setCreateTime(new Date());
        goods.setSmallPicture(figure1+" ,"+figure2+" ,"+figure3+" ,"+figure4+" ");
        goods.setCenterPicture(goods.getGoodsMainFigure()+" ,"+figure6+" ,"+figure7+" ,"+figure8+" ");
        goods.setBigPicture(figure9+" ,"+figure10+" ,"+figure11+" ,"+figure12+" ");

        String goodsPrice = goods.getGoodsPrice();
        if (null != goodsPrice) {
            int length = goodsPrice.length();
            String s = goodsPrice.charAt(length - 1) + "";
            if (s.equals(".")){
                goodsPrice= goodsPrice.substring(0, length - 1);
                goods.setGoodsPrice(goodsPrice);
            }
        }
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
