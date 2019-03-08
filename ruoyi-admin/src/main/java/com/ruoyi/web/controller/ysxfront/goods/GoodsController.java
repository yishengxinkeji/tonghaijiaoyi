package com.ruoyi.web.controller.ysxfront.goods;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.utils.Uuid;
import com.ruoyi.yishengxin.domain.goods.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.yishengxin.service.IGoodsService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import org.springframework.web.multipart.MultipartFile;


/**
 * 商品 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-05
 */
@Controller
@RequestMapping("/front/goods")
public class GoodsController extends BaseController {


    @Autowired
    private IGoodsService goodsService;


    /**
     * 查询商品列表
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Goods goods) {
        startPage();
        List<Goods> list = goodsService.selectGoodsList(goods);
        return getDataTable(list);
    }


    /**
     * 新增保存商品
     * @param goods  商品信息
     * @param goodsOfPicture 主图轮播图
     * @param goodsOfDetailsPicture 详情图
     * @return
     * @throws IOException
     */

    @PostMapping("/add")
    @ResponseBody
    public ResponseResult addSave(Goods goods, MultipartFile[] goodsOfPicture, MultipartFile[] goodsOfDetailsPicture)  {
       //主图和轮播图路径
        String goodsOfPicturePath = "";

        if (goodsOfPicture.length == 0) {
            goods.setGoodsPicture("");
        } else {
            for (int i = 0; i < goodsOfPicture.length; i++) {
                //生成唯一标识
                String id = Uuid.getId();
                //图片存放路径，第一个是主图，其余的是轮播图
                String images = "e:/" + id + ",";

                try {
                    goodsOfPicture[i].transferTo(new File(images));
                } catch (IOException e) {
                   return ResponseResult.responseResult(ResponseEnum.GOODS_PICTURE_ADDERROR);
                }
                goodsOfPicturePath = goodsOfPicturePath + images;
            }
        }

        goods.setGoodsPicture(goodsOfPicturePath);

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


    /**
     * 修改保存商品
     *
     * @param goods  商品信息
     * @param goodsOfPicture 商品图片和轮播图
     * @param goodsOfDetailsPicture 商品详情图
     * @return
     */


    @PostMapping("/edit")
    @ResponseBody
    public ResponseResult editSave(Goods goods, MultipartFile[] goodsOfPicture, MultipartFile[] goodsOfDetailsPicture) {
//主图和轮播图路径
        String goodsOfPicturePath = "";

        if (goodsOfPicture.length == 0) {

        } else {
            for (int i = 0; i < goodsOfPicture.length; i++) {
                //生成唯一标识
                String id = Uuid.getId();
                //图片存放路径，第一个是主图，其余的是轮播图
                String images = "e:/" + id + ",";

                try {
                    goodsOfPicture[i].transferTo(new File(images));
                } catch (IOException e) {
                    return ResponseResult.responseResult(ResponseEnum.GOODS_PICTURE_ADDERROR);
                }
                goodsOfPicturePath = goodsOfPicturePath + images;
            }
        }

        goods.setGoodsPicture(goodsOfPicturePath);

        //详情图路径
        String goodsOfDetailsPicturePath = "";
        if (goodsOfDetailsPicture.length == 0) {

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

        goods.setUpdateTime(new Date());
        int i = goodsService.updateGoods(goods);

        if (i > 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }

        return ResponseResult.responseResult(ResponseEnum.GOODS_UPLOADERROR);
    }

    /**
     * 删除商品
     */

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(goodsService.deleteGoodsByIds(ids));
    }

}
