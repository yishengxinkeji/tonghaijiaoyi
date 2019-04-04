package com.ruoyi.web.controller.ysxfront.goods;

import java.util.List;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.goods.GoodsRefundreason;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.yishengxin.service.IGoodsRefundreasonService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 退款原因 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-19
 */
@Controller
@RequestMapping("/front/goodsRefundreason")
public class GoodsRefundreasonController {


    @Autowired
    private IGoodsRefundreasonService goodsRefundreasonService;


    /**
     * 查询退款原因列表
     */

    @PostMapping("/list")
    @ResponseBody
    public ResponseResult list(@RequestHeader("token") String token, GoodsRefundreason goodsRefundreason) {

        List<GoodsRefundreason> list = goodsRefundreasonService.selectGoodsRefundreasonList(goodsRefundreason);

        return ResponseResult.responseResult(ResponseEnum.SUCCESS, list);

    }


}
