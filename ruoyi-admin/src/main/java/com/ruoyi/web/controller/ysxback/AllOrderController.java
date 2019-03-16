package com.ruoyi.web.controller.ysxback;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 订单管理,所有订单管理
 */
@Controller
@RequestMapping("yishengxin/allOrder")
public class AllOrderController extends BaseController {
    private String prefix = "yishengxin/allOrder";

    @Autowired
    private IVipBuyService vipBuyService;   //个人购买
    @Autowired
    private IVipExchangeService vipExchangeService;     //个人兑换
    @Autowired
    private IVipTradeSslSaleService vipTradeSslSaleService;
    @Autowired
    private IVipTradeHkdSaleService vipTradeHkdSaleService;
    @Autowired
    private ITradeService tradeService;


    @RequiresPermissions("yishengxin:allOrder:view")
    @GetMapping()
    public String allOrder(ModelMap modelMap) {
        return prefix + "/allOrder";
    }


}
