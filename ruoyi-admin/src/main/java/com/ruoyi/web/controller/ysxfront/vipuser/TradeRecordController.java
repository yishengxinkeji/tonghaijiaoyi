package com.ruoyi.web.controller.ysxfront.vipuser;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.AppealType;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.vipUser.*;
import com.ruoyi.yishengxin.service.*;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 交易状态和交易记录查询
 */
@RestController
@RequestMapping("/front/trade")
public class TradeRecordController extends BaseFrontController {

    private static final Logger log = LoggerFactory.getLogger(TradeBuyController.class);

    @Autowired
    private IVipTradeSslSaleService vipTradeSaleService;
    @Autowired
    private IVipTradeSslBuyService vipTradeSslBuyService;

    @Autowired
    private IVipTradeHkdSaleService vipTradeHkdSaleService;
    @Autowired
    private IVipTradeHkdBuyService vipTradeHkdBuyService;
    @Autowired
    private IVipTradeService vipTradeService;
    @Autowired
    private IVipAccountService accountService;
    @Autowired
    private IVipUserService vipUserService;
    @Autowired
    private IVipAppealService vipAppealService;

    /**
     * 全部交易记录查询
     * @param token
     * @return
     */
    @PostMapping("/allTrade")
    public ResponseResult allTrade(@RequestHeader("token") String token){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
        vipTradeHkdSale.setVipId(vipUser.getId());
        vipTradeHkdSale.getParams().put("VipTradeHkdSale"," order by sale_time desc");

        List<VipTradeHkdSale> vipTradeHkdSales = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);

        VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
        vipTradeHkdBuy.setVipId(vipUser.getId());
        vipTradeHkdBuy.getParams().put("VipTradeHkdBuy"," order by buy_time desc ");

        List<VipTradeHkdBuy> vipTradeHkdBuys = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy);

        List list = new ArrayList();
        vipTradeHkdSales.stream().forEach(vipTradeHkdSale1 -> {
            Map map = new HashMap();
            map.put("type", CustomerConstants.HKD);
            map.put("number",vipTradeHkdSale1.getSaleNumber());  //数量
            map.put("total",vipTradeHkdSale1.getSaleTotal());   //总价
            map.put("status",vipTradeHkdSale1.getSaleStatus()); //交易状态
            map.put("id",vipTradeHkdSale1.getId());
            list.add(map);
        });

        vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
            Map map = new HashMap();
            map.put("type", CustomerConstants.HKD);
            map.put("number",vipTradeHkdBuy1.getBuyNumber());  //数量
            map.put("total",vipTradeHkdBuy1.getBuyTotal());   //总价
            map.put("status",vipTradeHkdBuy1.getBuyStatus()); //交易状态
            map.put("id",vipTradeHkdBuy1.getId());
            list.add(map);
        });

        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer name1 = Integer.valueOf(o1.get("time").toString()) ;//name1是从你list里面拿出来的一个
                Integer name2 = Integer.valueOf(o2.get("time").toString()) ; //name1是从你list里面拿出来的第二个name
                return name2.compareTo(name1);
            }
        });
        return ResponseResult.success();
    }

    /**
     * 交易中
     * @param token
     * @return
     */
    @PostMapping("/trading")
    public ResponseResult trading(@RequestHeader("token") String token){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
        vipTradeHkdSale.setVipId(vipUser.getId());
        vipTradeHkdSale.setSaleStatus(TradeStatus.TRADING.getCode());
        vipTradeHkdSale.getParams().put("VipTradeHkdSale"," order by sale_time desc");

        List<VipTradeHkdSale> vipTradeHkdSales = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);

        VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
        vipTradeHkdBuy.setVipId(vipUser.getId());
        vipTradeHkdBuy.setBuyStatus(TradeStatus.TRADING.getCode());
        vipTradeHkdBuy.getParams().put("VipTradeHkdBuy"," order by buy_time desc ");

        List<VipTradeHkdBuy> vipTradeHkdBuys = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy);

        List list = new ArrayList();
        vipTradeHkdSales.stream().forEach(vipTradeHkdSale1 -> {
            Map map = new HashMap();
            map.put("type", CustomerConstants.HKD);
            map.put("number",vipTradeHkdSale1.getSaleNumber());  //数量
            map.put("total",vipTradeHkdSale1.getSaleTotal());   //总价
            map.put("status",vipTradeHkdSale1.getSaleStatus()); //交易状态
            map.put("id",vipTradeHkdSale1.getId());
            list.add(map);
        });

        vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
            Map map = new HashMap();
            map.put("type", CustomerConstants.HKD);
            map.put("number",vipTradeHkdBuy1.getBuyNumber());  //数量
            map.put("total",vipTradeHkdBuy1.getBuyTotal());   //总价
            map.put("status",vipTradeHkdBuy1.getBuyStatus()); //交易状态
            map.put("id",vipTradeHkdBuy1.getId());
            list.add(map);
        });

        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer name1 = Integer.valueOf(o1.get("time").toString()) ;//name1是从你list里面拿出来的一个
                Integer name2 = Integer.valueOf(o2.get("time").toString()) ; //name1是从你list里面拿出来的第二个name
                return name2.compareTo(name1);
            }
        });
        return ResponseResult.success();
    }

    /**
     * 交易成功
     * @param token
     * @return
     */
    @PostMapping("/tradeSuccess")
    public ResponseResult tradeSuccess(@RequestHeader("token") String token){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
        vipTradeHkdSale.setVipId(vipUser.getId());
        vipTradeHkdSale.setSaleStatus(TradeStatus.SUCCESS.getCode());
        vipTradeHkdSale.getParams().put("VipTradeHkdSale"," order by sale_time desc");

        List<VipTradeHkdSale> vipTradeHkdSales = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);

        VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
        vipTradeHkdBuy.setVipId(vipUser.getId());
        vipTradeHkdBuy.setBuyStatus(TradeStatus.SUCCESS.getCode());
        vipTradeHkdBuy.getParams().put("VipTradeHkdBuy"," order by buy_time desc ");

        List<VipTradeHkdBuy> vipTradeHkdBuys = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy);

        List list = new ArrayList();

        vipTradeHkdSales.stream().forEach(vipTradeHkdSale1 -> {
            Map map = new HashMap();
            map.put("type", CustomerConstants.HKD);
            map.put("number",vipTradeHkdSale1.getSaleNumber());  //数量
            map.put("total",vipTradeHkdSale1.getSaleTotal());   //总价
            map.put("status",vipTradeHkdSale1.getSaleStatus()); //交易状态
            map.put("id",vipTradeHkdSale1.getId());
            list.add(map);
        });

        vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
            Map map = new HashMap();
            map.put("type", CustomerConstants.HKD);
            map.put("number",vipTradeHkdBuy1.getBuyNumber());  //数量
            map.put("total",vipTradeHkdBuy1.getBuyTotal());   //总价
            map.put("status",vipTradeHkdBuy1.getBuyStatus()); //交易状态
            map.put("id",vipTradeHkdBuy1.getId());
            list.add(map);
        });

        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer name1 = Integer.valueOf(o1.get("time").toString()) ;//name1是从你list里面拿出来的一个
                Integer name2 = Integer.valueOf(o2.get("time").toString()) ; //name1是从你list里面拿出来的第二个name
                return name2.compareTo(name1);
            }
        });
        return ResponseResult.success();
    }

    /**
     * 交易失败
     * @param token
     * @return
     */
    @PostMapping("/tradeFail")
    public ResponseResult tradeFail(@RequestHeader("token") String token){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
        vipTradeHkdSale.setVipId(vipUser.getId());
        vipTradeHkdSale.setSaleStatus(TradeStatus.FAIL.getCode());
        vipTradeHkdSale.getParams().put("VipTradeHkdSale"," order by sale_time desc");

        List<VipTradeHkdSale> vipTradeHkdSales = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);

        VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
        vipTradeHkdBuy.setVipId(vipUser.getId());
        vipTradeHkdBuy.setBuyStatus(TradeStatus.FAIL.getCode());
        vipTradeHkdBuy.getParams().put("VipTradeHkdBuy"," order by buy_time desc ");

        List<VipTradeHkdBuy> vipTradeHkdBuys = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy);

        List list = new ArrayList();

        vipTradeHkdSales.stream().forEach(vipTradeHkdSale1 -> {
            Map map = new HashMap();
            map.put("type", CustomerConstants.HKD);
            map.put("number",vipTradeHkdSale1.getSaleNumber());  //数量
            map.put("total",vipTradeHkdSale1.getSaleTotal());   //总价
            map.put("status",vipTradeHkdSale1.getSaleStatus()); //交易状态
            map.put("id",vipTradeHkdSale1.getId());
            list.add(map);
        });

        vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
            Map map = new HashMap();
            map.put("type", CustomerConstants.HKD);
            map.put("number",vipTradeHkdBuy1.getBuyNumber());  //数量
            map.put("total",vipTradeHkdBuy1.getBuyTotal());   //总价
            map.put("status",vipTradeHkdBuy1.getBuyStatus()); //交易状态
            map.put("id",vipTradeHkdBuy1.getId());
            list.add(map);
        });

        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer name1 = Integer.valueOf(o1.get("time").toString()) ;//name1是从你list里面拿出来的一个
                Integer name2 = Integer.valueOf(o2.get("time").toString()) ; //name1是从你list里面拿出来的第二个name
                return name2.compareTo(name1);
            }
        });
        return ResponseResult.success();
    }


    /**
     * 全部交易记录
     * @param token
     * @return
     */
    @PostMapping("/tradeRecordList")
    public ResponseResult tradeRecordList(@RequestHeader("token") String token){

        try {
            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }
            List list = new ArrayList();
            //交易明细
            VipTrade vipTrade = new VipTrade();
            vipTrade.setVipId(vipUser.getId());
            vipTrade.getParams().put("tradeTime","order by trade_time desc");
            List<VipTrade> vipTrades = vipTradeService.selectVipTradeList(vipTrade);

            vipTrades.stream().forEach(vipTrade1 -> {
                Map map1 = new HashMap();
                map1.put("vipTrade",vipTrade1.getVipTrade());
                map1.put("tradeTime",vipTrade1.getTradeTime());
                map1.put("tradeNumber",vipTrade1.getTradeNumber());
                map1.put("toVipId",vipTrade1.getToVipId());
                map1.put("toVipNickname",vipTrade1.getToVipNickname());
                map1.put("toVipAvater",vipTrade1.getToVipAvater());
                list.add(map1);
            });

            VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();
            vipTradeSslBuy.setVipId(vipUser.getId());
            vipTradeSslBuy.setBuyStatus(TradeStatus.FAIL.getCode());
            vipTradeSslBuy.getParams().put("VipTradeSslBuy"," order by buy_time desc");

            List<VipTradeSslBuy> vipTradeSslBuys = vipTradeSslBuyService.selectVipTradeBuyList(vipTradeSslBuy);

            VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
            vipTradeSslSale.setVipId(vipUser.getId());
            vipTradeSslSale.setSaleStatus(TradeStatus.FAIL.getCode());
            vipTradeSslSale.getParams().put("VipTradeSslSale"," order by sale_time desc");

            List<VipTradeSslSale> vipTradeSslSales = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);

            VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
            vipTradeHkdSale.setVipId(vipUser.getId());
            vipTradeHkdSale.setSaleStatus(TradeStatus.FAIL.getCode());
            vipTradeHkdSale.getParams().put("VipTradeHkdSale"," order by sale_time desc");

            List<VipTradeHkdSale> vipTradeHkdSales = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);

            VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
            vipTradeHkdBuy.setVipId(vipUser.getId());
            vipTradeHkdBuy.setBuyStatus(TradeStatus.FAIL.getCode());
            vipTradeHkdBuy.getParams().put("VipTradeHkdBuy"," order by buy_time desc ");

            List<VipTradeHkdBuy> vipTradeHkdBuys = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy);

            vipTradeSslSales.stream().forEach(vipTradeSslSale1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.SALE_SSL);    //挂卖ssl
                map2.put("tradeTime",vipTradeSslSale1.getSaleTime());
                map2.put("tradeNumber",vipTradeSslSale1.getSaleNumber());
                map2.put("toVipId",vipTradeSslSale1.getBuyId());
                map2.put("toVipNickname",vipTradeSslSale1.getBuyNickname());
                map2.put("toVipAvater",vipTradeSslSale1.getBuyAvater());
                list.add(map2);
            });
            vipTradeSslBuys.stream().forEach(vipTradeSslBuy1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.BUY_SSL);    //挂买SSL
                map2.put("tradeTime",vipTradeSslBuy1.getBuyTime());
                map2.put("tradeNumber",vipTradeSslBuy1.getBuyNumber());
                map2.put("toVipId",vipTradeSslBuy1.getSaleId());
                map2.put("toVipNickname",vipTradeSslBuy1.getSaleNickname());
                map2.put("toVipAvater",vipTradeSslBuy1.getSaleAvater());
                list.add(map2);
            });

            vipTradeHkdSales.stream().forEach(vipTradeHkdSale1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.SALE_HKD);    //挂卖hkd
                map2.put("tradeTime",vipTradeHkdSale1.getSaleTime());
                map2.put("tradeNumber",vipTradeHkdSale1.getSaleNumber());
                map2.put("toVipId",vipTradeHkdSale1.getBuyId());
                map2.put("toVipNickname",vipTradeHkdSale1.getBuyNickname());
                map2.put("toVipAvater",vipTradeHkdSale1.getBuyAvater());
                list.add(map2);
            });

            vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.BUY_HKD);    //挂买ssl
                map2.put("tradeTime",vipTradeHkdBuy1.getBuyTime());
                map2.put("tradeNumber",vipTradeHkdBuy1.getBuyNumber());
                map2.put("toVipId",vipTradeHkdBuy1.getSaleId());
                map2.put("toVipNickname",vipTradeHkdBuy1.getSaleNickname());
                map2.put("toVipAvater",vipTradeHkdBuy1.getSaleAvater());
                list.add(map2);
            });

            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Integer name1 = Integer.valueOf(o1.get("tradeTime").toString()) ;//name1是从你list里面拿出来的一个
                    Integer name2 = Integer.valueOf(o2.get("tradeTime").toString()) ; //name1是从你list里面拿出来的第二个name
                    return name2.compareTo(name1);
                }
            });

            //将用户最新的信息保存到Redis中
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
    }


    /**
     * 买入记录
     * @param token
     * @return
     */
    @PostMapping("/tradeRecordBuy")
    public ResponseResult tradeRecordBuy(@RequestHeader("token") String token) {

        try{
            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }

            VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();
            vipTradeSslBuy.setVipId(vipUser.getId());
            vipTradeSslBuy.setBuyStatus(TradeStatus.FAIL.getCode());
            vipTradeSslBuy.getParams().put("VipTradeSslBuy"," order by buy_time desc");

            List<VipTradeSslBuy> vipTradeSslBuys = vipTradeSslBuyService.selectVipTradeBuyList(vipTradeSslBuy);


            VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
            vipTradeHkdBuy.setVipId(vipUser.getId());
            vipTradeHkdBuy.setBuyStatus(TradeStatus.FAIL.getCode());
            vipTradeHkdBuy.getParams().put("VipTradeHkdBuy"," order by buy_time desc ");

            List<VipTradeHkdBuy> vipTradeHkdBuys = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy);

            List list = new ArrayList();
            vipTradeSslBuys.stream().forEach(vipTradeSslBuy1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.BUY_SSL);    //挂买SSL
                map2.put("tradeTime",vipTradeSslBuy1.getBuyTime());
                map2.put("tradeNumber",vipTradeSslBuy1.getBuyNumber());
                map2.put("toVipId",vipTradeSslBuy1.getSaleId());
                map2.put("toVipNickname",vipTradeSslBuy1.getSaleNickname());
                map2.put("toVipAvater",vipTradeSslBuy1.getSaleAvater());
                list.add(map2);
            });

            vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.BUY_HKD);    //挂买ssl
                map2.put("tradeTime",vipTradeHkdBuy1.getBuyTime());
                map2.put("tradeNumber",vipTradeHkdBuy1.getBuyNumber());
                map2.put("toVipId",vipTradeHkdBuy1.getSaleId());
                map2.put("toVipNickname",vipTradeHkdBuy1.getSaleNickname());
                map2.put("toVipAvater",vipTradeHkdBuy1.getSaleAvater());
                list.add(map2);
            });

            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Integer name1 = Integer.valueOf(o1.get("tradeTime").toString()) ;//name1是从你list里面拿出来的一个
                    Integer name2 = Integer.valueOf(o2.get("tradeTime").toString()) ; //name1是从你list里面拿出来的第二个name
                    return name2.compareTo(name1);
                }
            });

            return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }

    }


    /**
     * 卖出记录
     * @param token
     * @return
     */
    @PostMapping("/tradeRecordSale")
    public ResponseResult tradeRecordSale(@RequestHeader("token") String token) {

        try{
            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }

            VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
            vipTradeSslSale.setVipId(vipUser.getId());
            vipTradeSslSale.setSaleStatus(TradeStatus.FAIL.getCode());
            vipTradeSslSale.getParams().put("VipTradeSslSale"," order by sale_time desc");

            List<VipTradeSslSale> vipTradeSslSales = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);

            VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
            vipTradeHkdSale.setVipId(vipUser.getId());
            vipTradeHkdSale.setSaleStatus(TradeStatus.FAIL.getCode());
            vipTradeHkdSale.getParams().put("VipTradeHkdSale"," order by sale_time desc");

            List<VipTradeHkdSale> vipTradeHkdSales = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);

            List list = new ArrayList();
            vipTradeSslSales.stream().forEach(vipTradeSslSale1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.SALE_SSL);    //挂卖ssl
                map2.put("tradeTime",vipTradeSslSale1.getSaleTime());
                map2.put("tradeNumber",vipTradeSslSale1.getSaleNumber());
                map2.put("toVipId",vipTradeSslSale1.getBuyId());
                map2.put("toVipNickname",vipTradeSslSale1.getBuyNickname());
                map2.put("toVipAvater",vipTradeSslSale1.getBuyAvater());
                list.add(map2);
            });

            vipTradeHkdSales.stream().forEach(vipTradeHkdSale1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.SALE_HKD);    //挂卖hkd
                map2.put("tradeTime",vipTradeHkdSale1.getSaleTime());
                map2.put("tradeNumber",vipTradeHkdSale1.getSaleNumber());
                map2.put("toVipId",vipTradeHkdSale1.getBuyId());
                map2.put("toVipNickname",vipTradeHkdSale1.getBuyNickname());
                map2.put("toVipAvater",vipTradeHkdSale1.getBuyAvater());
                list.add(map2);
            });

            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Integer name1 = Integer.valueOf(o1.get("tradeTime").toString()) ;//name1是从你list里面拿出来的一个
                    Integer name2 = Integer.valueOf(o2.get("tradeTime").toString()) ; //name1是从你list里面拿出来的第二个name
                    return name2.compareTo(name1);
                }
            });

            return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }

    }



    /**
     *  转出记录
     * @param token
     * @return
     */
    @PostMapping("/tradeRecordOut")
    public ResponseResult tradeRecordOut(@RequestHeader("token") String token){

        try {
            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }
            List list = new ArrayList();
            //交易明细
            VipTrade vipTrade = new VipTrade();
            vipTrade.setVipId(vipUser.getId());
            vipTrade.setVipTrade(TradeType.OUT_HKD.getCode());
            vipTrade.getParams().put("tradeTime","order by trade_time desc");
            List<VipTrade> vipTrades = vipTradeService.selectVipTradeList(vipTrade);

            vipTrades.stream().forEach(vipTrade1 -> {
                Map map1 = new HashMap();
                map1.put("vipTrade",vipTrade1.getVipTrade());
                map1.put("tradeTime",vipTrade1.getTradeTime());
                map1.put("tradeNumber",vipTrade1.getTradeNumber());
                map1.put("toVipId",vipTrade1.getToVipId());
                map1.put("toVipNickname",vipTrade1.getToVipNickname());
                map1.put("toVipAvater",vipTrade1.getToVipAvater());
                list.add(map1);
            });

            vipTrade.setVipTrade(TradeType.OUT_SSL.getCode());
            vipTrade.getParams().put("tradeTime","order by trade_time desc");
            List<VipTrade> vipTrades1 = vipTradeService.selectVipTradeList(vipTrade);
            vipTrades1.stream().forEach(vipTrade1 -> {
                Map map1 = new HashMap();
                map1.put("vipTrade",vipTrade1.getVipTrade());
                map1.put("tradeTime",vipTrade1.getTradeTime());
                map1.put("tradeNumber",vipTrade1.getTradeNumber());
                map1.put("toVipId",vipTrade1.getToVipId());
                map1.put("toVipNickname",vipTrade1.getToVipNickname());
                map1.put("toVipAvater",vipTrade1.getToVipAvater());
                list.add(map1);
            });

            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Integer name1 = Integer.valueOf(o1.get("tradeTime").toString()) ;//name1是从你list里面拿出来的一个
                    Integer name2 = Integer.valueOf(o2.get("tradeTime").toString()) ; //name1是从你list里面拿出来的第二个name
                    return name2.compareTo(name1);
                }
            });

            //将用户最新的信息保存到Redis中
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
    }

    /**
     *  转入记录
     * @param token
     * @return
     */
    @PostMapping("/tradeRecordIn")
    public ResponseResult tradeRecordIn(@RequestHeader("token") String token){

        try {
            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }
            List list = new ArrayList();
            //交易明细
            VipTrade vipTrade = new VipTrade();
            vipTrade.setVipId(vipUser.getId());
            vipTrade.setVipTrade(TradeType.IN_HKD.getCode());
            vipTrade.getParams().put("tradeTime","order by trade_time desc");
            List<VipTrade> vipTrades = vipTradeService.selectVipTradeList(vipTrade);

            vipTrades.stream().forEach(vipTrade1 -> {
                Map map1 = new HashMap();
                map1.put("vipTrade",vipTrade1.getVipTrade());
                map1.put("tradeTime",vipTrade1.getTradeTime());
                map1.put("tradeNumber",vipTrade1.getTradeNumber());
                map1.put("toVipId",vipTrade1.getToVipId());
                map1.put("toVipNickname",vipTrade1.getToVipNickname());
                map1.put("toVipAvater",vipTrade1.getToVipAvater());
                list.add(map1);
            });

            vipTrade.setVipTrade(TradeType.IN_SSL.getCode());
            vipTrade.getParams().put("tradeTime","order by trade_time desc");
            List<VipTrade> vipTrades1 = vipTradeService.selectVipTradeList(vipTrade);
            vipTrades1.stream().forEach(vipTrade1 -> {
                Map map1 = new HashMap();
                map1.put("vipTrade",vipTrade1.getVipTrade());
                map1.put("tradeTime",vipTrade1.getTradeTime());
                map1.put("tradeNumber",vipTrade1.getTradeNumber());
                map1.put("toVipId",vipTrade1.getToVipId());
                map1.put("toVipNickname",vipTrade1.getToVipNickname());
                map1.put("toVipAvater",vipTrade1.getToVipAvater());
                list.add(map1);
            });

            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Integer name1 = Integer.valueOf(o1.get("tradeTime").toString()) ;//name1是从你list里面拿出来的一个
                    Integer name2 = Integer.valueOf(o2.get("tradeTime").toString()) ; //name1是从你list里面拿出来的第二个name
                    return name2.compareTo(name1);
                }
            });

            //将用户最新的信息保存到Redis中
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
    }



    /**
     * 查看详情
     * @param token
     * @param id    订单id
     * @param vipTrade  订单类型(卖出/买入)
     * @return
     */
    @PostMapping("/tradeDetail")
    public ResponseResult tradeDetail(@RequestHeader("token") String token,
                                        @RequestParam("id") String id,
                                        @RequestParam("vipTrade") String vipTrade) {


        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipAccount vipAccount = new VipAccount();
        vipAccount.setIsDefault(CustomerConstants.YES);

        //买HKD
        if(vipTrade.equalsIgnoreCase(TradeType.BUY_HKD.getCode())){
            vipAccount.setVipId(vipUser.getId());
            List<VipAccount> vipAccounts = accountService.selectVipAccountList(vipAccount);

            Map map = new HashMap();
            VipTradeHkdBuy vipTradeHkdBuy = vipTradeHkdBuyService.selectVipTradeHkdBuyById(Integer.parseInt(id));

            map.put("id",id);
            map.put("orderNo",vipTradeHkdBuy.getBuyNo());
            map.put("time",vipTradeHkdBuy.getBuyTime());
            map.put("type", CustomerConstants.HKD);
            map.put("number",vipTradeHkdBuy.getBuyNumber());
            map.put("total",vipTradeHkdBuy.getBuyTotal());
            map.put("saleId",vipTradeHkdBuy.getSaleId());
            map.put("salePhone",vipTradeHkdBuy.getSalePhone());
            if(vipAccounts.size() > 0){
                map.put("saleAccount",vipAccounts.get(0).getAccountNumber());
                map.put("accountImg",vipAccounts.get(0).getAccountImg());
            }else {
                map.put("saleAccount","");
                map.put("accountImg","");
            }

            if(vipTradeHkdBuy.getBuyStatus().equals(TradeStatus.FAIL)){
                map.put("proof",vipTradeHkdBuy.getFailReason());
            }else if(vipTradeHkdBuy.getBuyStatus().equals(TradeStatus.SUCCESS)){
                map.put("proof",vipTradeHkdBuy.getProof());
            }else {
                map.put("proof","");
            }
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        }

        //卖HKD
        if(vipTrade.equalsIgnoreCase(TradeType.SALE_HKD.getCode())){
            VipTradeHkdSale vipTradeHkdSale = vipTradeHkdSaleService.selectVipTradeHkdSaleById(Integer.parseInt(id));

            VipUser saleUser = vipUserService.selectVipUserById(vipTradeHkdSale.getVipId());
            vipAccount.setVipId(saleUser.getId());
            List<VipAccount> vipAccounts = accountService.selectVipAccountList(vipAccount);
            Map map = new HashMap();

            map.put("id",id);
            map.put("orderNo",vipTradeHkdSale.getSaleNo());
            map.put("time",vipTradeHkdSale.getSaleTime());
            map.put("type", CustomerConstants.HKD);
            map.put("number",vipTradeHkdSale.getSaleNumber());
            map.put("total",vipTradeHkdSale.getSaleTotal());
            map.put("saleId",vipTradeHkdSale.getVipId());
            map.put("salePhone",saleUser.getPhone());
            if(vipAccounts.size() > 0){
                map.put("saleAccount",vipAccounts.get(0).getAccountNumber());
                map.put("accountImg",vipAccounts.get(0).getAccountImg());
            }else {
                map.put("saleAccount","");
                map.put("accountImg","");
            }

            if(vipTradeHkdSale.getSaleStatus().equals(TradeStatus.FAIL)){
                map.put("proof",vipTradeHkdSale.getFailReason());
            }else if(vipTradeHkdSale.getSaleType().equals(TradeStatus.SUCCESS)){
                map.put("proof",vipTradeHkdSale.getProof());
            }else {
                map.put("proof","");
            }
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        }

            return ResponseResult.success();

        }


    /**
     * 申诉
     * @param token
     * @param vipAppeal (content/orderType,orderId),
     *     SALE_HKD("2", "挂卖HKD"),
     *     BUY_HKD("3", "挂买HKD"),
     * @return
     */
    //插入申诉记录,修改订单的申诉状态
    public ResponseResult appeal(@RequestHeader("token") String token,
                                 VipAppeal vipAppeal){


        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        try{

            int i = vipAppealService.appeal(vipUser,vipAppeal);
            if(i > 0) {
                return ResponseResult.success();
            }
            return ResponseResult.error();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }
    }


}
