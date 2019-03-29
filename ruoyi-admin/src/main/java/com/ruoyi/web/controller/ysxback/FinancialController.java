package com.ruoyi.web.controller.ysxback;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 财务管理
 */
@Controller
@RequestMapping("yishengxin/financial")
public class FinancialController extends BaseController {
    private String prefix = "yishengxin/financial";

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
    @Autowired
    private IGoodsOrderService goodsOrderService;


//总资金[  现金[即兑换的差价], [ssl: 交易手续费+商品购买]   ] , [hkd:交易手续费 ] , 可用资金 [ 购买-兑换的差价 ],
// 待返资金[当前用户未兑换金额总和], 已提现 [已兑换金额总和]
    @RequiresPermissions("yishengxin:financial:view")
    @GetMapping()
    public String customer(ModelMap modelMap) {

        //客户购买商品花费的ssl
        double goodSum = goodsOrderService.selectSum();

        //总资金(兑换-购买的差价)
        double buySum = vipBuyService.selectSum();    //购买总金额
        double exchangeSum = vipExchangeService.selectSumByIfExchage("2");   //兑换总金额  --已提现
        double divSum = NumberUtil.sub(exchangeSum,buySum);    //兑换的价差 -- 可用资金
        double waitExchange = vipExchangeService.selectSumByIfExchage("1");  //当前未兑换的现金总和 -- 待返资金

        //2019年一直到以后的今天,ssl手续费总和
        double sslSum = vipTradeSslSaleService.selectSum(DateUtil.beginOfYear(DateUtil.parseDate("2019-01-01")),DateUtil.endOfDay(new Date()));
        //2019年一直到以后的今天,hkd交易总和
        double hkdSum = vipTradeHkdSaleService.selectSum(DateUtil.beginOfYear(DateUtil.parseDate("2019-01-01")),DateUtil.endOfDay(new Date())); //

        Trade trade = tradeService.selectTradeList(new Trade()).get(0);
        //ssl交易手续费
        String sslCharge = trade.getSslCharge();
        //hkd交易手续费
        String hkdCharge = trade.getHkdCharge();

        //ssl总资金 = ssl手续费 + 商品购买花费
        double sslChargeSum = NumberUtil.add(sslSum,goodSum);
        double hkdChargeSum = NumberUtil.add(NumberUtil.mul(hkdSum, Double.parseDouble(hkdCharge)),exchangeSum);   //hkd交易手续费+兑换的hkd

        modelMap.put("exchangeSum",exchangeSum);  //已提现
        modelMap.put("divSum",divSum);  //可用资金
        modelMap.put("waitExchange",waitExchange);  //待返资金
        modelMap.put("sslChargeSum",sslChargeSum);  //ssl总资金
        modelMap.put("hkdChargeSum",hkdChargeSum);  //hkd总资金
        modelMap.put("buySum",buySum);      //现金入账总资金

        return prefix + "/financial";
    }


    /**
     * 用户根据时间查询会员注册情况
     * @param day	权重最高
     * @param month	次之
     * @return
     */
    @RequestMapping("/timeSearch")
    @ResponseBody
    public AjaxResult timeSearch(String day,String month){

        double buySum = 0.00;    //购买总金额
        double exchangeSum = 0.00;   //兑换总金额  --已提现

        DateTime begin = DateUtil.beginOfDay(new Date());
        DateTime end = DateUtil.endOfDay(new Date());
        if(!"".equals(day)){
            begin = DateUtil.beginOfDay(DateUtils.parseDate(day));
            end = DateUtil.endOfDay(DateUtils.parseDate(day));
        }else if("".equals(day) && !"".equals(month)){
            begin = DateUtil.beginOfMonth(DateUtils.parseDate(month));
            end = DateUtil.endOfMonth(DateUtils.parseDate(month));
        }
        buySum = vipBuyService.selectSumByTime("2",begin,end);
        exchangeSum = vipExchangeService.selectSumByIfExchageAndTime("2",begin,end);
        double divSum = NumberUtil.sub(exchangeSum,buySum);    //兑换的价差 -- 可用资金
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("buySum",buySum);
        ajaxResult.put("exchangeSum",exchangeSum);
        ajaxResult.put("divSum",divSum);
        return ajaxResult;
    }



}
