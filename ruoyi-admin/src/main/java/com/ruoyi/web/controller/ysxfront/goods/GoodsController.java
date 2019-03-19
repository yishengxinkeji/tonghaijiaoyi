package com.ruoyi.web.controller.ysxfront.goods;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.utils.Uuid;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.goods.Goods;
import com.ruoyi.yishengxin.domain.goods.GoodsCollection;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IGoodsCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.yishengxin.service.IGoodsService;
import org.springframework.web.multipart.MultipartFile;


/**
 * 商品 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-05
 */
@Controller
@RequestMapping("/front/goods")
public class GoodsController extends BaseFrontController {


    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IGoodsCollectionService goodsCollectionService;


    @PostMapping("/upload")
    @ResponseBody
    public ResponseResult avaterUpload(@RequestHeader("token") String token,@RequestParam("file") MultipartFile file){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        try {
            if (!file.isEmpty()) {
                //图片地址
                String path = uploadFile(file);
                vipUser.setAvater(Global.getFrontPath()+path);
                if(true){
                    return ResponseResult.responseResult(ResponseEnum.SUCCESS,Global.getFrontPath()+path);
                }
            }
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_AVATER);
        } catch(FileSizeLimitExceededException e){

            return ResponseResult.responseResult(ResponseEnum.FILE_TOO_MAX);
        }catch (FileNameLengthLimitExceededException e2){

            return ResponseResult.responseResult(ResponseEnum.FILE_NAME_LENGTH);
        }catch (IOException e3){
            return ResponseResult.error();
        }
    }



    /**
     * 查询商品列表
     */

    @PostMapping("/list")
    @ResponseBody
    public ResponseResult list(@RequestHeader(value = "token",required = false)String token) {


        Goods goods = new Goods();
        goods.setStandUpAndDown("上架");

        List<Goods> list = goodsService.selectGoodsList(goods);
        for (int i = 0; i < list.size(); i++) {
            Goods goods1 = list.get(i);

            //校验传参
            if (null == token || "".equals(token)) {
                goods1.setCollectionStatus(0);
                return ResponseResult.responseResult(ResponseEnum.SUCCESS,goods1);
            }
            // 校验登录状态
            VipUser vipUser = userExist(token);

            if (null == vipUser ) {
                goods1.setCollectionStatus(0);
                return ResponseResult.responseResult(ResponseEnum.SUCCESS,goods1);

            }else {

                Integer id = goods1.getId();
                Integer id1 = vipUser.getId();
                GoodsCollection goodsCollection = new GoodsCollection();
                goodsCollection.setUid(id);
                goodsCollection.setUid(id1);
                List<GoodsCollection> goodsCollections = goodsCollectionService.selectGoodsCollectionList(goodsCollection);
                goods1.setCollectionStatus(0);
                if (goodsCollections.size()>0){
                    goods1.setCollectionStatus(1);
                }
                return ResponseResult.responseResult(ResponseEnum.SUCCESS,goods1);
            }
        }
      
       return ResponseResult.responseResult(ResponseEnum.GOODS_SELECTERROR);
    }
    @PostMapping("/ceshi")
    @ResponseBody
    public ResponseResult addSave1(Goods goods){
        int i = goodsService.insertGoods(goods);
        return null;
    }

    /**
     * 新增保存商品
     * @param goods  商品信息
     * @param goodsMainFigure1 主图
     * @param
     * @param goodsOfDetailsPicture 详情图
     * @return
     * @throws IOException
     */



    @PostMapping("/add")
    @ResponseBody
    public ResponseResult addSave(Goods goods, MultipartFile goodsMainFigure1, MultipartFile[] goodsSlideShow1, MultipartFile[] goodsOfDetailsPicture)  {
            goods.setGoodsSoldNumber(0);
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


    /**
     * 修改保存商品
     *
     * @param goods  商品信息
     * @param
     * @param goodsOfDetailsPicture 商品详情图
     * @return
     */


    @PostMapping("/edit")
    @ResponseBody
    public ResponseResult editSave(Goods goods, MultipartFile goodsMainFigure1,MultipartFile[] goodsSlideShow1, MultipartFile[] goodsOfDetailsPicture) {

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
    public ResponseResult remove(String ids) {

        int i = goodsService.deleteGoodsByIds(ids);

        if( i > 0){
            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }
        return ResponseResult.responseResult(ResponseEnum.GOODS_DELECTERROR);
    }

}
