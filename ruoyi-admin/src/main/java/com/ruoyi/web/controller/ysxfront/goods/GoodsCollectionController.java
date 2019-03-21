package com.ruoyi.web.controller.ysxfront.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.Vo.GoodsCollectionVo;
import com.ruoyi.yishengxin.domain.goods.Goods;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.domain.goods.GoodsCollection;
import com.ruoyi.yishengxin.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.yishengxin.service.IGoodsCollectionService;

/**
 * 商品收藏 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-01
 */
@Controller
@RequestMapping("/front/goodsCollection")
public class GoodsCollectionController extends BaseFrontController {

    @Autowired
    private IGoodsCollectionService goodsCollectionService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询商品收藏列表
     */

    @PostMapping("/list")
    @ResponseBody
    public ResponseResult list(@RequestHeader(value = "token")String token) {
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);

        }
        GoodsCollection goodsCollection = new GoodsCollection();
        goodsCollection.setUid(vipUser.getId());
        //查询列表
        List<GoodsCollection> goodsCollections = goodsCollectionService.selectGoodsCollectionList(goodsCollection);
        if (goodsCollections.size() == 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS, "数据为空");
        }
        List<GoodsCollectionVo> goodsCollectionVos = new ArrayList<>();
        for (int i = 0; i < goodsCollections.size(); i++) {
            GoodsCollectionVo goodsCollectionVo = new GoodsCollectionVo();
            Integer gid = goodsCollections.get(i).getGid();
            Goods goods = goodsService.selectGoodsById(gid);
            Integer id = goodsCollections.get(i).getId();
            goodsCollectionVo.setGoodsCollectionId(id);
            goodsCollectionVo.setGoods(goods);
            goodsCollectionVos.add(goodsCollectionVo);

        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS, goodsCollectionVos);
    }

    /**
     * 新增保存商品收藏
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseResult addSave(@RequestHeader("token")String token, int gid) {
        // 校验登录状态


        //校验传参
        if (null == token || "".equals(token) || gid < 0) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }
        VipUser vipUser = userExist(token);

        if (null == vipUser) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        GoodsCollection goodsCollection = new GoodsCollection();

            GoodsCollection goodsCollection1 = new GoodsCollection();
            goodsCollection1.setUid(vipUser.getId());
            goodsCollection1.setGid(gid);
            List<GoodsCollection> goodsCollections1 = goodsCollectionService.selectGoodsCollectionList(goodsCollection1);
            if (goodsCollections1.size() >0) {
                return ResponseResult.responseResult(ResponseEnum.SUCCESS);
            }
        goodsCollection.setGid(gid);
        goodsCollection.setUid(vipUser.getId());
        List<GoodsCollection> goodsCollections = goodsCollectionService.selectGoodsCollectionList(goodsCollection);
        if(goodsCollections.size() > 0){
            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }

        goodsCollection.setCreateTime(new Date());
        //添加收藏
        int i = goodsCollectionService.insertGoodsCollection(goodsCollection);
        if (i > 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }
        return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_ADD);
    }


    /**
     * 删除商品收藏
     */
    @PostMapping("/remove")
    @ResponseBody
    public ResponseResult remove(@RequestHeader("token")String token, String ids) {

        String[] ids1 =ids.split("\\.");
        //校验传参
        if (null == ids || "".equals(token) || null == token) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        for (int i = 0; i < ids1.length; i++) {
            String id = ids1[i];
            int i1 = goodsCollectionService.deleteGoodsCollectionByIds(id);
            if (i1 == 0) {
                return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_DELECT);
            }
        }

        return ResponseResult.responseResult(ResponseEnum.SUCCESS);

    }

}
