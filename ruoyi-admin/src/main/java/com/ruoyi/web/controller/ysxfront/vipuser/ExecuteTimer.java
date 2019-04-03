package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.util.RedisConfig;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.yishengxin.domain.vipUser.VipLock;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslBuy;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeSslSale;
import com.ruoyi.yishengxin.mapper.vipUser.VipTradeSslSaleMapper;
import com.ruoyi.yishengxin.service.IVipLockService;
import com.ruoyi.yishengxin.service.IVipTradeSslBuyService;
import com.ruoyi.yishengxin.service.IVipTradeSslSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.Collections;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ExecuteTimer {

    @Autowired
    private IVipTradeSslSaleService vipTradeSslSaleService;

    @Autowired
    private IVipTradeSslBuyService vipTradeSslBuyService;
    @Autowired
    private IVipLockService vipLockService;


    /**
     * 定时任务
     */
    //应该每小时执行一次
    //查询所有的
    @Scheduled(cron = "0 0/1 * * * ?  ")
    public void timerCron() {
        //每次任务启动后,需要有个标志,用户暂时不能操作 取消按钮,Y代表任务正在执行中
        RedisUtils.set(CustomerConstants.TASK_STATUS_KEY,"Y");

        VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();
        vipTradeSslBuy.setBuyStatus(TradeStatus.TRADING.getCode());
        vipTradeSslBuy.getParams().put("VipTradeSslBuy"," order by buy_time asc");

        VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
        vipTradeSslSale.setSaleStatus(TradeStatus.TRADING.getCode());
        vipTradeSslSale.getParams().put("VipTradeSslSale"," order by unit_price asc");

        List<VipTradeSslBuy> vipTradeSslBuys = vipTradeSslBuyService.selectVipTradeBuyList(vipTradeSslBuy);

        List<VipTradeSslSale> vipTradeSslSales = new ArrayList<>();
        if(vipTradeSslBuys.size() > 0){
            for (int i = 0; i < 10 ; i++) {
                vipTradeSslBuys = vipTradeSslBuyService.selectVipTradeBuyList(vipTradeSslBuy);
                for(VipTradeSslBuy vipTradeSslBuy1 : vipTradeSslBuys){
                    //得到订单的单价
                    String unitPrice = vipTradeSslBuy1.getUnitPrice();
                    double buyNumber = Double.parseDouble(vipTradeSslBuy1.getBuyNumber());
                    double number = 0;
                    vipTradeSslSales = vipTradeSslSaleService.selectVipTradeSaleList(vipTradeSslSale);
                    if(vipTradeSslSales.size() > 0){
                        List<VipTradeSslSale> list = new ArrayList();
                        for (int j = 0; j < vipTradeSslSales.size(); j++) {
                            if(number > buyNumber){
                                list.add(vipTradeSslSales.get(j));
                                break;
                            }
                            if(Double.parseDouble(vipTradeSslSales.get(j).getUnitPrice())<= Double.parseDouble(unitPrice)){
                                number += Double.parseDouble(vipTradeSslSales.get(j).getSaleNumber());
                                list.add(vipTradeSslSales.get(j));
                            }
                        }
                        vipTradeSslBuyService.dealTimer(vipTradeSslBuy1,list);
                        /*list.stream().forEach(vipTradeSslSale1->{
                            vipTradeSslBuyService.dealTimer(vipTradeSslBuy1,list);
                        });*/
                    }
                }
            }
        }
        //更改Redis的标志
        RedisUtils.set(CustomerConstants.TASK_STATUS_KEY,"N");
    }


    /**
     * 锁仓定时, 每天早上00点查询到期的锁仓数据
     */
   @Scheduled(cron = "0 0 0 * * ? ")
    public void timerLock() {
        vipLockService.updateTimerLock();
    }
}
