package com.ruoyi.web.controller.ysxfront.goods;

import java.util.List;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.domain.goods.GoodsCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 查询商品收藏列表
     */

    @PostMapping("/list")
    @ResponseBody
    public ResponseResult list(String token) {
       // 校验登录状态
        VipUser vipUser = userExist(token);

        if (vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);

        }
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        GoodsCollection goodsCollection = new GoodsCollection();
        goodsCollection.setId(vipUser.getId());
        //查询列表
        List<GoodsCollection> goodsCollections = goodsCollectionService.selectGoodsCollectionList(goodsCollection);
        if (null == goodsCollections) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS, "数据为空");
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS, goodsCollections);
    }

    /**
     * 新增保存商品收藏
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseResult addSave(String token,int gid) {
        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);

        }

        //校验传参
        if (null == token || "".equals(token) || gid < 0 ) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        GoodsCollection goodsCollection = new GoodsCollection();
        goodsCollection.setId(vipUser.getId());
        goodsCollection.setGid(gid);

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
    public ResponseResult remove(String token,String ids) {
        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        //校验传参
        if (null == ids || "".equals(token) || null == token || "".equals(ids)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        int i = goodsCollectionService.deleteGoodsCollectionByIds(ids);
        if (i > 0) {
            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }
        return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_DELECT);
    }

}
