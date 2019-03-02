package com.ruoyi.web.controller.ysxfront.goods;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.VipUser;
import com.ruoyi.yishengxin.domain.goods.Goods;
import com.ruoyi.yishengxin.service.IGoodsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/front/goods")
public class GoodsController extends BaseFrontController {

    @Resource
    private IGoodsService  iGoodsService;

    /**
     * 商品展示借口
     *
     * @return
     */

    @GetMapping("/shows")
    public ResponseResult showsGoods(){

        List<Goods> goods = iGoodsService.selectGoods();
       if ( null == goods) {
           return ResponseResult.responseResult(ResponseEnum.SUCCESS,"数据为空");
       }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS, goods);
    }



}
