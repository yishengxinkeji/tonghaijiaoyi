package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.framework.util.RedisConfig;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslBuy;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslSale;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeSslSaleMapper;
import com.ruoyi.yishengxin.service.IVipTradeSslBuyService;
import com.ruoyi.yishengxin.service.IVipTradeSslSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExecuteTimer {

    @Autowired
    private IVipTradeSslSaleService vipTradeSslSaleService;

    @Autowired
    private IVipTradeSslBuyService vipTradeSslBuyService;


    /**
     * 定时任务
     */
    //应该每小时执行一次
    //查询所有的
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void timerCron() {
        //每次任务启动后,需要有个标志,用户暂时不能操作 取消按钮,Y代表任务正在执行中
        RedisUtils.set(CustomerConstants.TASK_STATUS_KEY,"Y");

        VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();
        vipTradeSslBuy.setBuyStatus(TradeStatus.TRADING.getCode());
        vipTradeSslBuy.getParams().put("VipTradeSslBuy"," order by buy_time desc");

        VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
        vipTradeSslSale.setSaleStatus(TradeStatus.TRADING.getCode());
        vipTradeSslSale.getParams().put("VipTradeSslSale"," order by sale_time desc");

        List<VipTradeSslBuy> vipTradeSslBuys = vipTradeSslBuyService.selectVipTradeBuyList(vipTradeSslBuy);

        List<VipTradeSslSale> vipTradeSslSales = vipTradeSslSaleService.selectVipTradeSaleList(vipTradeSslSale);

        if(vipTradeSslBuys.size() > 0){
            for(VipTradeSslBuy vipTradeSslBuy1 : vipTradeSslBuys){
                //得到订单的数量和单价
                String buyNumber = vipTradeSslBuy1.getBuyNumber();
                String unitPrice = vipTradeSslBuy1.getUnitPrice();

                vipTradeSslSales.stream().filter(vipTradeSslSale1 -> {
                    //卖价需要低于买价
                    return Double.parseDouble(vipTradeSslSale1.getUnitPrice()) <= Double.parseDouble(unitPrice);
                }).forEach(vipTradeSslSale1 -> {
                    String saleNumber = vipTradeSslSale1.getSaleNumber();
                    String salePrice = vipTradeSslSale1.getUnitPrice();
                    if(Double.parseDouble(saleNumber) > Double.parseDouble(buyNumber)){
                        //卖的数量大于买的数量
                        vipTradeSslBuyService.dealLgOrder(vipTradeSslBuy1,vipTradeSslSale1);

                    }else if(Double.parseDouble(saleNumber) == Double.parseDouble(buyNumber)){
                        //两个数量相同
                        vipTradeSslBuyService.dealEqOrder(vipTradeSslBuy1,vipTradeSslSale1);

                    }else if(Double.parseDouble(saleNumber) < Double.parseDouble(buyNumber)){
                        //卖的数量小于买的数量
                        vipTradeSslBuyService.dealltOrder(vipTradeSslBuy1,vipTradeSslSale1);

                    }
                    vipTradeSslSales.remove(vipTradeSslSale1);
                    return;
                });
            }
        }
        //更改Redis的标志
        RedisUtils.set(CustomerConstants.TASK_STATUS_KEY,"N");
    }



}
