package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.wall.Violation;
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
import com.ruoyi.yishengxin.domain.TradeExplain;
import com.ruoyi.yishengxin.domain.Transfer;
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
    @Autowired
    private ITradeExplainService tradeExplainService;

    /**
     * 进入交易中心
     * @param token
     * @return
     */
    @PostMapping("/toTrade")
    public ResponseResult toTrade(@RequestHeader("token") String token){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipUser vipUser1 = vipUserService.selectVipUserById(vipUser.getId());
        Map map = new HashMap();
        map.put("ssl",vipUser1.getSslMoney());
        map.put("hkd",vipUser1.getHkdMoney());

        List list = new ArrayList();
        List<TradeExplain> tradeExplains = tradeExplainService.selectTradeExplainList(new TradeExplain());
        if(tradeExplains.size() > 0){
            tradeExplains.stream().forEach(tradeExplain -> {
                Map map1 = new HashMap();
                map1.put("title",tradeExplain.getTitle());
                map1.put("id",tradeExplain.getId());
                list.add(map1);
            });
        }

        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list,map);
    }



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
            map.put("vipTrade",vipTradeHkdSale1.getSaleType());
            map.put("number",vipTradeHkdSale1.getSaleNumber());  //数量
            map.put("total",vipTradeHkdSale1.getSaleTotal());   //总价
            map.put("status",vipTradeHkdSale1.getSaleStatus()); //交易状态
            map.put("id",vipTradeHkdSale1.getId());
            map.put("time",vipTradeHkdSale1.getSaleTime());
            map.put("isAppeal",vipTradeHkdSale1.getIsAppeal());
            map.put("failStatus",vipTradeHkdSale1.getTradeFailStatus());
            list.add(map);
        });

        vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
            Map map = new HashMap();
            map.put("vipTrade",vipTradeHkdBuy1.getBuyType());
            map.put("number",vipTradeHkdBuy1.getBuyNumber());  //数量
            map.put("total",vipTradeHkdBuy1.getBuyTotal());   //总价
            map.put("status",vipTradeHkdBuy1.getBuyStatus()); //交易状态
            map.put("id",vipTradeHkdBuy1.getId());
            map.put("time",vipTradeHkdBuy1.getBuyTime());
            map.put("isAppeal",vipTradeHkdBuy1.getIsAppeal());
            map.put("failStatus",vipTradeHkdBuy1.getTradeFailStatus());
            list.add(map);
        });

        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String name1 = String.valueOf(DateUtils.parseDate(o1.get("time")).getTime()) ;//name1是从你list里面拿出来的一个
                String name2 = String.valueOf(DateUtils.parseDate(o2.get("time")).getTime()) ; //name1是从你list里面拿出来的第二个name
                return name2.compareTo(name1);
            }
        });
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
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
        vipTradeHkdSale.getParams().put("VipTradeHkdSale"," and (sale_status=5 or sale_status=6) order by sale_time desc");

        List<VipTradeHkdSale> vipTradeHkdSales = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);


        VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
        vipTradeHkdBuy.setVipId(vipUser.getId());
        vipTradeHkdBuy.getParams().put("VipTradeHkdBuy"," and (buy_status=5 or buy_status=6) order by buy_time desc ");

        List<VipTradeHkdBuy> vipTradeHkdBuys = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy);

        List list = new ArrayList();
        vipTradeHkdSales.stream().forEach(vipTradeHkdSale1 -> {
            Map map = new HashMap();
            map.put("vipTrade",vipTradeHkdSale1.getSaleType());
            map.put("number",vipTradeHkdSale1.getSaleNumber());  //数量
            map.put("total",vipTradeHkdSale1.getSaleTotal());   //总价
            map.put("status",vipTradeHkdSale1.getSaleStatus()); //交易状态
            map.put("id",vipTradeHkdSale1.getId());
            map.put("time",vipTradeHkdSale1.getSaleTime());
            map.put("isAppeal",vipTradeHkdSale1.getIsAppeal());
            list.add(map);
        });

        vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
            Map map = new HashMap();
            map.put("vipTrade",vipTradeHkdBuy1.getBuyType());
            map.put("number",vipTradeHkdBuy1.getBuyNumber());  //数量
            map.put("total",vipTradeHkdBuy1.getBuyTotal());   //总价
            map.put("status",vipTradeHkdBuy1.getBuyStatus()); //交易状态
            map.put("id",vipTradeHkdBuy1.getId());
            map.put("time",vipTradeHkdBuy1.getBuyTime());
            map.put("isAppeal",vipTradeHkdBuy1.getIsAppeal());
            list.add(map);
        });

        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String name1 = String.valueOf(DateUtils.parseDate(o1.get("time")).getTime()) ;//name1是从你list里面拿出来的一个
                String name2 = String.valueOf(DateUtils.parseDate(o2.get("time")).getTime()) ; //name1是从你list里面拿出来的第二个name
                return name2.compareTo(name1);
            }
        });
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
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
            map.put("vipTrade",vipTradeHkdSale1.getSaleType());
            map.put("number",vipTradeHkdSale1.getSaleNumber());  //数量
            map.put("total",vipTradeHkdSale1.getSaleTotal());   //总价
            map.put("status",vipTradeHkdSale1.getSaleStatus()); //交易状态
            map.put("id",vipTradeHkdSale1.getId());
            map.put("time",vipTradeHkdSale1.getSaleTime());
            map.put("isAppeal",vipTradeHkdSale1.getIsAppeal());
            list.add(map);
        });

        vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
            Map map = new HashMap();
            map.put("vipTrade",vipTradeHkdBuy1.getBuyType());
            map.put("number",vipTradeHkdBuy1.getBuyNumber());  //数量
            map.put("total",vipTradeHkdBuy1.getBuyTotal());   //总价
            map.put("status",vipTradeHkdBuy1.getBuyStatus()); //交易状态
            map.put("id",vipTradeHkdBuy1.getId());
            map.put("time",vipTradeHkdBuy1.getBuyTime());
            map.put("isAppeal",vipTradeHkdBuy1.getIsAppeal());
            list.add(map);
        });

        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String name1 = String.valueOf(DateUtils.parseDate(o1.get("time")).getTime()) ;//name1是从你list里面拿出来的一个
                String name2 = String.valueOf(DateUtils.parseDate(o2.get("time")).getTime()) ; //name1是从你list里面拿出来的第二个name
                return name2.compareTo(name1);
            }
        });
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
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
            map.put("vipTrade",vipTradeHkdSale1.getSaleType());
            map.put("number",vipTradeHkdSale1.getSaleNumber());  //数量
            map.put("total",vipTradeHkdSale1.getSaleTotal());   //总价
            map.put("status",vipTradeHkdSale1.getSaleStatus()); //交易状态
            map.put("id",vipTradeHkdSale1.getId());
            map.put("time",vipTradeHkdSale1.getSaleTime());
            map.put("isAppeal",vipTradeHkdSale1.getIsAppeal());
            map.put("failStatus",vipTradeHkdSale1.getTradeFailStatus());
            list.add(map);
        });

        vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
            Map map = new HashMap();
            map.put("vipTrade",vipTradeHkdBuy1.getBuyType());
            map.put("number",vipTradeHkdBuy1.getBuyNumber());  //数量
            map.put("total",vipTradeHkdBuy1.getBuyTotal());   //总价
            map.put("status",vipTradeHkdBuy1.getBuyStatus()); //交易状态
            map.put("id",vipTradeHkdBuy1.getId());
            map.put("time",vipTradeHkdBuy1.getBuyTime());
            map.put("isAppeal",vipTradeHkdBuy1.getIsAppeal());
            map.put("failStatus",vipTradeHkdBuy1.getTradeFailStatus());
            list.add(map);
        });

        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String name1 = String.valueOf(DateUtils.parseDate(o1.get("time")).getTime()) ;//name1是从你list里面拿出来的一个
                String name2 = String.valueOf(DateUtils.parseDate(o2.get("time")).getTime()) ; //name1是从你list里面拿出来的第二个name
                return name2.compareTo(name1);
            }
        });
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
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
            vipTradeSslBuy.setBuyStatus(TradeStatus.SUCCESS.getCode());
            vipTradeSslBuy.getParams().put("VipTradeSslBuy"," order by buy_time desc");

            List<VipTradeSslBuy> vipTradeSslBuys = vipTradeSslBuyService.selectVipTradeBuyList(vipTradeSslBuy);

            VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
            vipTradeSslSale.setVipId(vipUser.getId());
            vipTradeSslSale.setSaleStatus(TradeStatus.SUCCESS.getCode());
            vipTradeSslSale.getParams().put("VipTradeSslSale"," order by sale_time desc");

            List<VipTradeSslSale> vipTradeSslSales = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);

            VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
            vipTradeHkdSale.setVipId(vipUser.getId());
            vipTradeHkdSale.setSaleStatus(TradeStatus.SUCCESS.getCode());
            vipTradeHkdSale.getParams().put("VipTradeHkdSale"," order by sale_time desc");

            List<VipTradeHkdSale> vipTradeHkdSales = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);

            VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
            vipTradeHkdBuy.setVipId(vipUser.getId());
            vipTradeHkdBuy.getParams().put("VipTradeHkdBuy"," and buy_status=2 order by buy_time desc ");

            List<VipTradeHkdBuy> vipTradeHkdBuys = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy);

            vipTradeSslSales.stream().forEach(vipTradeSslSale1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.SALE_SSL.getCode());    //挂卖ssl
                map2.put("tradeTime",vipTradeSslSale1.getSaleTime());
                map2.put("tradeNumber",vipTradeSslSale1.getSaleNumber());
                map2.put("toVipId",vipTradeSslSale1.getBuyId());
                map2.put("toVipNickname",vipTradeSslSale1.getBuyNickname());
                map2.put("toVipAvater",vipTradeSslSale1.getBuyAvater());
                list.add(map2);
            });
            vipTradeSslBuys.stream().forEach(vipTradeSslBuy1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.BUY_SSL.getCode());    //挂买SSL
                map2.put("tradeTime",vipTradeSslBuy1.getBuyTime());
                map2.put("tradeNumber",vipTradeSslBuy1.getBuyNumber());
                map2.put("toVipId",vipTradeSslBuy1.getSaleId());
                map2.put("toVipNickname",vipTradeSslBuy1.getSaleNickname());
                map2.put("toVipAvater",vipTradeSslBuy1.getSaleAvater());
                list.add(map2);
            });

            vipTradeHkdSales.stream().forEach(vipTradeHkdSale1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.SALE_HKD.getCode());    //挂卖hkd
                map2.put("tradeTime",vipTradeHkdSale1.getSaleTime());
                map2.put("tradeNumber",vipTradeHkdSale1.getSaleNumber());
                map2.put("toVipId",vipTradeHkdSale1.getBuyId());
                map2.put("toVipNickname",vipTradeHkdSale1.getBuyNickname());
                map2.put("toVipAvater",vipTradeHkdSale1.getBuyAvater());
                list.add(map2);
            });

            vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.BUY_HKD.getCode());    //挂买ssl
                map2.put("tradeTime",vipTradeHkdBuy1.getBuyTime());
                map2.put("tradeNumber",vipTradeHkdBuy1.getBuyNumber());
                map2.put("toVipId",vipTradeHkdBuy1.getSaleId());
                map2.put("toVipNickname",vipTradeHkdBuy1.getSaleNickname());
                map2.put("toVipAvater",vipTradeHkdBuy1.getSaleAvater());
                list.add(map2);
            });

            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    String name1 = String.valueOf(DateUtils.parseDate(o1.get("tradeTime")).getTime()) ;//name1是从你list里面拿出来的一个
                    String name2 = String.valueOf(DateUtils.parseDate(o2.get("tradeTime")).getTime()) ; //name1是从你list里面拿出来的第二个name
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
            vipTradeSslBuy.setBuyStatus(TradeStatus.SUCCESS.getCode());
            vipTradeSslBuy.getParams().put("VipTradeSslBuy"," order by buy_time desc");

            List<VipTradeSslBuy> vipTradeSslBuys = vipTradeSslBuyService.selectVipTradeBuyList(vipTradeSslBuy);


            VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
            vipTradeHkdBuy.setVipId(vipUser.getId());
            vipTradeHkdBuy.setBuyStatus(TradeStatus.SUCCESS.getCode());
            vipTradeHkdBuy.getParams().put("VipTradeHkdBuy"," order by buy_time desc ");

            List<VipTradeHkdBuy> vipTradeHkdBuys = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy);

            List list = new ArrayList();
            vipTradeSslBuys.stream().forEach(vipTradeSslBuy1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.BUY_SSL.getCode());    //挂买SSL
                map2.put("tradeTime",vipTradeSslBuy1.getBuyTime());
                map2.put("tradeNumber",vipTradeSslBuy1.getBuyNumber());
                map2.put("toVipId",vipTradeSslBuy1.getSaleId());
                map2.put("toVipNickname",vipTradeSslBuy1.getSaleNickname());
                map2.put("toVipAvater",vipTradeSslBuy1.getSaleAvater());
                list.add(map2);
            });

            vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.BUY_HKD.getCode());    //挂买hkd
                map2.put("tradeTime",vipTradeHkdBuy1.getBuyTime());
                map2.put("tradeNumber",vipTradeHkdBuy1.getBuyNumber());
                map2.put("toVipId",vipTradeHkdBuy1.getSaleId());
                map2.put("toVipNickname",vipTradeHkdBuy1.getSaleNickname());
                map2.put("toVipAvater",vipTradeHkdBuy1.getSaleAvater());
                list.add(map2);
            });

            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    String name1 = String.valueOf(DateUtils.parseDate(o1.get("tradeTime")).getTime()) ;//name1是从你list里面拿出来的一个
                    String name2 = String.valueOf(DateUtils.parseDate(o2.get("tradeTime")).getTime()) ; //name1是从你list里面拿出来的第二个name
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
            vipTradeSslSale.setSaleStatus(TradeStatus.SUCCESS.getCode());
            vipTradeSslSale.getParams().put("VipTradeSslSale"," order by sale_time desc");

            List<VipTradeSslSale> vipTradeSslSales = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);

            VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
            vipTradeHkdSale.setVipId(vipUser.getId());
            vipTradeHkdSale.setSaleStatus(TradeStatus.SUCCESS.getCode());
            vipTradeHkdSale.getParams().put("VipTradeHkdSale"," order by sale_time desc");

            List<VipTradeHkdSale> vipTradeHkdSales = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);

            List list = new ArrayList();
            vipTradeSslSales.stream().forEach(vipTradeSslSale1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.SALE_SSL.getCode());    //挂卖ssl
                map2.put("tradeTime",vipTradeSslSale1.getSaleTime());
                map2.put("tradeNumber",vipTradeSslSale1.getSaleNumber());
                map2.put("toVipId",vipTradeSslSale1.getBuyId());
                map2.put("toVipNickname",vipTradeSslSale1.getBuyNickname());
                map2.put("toVipAvater",vipTradeSslSale1.getBuyAvater());
                list.add(map2);
            });

            vipTradeHkdSales.stream().forEach(vipTradeHkdSale1 -> {
                Map map2 = new HashMap();
                map2.put("vipTrade",TradeType.SALE_HKD.getCode());    //挂卖hkd
                map2.put("tradeTime",vipTradeHkdSale1.getSaleTime());
                map2.put("tradeNumber",vipTradeHkdSale1.getSaleNumber());
                map2.put("toVipId",vipTradeHkdSale1.getBuyId());
                map2.put("toVipNickname",vipTradeHkdSale1.getBuyNickname());
                map2.put("toVipAvater",vipTradeHkdSale1.getBuyAvater());
                list.add(map2);
            });

            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    String name1 = String.valueOf(DateUtils.parseDate(o1.get("tradeTime")).getTime()) ;//name1是从你list里面拿出来的一个
                    String name2 = String.valueOf(DateUtils.parseDate(o2.get("tradeTime")).getTime()) ; //name1是从你list里面拿出来的第二个name
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
            vipTrade.getParams().put("tradeTime"," order by trade_time desc");
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
                    String name1 = String.valueOf(DateUtils.parseDate(o1.get("tradeTime")).getTime()) ;//name1是从你list里面拿出来的一个
                    String name2 = String.valueOf(DateUtils.parseDate(o2.get("tradeTime")).getTime()) ; //name1是从你list里面拿出来的第二个name
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
                    String name1 = String.valueOf(DateUtils.parseDate(o1.get("tradeTime")).getTime()) ;//name1是从你list里面拿出来的一个
                    String name2 = String.valueOf(DateUtils.parseDate(o2.get("tradeTime")).getTime()) ; //name1是从你list里面拿出来的第二个name
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

        VipAppeal vipAppeal = new VipAppeal();
        //买HKD
        if(vipTrade.equalsIgnoreCase(TradeType.BUY_HKD.getCode())){
            Map map = new HashMap();
            VipTradeHkdBuy vipTradeHkdBuy = vipTradeHkdBuyService.selectVipTradeHkdBuyById(Integer.parseInt(id));
            
            vipAppeal.setOrderNo(vipTradeHkdBuy.getBuyNo());
            vipAppeal.setBuyId(vipUser.getId());
            List<VipAppeal> vipAppeals = vipAppealService.selectVipAppealList(vipAppeal);
            map.put("id",id);
            map.put("orderNo",vipTradeHkdBuy.getBuyNo());
            map.put("time",vipTradeHkdBuy.getBuyTime());
            map.put("number",vipTradeHkdBuy.getBuyNumber());
            map.put("total",vipTradeHkdBuy.getBuyTotal());
            map.put("saleId",vipTradeHkdBuy.getSaleId());
            map.put("salePhone",vipTradeHkdBuy.getSalePhone());
            map.put("saleAccount",vipTradeHkdBuy.getSaleAccount());
            map.put("accountImg",vipTradeHkdBuy.getSaleAccountProof());
            map.put("proof",vipTradeHkdBuy.getProof());
            if(vipTradeHkdBuy.getBuyStatus().equalsIgnoreCase(TradeStatus.FAIL.getCode())){
                if(vipAppeals.size() > 0){
                    String appealThing = vipAppeals.get(0).getAppealReason();
                    map.put("failReason",vipTradeHkdBuy.getFailReason()+"("+appealThing+")");
                }else {
                    map.put("failReason",vipTradeHkdBuy.getFailReason());
                }
            }else if(vipTradeHkdBuy.getBuyStatus().equalsIgnoreCase(TradeStatus.SUCCESS.getCode())) {
                if(vipAppeals.size() > 0){
                    String appealThing = vipAppeals.get(0).getAppealReason();
                    map.put("failReason",appealThing);
                }else {
                    map.put("failReason","-1");
                }
            }else {
                map.put("failReason","-1");
            }
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        }

        //卖HKD
        if(vipTrade.equalsIgnoreCase(TradeType.SALE_HKD.getCode())){
            VipTradeHkdSale vipTradeHkdSale = vipTradeHkdSaleService.selectVipTradeHkdSaleById(Integer.parseInt(id));

            VipUser saleUser = vipUserService.selectVipUserById(vipTradeHkdSale.getVipId());
            Map map = new HashMap();

            vipAppeal.setOrderNo(vipTradeHkdSale.getSaleNo());
            vipAppeal.setSaleId(vipUser.getId());
            List<VipAppeal> vipAppeals = vipAppealService.selectVipAppealList(vipAppeal);

            map.put("id",id);
            map.put("orderNo",vipTradeHkdSale.getSaleNo());
            map.put("time",vipTradeHkdSale.getSaleTime());
            map.put("number",vipTradeHkdSale.getSaleNumber());
            map.put("total",vipTradeHkdSale.getSaleTotal());
            map.put("saleId",vipTradeHkdSale.getVipId());
            map.put("salePhone",saleUser.getPhone());
            map.put("saleAccount",vipTradeHkdSale.getSaleAccount());
            map.put("accountImg",vipTradeHkdSale.getSaleAccountProof());
            map.put("proof",vipTradeHkdSale.getProof());
            if(vipTradeHkdSale.getSaleStatus().equalsIgnoreCase(TradeStatus.FAIL.getCode())){
                if(vipAppeals.size() > 0){
                    String appealThing = vipAppeals.get(0).getAppealReason();
                    map.put("failReason",vipTradeHkdSale.getFailReason()+"("+appealThing+")");
                }else {
                    map.put("failReason",vipTradeHkdSale.getFailReason());
                }
            }else if(vipTradeHkdSale.getSaleStatus().equalsIgnoreCase(TradeStatus.SUCCESS.getCode())) {
                if(vipAppeals.size() > 0){
                    String appealThing = vipAppeals.get(0).getAppealReason();
                    map.put("failReason",appealThing);
                }else {
                    map.put("failReason","-1");
                }
            }else{
                map.put("failReason","-1");
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
    @PostMapping("/vipAppeal")
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

    /**
     * 实时成交记录
     * @return
     */
    @PostMapping("/transaction")
    public ResponseResult transaction(){
        try {
            List list = new ArrayList();

            VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();
            vipTradeSslBuy.setBuyStatus(TradeStatus.SUCCESS.getCode());
            vipTradeSslBuy.getParams().put("VipTradeSslBuy"," order by buy_time desc limit 0,30");

            List<VipTradeSslBuy> vipTradeSslBuys = vipTradeSslBuyService.selectVipTradeBuyList(vipTradeSslBuy);

            VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
            vipTradeSslSale.setSaleStatus(TradeStatus.SUCCESS.getCode());
            vipTradeSslSale.getParams().put("VipTradeSslSale"," order by sale_time desc limit 0,30");

            List<VipTradeSslSale> vipTradeSslSales = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);

            vipTradeSslSales.stream().forEach(vipTradeSslSale1 -> {
                Map map2 = new HashMap();
                map2.put("number",vipTradeSslSale1.getSaleNumber());
                map2.put("price",vipTradeSslSale1.getUnitPrice());
                map2.put("tradeTime",vipTradeSslSale1.getSaleTime());
                list.add(map2);
            });
            vipTradeSslBuys.stream().forEach(vipTradeSslBuy1 -> {
                Map map2 = new HashMap();
                map2.put("number",vipTradeSslBuy1.getBuyNumber());
                map2.put("price",vipTradeSslBuy1.getUnitPrice());
                map2.put("tradeTime",vipTradeSslBuy1.getBuyTime());
                list.add(map2);
            });

            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    String name1 = String.valueOf(DateUtils.parseDate(o1.get("tradeTime")).getTime()) ;//name1是从你list里面拿出来的一个
                    String name2 = String.valueOf(DateUtils.parseDate(o2.get("tradeTime")).getTime()) ; //name1是从你list里面拿出来的第二个name
                    return name2.compareTo(name1);
                }
            });

            List list1 = new ArrayList();
            if(list.size() > 10){
                list1 = list.subList(0, 10);
            }else {
                list1 = list;
            }
            //将用户最新的信息保存到Redis中
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,list1);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
    }

    /**
     * 寄售列表
     * @return
     */
    @PostMapping("/consignment")
    public ResponseResult consignmentList(){

        List list = new ArrayList();

        VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
        vipTradeSslSale.setSaleStatus(TradeStatus.SUCCESS.getCode());
        vipTradeSslSale.getParams().put("VipTradeSslSale"," order by sale_time desc limit 0,30");

        List<VipTradeSslSale> vipTradeSslSales = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);

        VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
        vipTradeHkdSale.setSaleStatus(TradeStatus.SUCCESS.getCode());
        vipTradeHkdSale.getParams().put("VipTradeHkdSale"," order by sale_time desc");

        List<VipTradeHkdSale> vipTradeHkdSales = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);

        for (VipTradeSslSale vipTradeSslSale1 : vipTradeSslSales){
            Map map2 = new HashMap();
            map2.put("number",vipTradeSslSale1.getSaleNumber());
            map2.put("price",vipTradeSslSale1.getUnitPrice());

            VipUser vipUser = vipUserService.selectVipUserById(vipTradeSslSale1.getVipId());
            map2.put("phone",vipUser.getPhone());
            map2.put("vid",vipUser.getId());
            map2.put("type",CustomerConstants.SSL);
            map2.put("time",vipTradeSslSale1.getSaleTime());
            list.add(map2);
        }

        for (VipTradeHkdSale vipTradeHkdSale1 : vipTradeHkdSales){
            Map map2 = new HashMap();
            map2.put("number",vipTradeHkdSale1.getSaleNumber());
            VipUser vipUser = vipUserService.selectVipUserById(vipTradeHkdSale1.getVipId());
            map2.put("phone",vipUser.getPhone());
            map2.put("vid",vipUser.getId());
            map2.put("type",CustomerConstants.HKD);
            map2.put("time",vipTradeHkdSale1.getSaleTime());
            list.add(map2);
        }

        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String name1 = String.valueOf(DateUtils.parseDate(o1.get("time")).getTime()) ;//name1是从你list里面拿出来的一个
                String name2 = String.valueOf(DateUtils.parseDate(o2.get("time")).getTime()) ; //name1是从你list里面拿出来的第二个name
                return name2.compareTo(name1);
            }
        });

        List list1 = new ArrayList();
        if(list.size() > 10){
            list1 = list.subList(0, 10);
        }else {
            list1 = list;
        }
        //将用户最新的信息保存到Redis中
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list1);
    }

    /**
     * k线 按类型查询所有的交易订单
     * @param type  类型(日 day,周 week,月 month),如果不传,默认按日
     * @return
     */
    @PostMapping("/kline")
    public ResponseResult kline(@RequestParam(value = "type",defaultValue = "day") String type){

        List<Map<String,String>> list = new ArrayList<>();
        List<Map<String,String>> n_list = new LinkedList();
        if(type.equalsIgnoreCase("day")){
            String now = DateUtil.format(DateUtil.offsetDay(new Date(),1), DateUtils.YYYY_MM_DD); //明天
            String begin = DateUtil.format(DateUtil.offsetDay(new Date(), -1),DateUtils.YYYY_MM_DD); //昨天

             list = vipTradeSaleService.selectSale(DateUtil.beginOfDay(DateUtils.parseDate(begin)),DateUtil.endOfDay(DateUtils.parseDate(now)));
        }

        if(type.equalsIgnoreCase("week")){

            String now = DateUtil.format(DateUtil.offsetDay(new Date(),1), DateUtils.YYYY_MM_DD); //1天以后

            String begin = DateUtil.format(DateUtil.offsetDay(new Date(), -6),DateUtils.YYYY_MM_DD); //6天以前

           list = vipTradeSaleService.selectSale(DateUtil.beginOfDay(DateUtils.parseDate(begin)),DateUtil.endOfDay(DateUtils.parseDate(now)));
        }

        if(type.equalsIgnoreCase("month")){

            String now = DateUtil.format(DateUtil.offsetDay(new Date(),1), DateUtils.YYYY_MM_DD); //1天以后

            String begin = DateUtil.format(DateUtil.offsetDay(new Date(), -30),DateUtils.YYYY_MM_DD); //30天以前

           list = vipTradeSaleService.selectSale(DateUtil.beginOfDay(DateUtils.parseDate(begin)),DateUtil.endOfDay(DateUtils.parseDate(now)));
        }

        if(list.size() > 1000){
            list = list.subList(list.size()-1001,list.size()-1);
        }
        list.stream().forEach(map -> {
            List<String> list1 = new ArrayList();
            Map map1 = new HashMap();
            map1.put("name",map.get("time"));
            list1.add(0,map.get("time"));
            list1.add(1,map.get("number"));
            map1.put("value",list1);
            n_list.add(map1);
        });
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,n_list);
    }


    /**
     * k线每次请求
     * @return
     */
    @GetMapping("/ktime")
    public ResponseResult ktime(){
        String now = DateUtil.format(DateUtil.offsetMinute(new Date(),6), DateUtils.YYYY_MM_DD_HH_MM_SS); //1分钟后

        String begin = DateUtil.format(DateUtil.offsetMinute(new Date(), 0),DateUtils.YYYY_MM_DD_HH_MM_SS); //1现在

        List<Map<String,String>> list = vipTradeSaleService.selectSale(DateUtil.parse(begin),DateUtil.parse(now));

        List n_list = new LinkedList();
        list.stream().forEach(map -> {
            List<String> list1 = new ArrayList();
            Map map1 = new HashMap();
            map1.put("name",map.get("time"));
            list1.add(0,map.get("time"));
            list1.add(1,map.get("number"));
            map1.put("value",list1);
            n_list.add(map1);
        });
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,n_list);
    }

    /**
     * 交易说明
     * @param id
     * @return
     */
    @GetMapping("/explain")
    public ResponseResult explain(@RequestParam("id") String id){
        TradeExplain tradeExplain = tradeExplainService.selectTradeExplainById(Integer.parseInt(id));
        Map map = new HashMap();
        map.put("id",tradeExplain.getId());
        map.put("title",tradeExplain.getTitle());
        map.put("content",tradeExplain.getContent());
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
    }

    /**
     * 实时成交单价及百分比
     * @return
     */
    @GetMapping("/actualPrice")
    public ResponseResult actualPrice (){
        Map map = new HashMap();
        //查询最近交易成功的两条记录
        List<Map<String,String>> list = vipTradeSaleService.selectTwoLeast();

        if(list.size()  == 2){
            //说明今日已经有交易了
            String unitPrice = list.get(0).get("unit_price");
            map.put("actualPrice",unitPrice);   //最新成交单价
            String unitPrice1 = list.get(1).get("unit_price");
            double div = NumberUtil.div(Double.parseDouble(unitPrice), Double.parseDouble(unitPrice1));
            //增长百分比
            String upPercent = NumberUtil.roundStr(NumberUtil.mul(div, 100), CustomerConstants.ROUND_NUMBER);
            map.put("percent",upPercent+"%");
        }else if(list.size() == 1){
            //说明已经凌晨,并且只交易了一条,则增长为100
            String unitPrice = list.get(0).get("unit_price");
            map.put("actualPrice",unitPrice);   //最新成交单价
            map.put("percent",1);
        }else if(list.size() == 0){
            //说明今天没有成交的,
            VipTradeSslSale vipTradeSslSale = vipTradeSaleService.selectByMaxId();
            if(vipTradeSslSale != null){
                map.put("actualPrice",vipTradeSslSale.getUnitPrice());   //最新成交单价
                map.put("percent",1);
            }
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
    }

}
