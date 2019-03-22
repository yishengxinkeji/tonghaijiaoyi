package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.Md5Utils;
import com.ruoyi.common.utils.RegexUtils;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.vipUser.*;
import com.ruoyi.yishengxin.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 挂卖中心
 */
@RestController
@RequestMapping("/front/tradeSale")
public class TradeSaleController extends BaseFrontController {

    private static final Logger log = LoggerFactory.getLogger(TradeSaleController.class);

    @Autowired
    private IVipTradeSslSaleService vipTradeSaleService;
    @Autowired
    private IVipTradeSslBuyService vipTradeSslBuyService;

    @Autowired
    private IVipTradeHkdSaleService vipTradeHkdSaleService;
    @Autowired
    private IVipTradeHkdBuyService vipTradeHkdBuyService;

    @Autowired
    private IVipUserService vipUserService;
    @Autowired
    private IVipAccountService accountService;



    /**
     * 挂卖
     * @param token
     * @param type  挂卖类型 SSL/HKD
     * @param number    挂卖数量
     * @param price     挂卖单价
     * @return
     */
    @PostMapping("/sale")
    public ResponseResult sale(@RequestHeader("token") String token,
                                 @RequestParam("type") String type,
                                 @RequestParam("number") String number,
                                 @RequestParam(value = "price",required = false) String price
    ){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsFrozen().equalsIgnoreCase(CustomerConstants.YES)){
            //用户已被冻结
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_FROZEN);
        }

        if(!ReUtil.isMatch(RegexUtils.DECIMAL_REGEX,number) && !ReUtil.isMatch(RegexUtils.INTEGER_REGEX,number)) {
            //数字格式不正确
            return ResponseResult.responseResult(ResponseEnum.NUMBER_TRANCT_ERROR);
        }
        if(type.equalsIgnoreCase("SSL")){
            if(!ReUtil.isMatch(RegexUtils.DECIMAL_REGEX,price) && !ReUtil.isMatch(RegexUtils.INTEGER_REGEX,price)) {
                //数字格式不正确
                return ResponseResult.responseResult(ResponseEnum.NUMBER_TRANCT_ERROR);
            }
        }

        try{
            if(type.equalsIgnoreCase(CustomerConstants.SSL)){
                if(Double.parseDouble(number) < 100){
                    //挂售ssl最低100起
                    return ResponseResult.responseResult(ResponseEnum.VIP_USER_SSL_NOT_ENOUGH);
                }

                int i = vipTradeSaleService.saleSsl(vipUser,number,price);
                if(i == 100){
                    //ssl资产不足
                    return ResponseResult.responseResult(ResponseEnum.VIP_USER_SSLINSUFFICIENT);
                }
                if(i == 200){
                    //今日交易量已达上限
                    return ResponseResult.responseResult(ResponseEnum.MAX_TRADE_BY_DAY);
                }
                if(i == 300){
                    //单次交易量已超上限
                    return ResponseResult.responseResult(ResponseEnum.MAX_TRADE_BY_TIME);
                }

                if(i == 400){
                    //单价太低
                    return ResponseResult.responseResult(ResponseEnum.UNITE_PRICE_TOO_LOW);
                }
                if(i == 500){
                    //单价太高
                    return ResponseResult.responseResult(ResponseEnum.UNITE_PRICE_TOO_HIGH);
                }

            }else if(type.equalsIgnoreCase(CustomerConstants.HKD)){

                VipAccount vipAccount = new VipAccount();
                vipAccount.setVipId(vipUser.getId());
                vipAccount.setIsDefault(CustomerConstants.YES);
                List<VipAccount> vipAccounts = accountService.selectVipAccountList(vipAccount);
                if(vipAccounts.size() == 0){
                    //缺少默认账户
                    return ResponseResult.responseResult(ResponseEnum.VIP_ACCOUNT_NO_DEFAULT);
                }

                //交易的hkd需要是100的整数倍
                if(Double.parseDouble(number) < 100 || Double.parseDouble(number) % 100 != 0){
                    return ResponseResult.responseResult(ResponseEnum.HKD_MULTIPLE_100);
                }

               int i = vipTradeHkdSaleService.saleHkd(vipUser,number,vipAccounts.get(0));
                if(i == 100){
                    //HKD资产不足
                    return ResponseResult.responseResult(ResponseEnum.VIP_USER_HKDINSUFFICIENT);
                }
                if(i == 200){
                    //今日交易量已达上限
                    return ResponseResult.responseResult(ResponseEnum.MAX_TRADE_BY_DAY);
                }
                if(i == 300){
                    //单次交易量已超上限
                    return ResponseResult.responseResult(ResponseEnum.MAX_TRADE_BY_TIME);
                }
            }
            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseResult.error();
        }
    }

    /**
     * 挂卖列表
     * @param token 传值查的是个人,不传值查的是系统
     * @param type  类型,如果查的是个人,需要有type(卖ssl,卖hkd)
     * @return
     */
    @PostMapping("/saleList")
    public ResponseResult saleSslList(@RequestHeader(value = "token",required = false) String token,
                                      @RequestParam(value = "type",required = false) String type){

        List list = new ArrayList();

        if(type.equalsIgnoreCase(TradeType.SALE_SSL.getCode())){
            //查的是ssl售卖列表
            VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
            //交易成功
            vipTradeSslSale.setSaleStatus(TradeStatus.SUCCESS.getCode());
            vipTradeSslSale.getParams().put("vipTradeSslSale"," order by sale_time desc");
            List<VipTradeSslSale> vipTradeSslSales = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);

            vipTradeSslSales.stream().forEach(vipTradeSslSale1 -> {
                Map map = new HashMap();
                map.put("number",vipTradeSslSale1.getSaleNumber());  //数量
                map.put("price",vipTradeSslSale1.getUnitPrice());   //单价
                map.put("time",vipTradeSslSale1.getSaleTime());  //时间
                list.add(map);
            });
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    String name1 = String.valueOf(DateUtils.parseDate(o1.get("time")).getTime());
                    String name2 = String.valueOf(DateUtils.parseDate(o2.get("time")).getTime());
                    return name2.compareTo(name1);
                }
            });
        }else if(type.equalsIgnoreCase(TradeType.SALE_HKD.getCode())){
            //查的是hkd列表
            VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
            vipTradeHkdSale.setSaleStatus(TradeStatus.WAITING.getCode());
            vipTradeHkdSale.getParams().put("vipTradeHkdSale"," order by sale_time desc");

            List<VipTradeHkdSale> vipTradeHkdSaleList = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);

            vipTradeHkdSaleList.stream().forEach(vipTradeHkdSale1 -> {
                Map map = new HashMap();
                map.put("type", TradeType.SALE_HKD.getCode());
                map.put("number",vipTradeHkdSale1.getSaleNumber());  //数量
                map.put("time",vipTradeHkdSale1.getSaleTime());  //时间
                map.put("id",vipTradeHkdSale1.getId());
                map.put("vipId",vipTradeHkdSale1.getVipId());
                list.add(map);
            });
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    String name1 = String.valueOf(DateUtils.parseDate(o1.get("time")).getTime());
                    String name2 = String.valueOf(DateUtils.parseDate(o2.get("time")).getTime());
                    return name2.compareTo(name1);
                }
            });
        }else {

            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }

            //查询该用户下的挂卖记录
            VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
            //等待交易中
            vipTradeSslSale.setSaleStatus(TradeStatus.TRADING.getCode());
            vipTradeSslSale.setVipId(vipUser.getId());
            vipTradeSslSale.getParams().put("vipTradeSslSale"," order by sale_time desc");
            List<VipTradeSslSale> vipTradeSslSales = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);

            VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
            vipTradeHkdSale.setVipId(vipUser.getId());
            vipTradeHkdSale.setSaleStatus(TradeStatus.WAITING.getCode());
            vipTradeHkdSale.getParams().put("vipTradeHkdSale"," order by sale_time desc");

            List<VipTradeHkdSale> vipTradeHkdSaleList = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);
            vipTradeSslSales.stream().forEach(vipTradeSslSale1 -> {
                Map map = new HashMap();
                map.put("type", TradeType.SALE_SSL.getCode());
                map.put("number",vipTradeSslSale1.getSaleNumber());  //数量
                map.put("price",vipTradeSslSale1.getUnitPrice());   //单价
                map.put("time",vipTradeSslSale1.getSaleTime());  //时间
                map.put("id",vipTradeSslSale1.getId());
                list.add(map);
            });

            vipTradeHkdSaleList.stream().forEach(vipTradeHkdSale1 -> {
                Map map = new HashMap();
                map.put("type", TradeType.SALE_HKD.getCode());
                map.put("number",vipTradeHkdSale1.getSaleNumber());  //数量
                map.put("time",vipTradeHkdSale1.getSaleTime());  //时间
                map.put("id",vipTradeHkdSale1.getId());
                list.add(map);
            });
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    String name1 = String.valueOf(DateUtils.parseDate(o1.get("time")).getTime());
                    String name2 = String.valueOf(DateUtils.parseDate(o2.get("time")).getTime());
                    return name2.compareTo(name1);
                }
            });
        }

        List list1 = new ArrayList();
        if(list.size() > 10){
            list1 = list.subList(0, 10);
        }else {
            list1 = list;
        }

        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list1);
    }


    /**
     * 取消挂售
     * @param token
     * @Param id  订单id
     * @Param type  类型(6,2),卖ssl,卖hkd
     * @return
     */
    @PostMapping("/cancelSale")
    public ResponseResult cancelSale(@RequestHeader("token") String token,
                                     @RequestParam("id") String id,
                                     @RequestParam("type") String type){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        if(vipUser.getIsFrozen().equalsIgnoreCase(CustomerConstants.YES)){
            //用户已被冻结
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_FROZEN);
        }

        try{
            if(RedisUtils.get(CustomerConstants.TASK_STATUS_KEY) == null || RedisUtils.get(CustomerConstants.TASK_STATUS_KEY).equals("N")){
                if(type.equalsIgnoreCase(TradeType.SALE_SSL.getCode())){
                    if(vipTradeSaleService.cancelSale(vipUser,id) > 0){
                        return ResponseResult.success();
                    }
                }

                if(type.equals(TradeType.SALE_HKD.getCode())){
                    if(vipTradeHkdSaleService.cancelSale(vipUser,id) > 0){
                        return ResponseResult.success();
                    }
                }

                return ResponseResult.error();
            }else if(RedisUtils.get(CustomerConstants.TASK_STATUS_KEY).equals("Y")){
                //表示任务正在进行中,所以暂时不能操作
                return ResponseResult.responseResult(ResponseEnum.SYS_DEAL_TRADE);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }
        return ResponseResult.error();
    }

    /**
     * ssl的挂卖列表
     * @return
     */
    @PostMapping("/saleSslList")
    public ResponseResult saleSslList(){

        VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
        vipTradeSslSale.setSaleStatus(TradeStatus.TRADING.getCode());
        vipTradeSslSale.getParams().put("VipTradeSslSale"," order by sale_time desc limit 0,10");
        List<VipTradeSslSale> vipTradeSslSales = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);
        List list = new ArrayList();
        vipTradeSslSales.stream().forEach(vipTradeSslSale1 -> {
            Map map = new HashMap();
            map.put("number",vipTradeSslSale1.getSaleNumber());  //数量
            map.put("price",vipTradeSslSale1.getUnitPrice());   //单价
            map.put("time",vipTradeSslSale1.getSaleTime());  //时间
            map.put("id",vipTradeSslSale1.getId());
            list.add(map);
        });
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }

    //实时成交
    @PostMapping("/trueOver")
    public ResponseResult trueOver(){

        VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();
        vipTradeSslBuy.setBuyStatus(TradeStatus.SUCCESS.getCode());
        vipTradeSslBuy.getParams().put("VipTradeSslBuy"," order by buy_time desc limit 0,10");
        List<VipTradeSslBuy> vipTradeSslBuys = vipTradeSslBuyService.selectVipTradeBuyList(vipTradeSslBuy);

        VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
        vipTradeSslSale.getParams().put("VipTradeSslSale"," order by sale_time desc limit 0,10");
        List<VipTradeSslSale> vipTradeSslSales = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);

        List list = new ArrayList();
        vipTradeSslBuys.stream().forEach(vipTradeSslBuy1 -> {
            Map map = new HashMap();
            map.put("price",vipTradeSslBuy1.getUnitPrice());
            map.put("number",vipTradeSslBuy1.getBuyNumber());
            list.add(map);
        });
        vipTradeSslSales.stream().forEach(vipTradeSslSale1 -> {
            Map map = new HashMap();
            map.put("price",vipTradeSslSale1.getUnitPrice());
            map.put("number",vipTradeSslSale1.getSaleNumber());
            list.add(map);
        });

        List list1 = new ArrayList();
        if(list.size() > 10){
            list1 = list.subList(0, 10);
        }else {
            list1 = list;
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list1);
    }


    /**
     * HKD交易列表
     * @return
     */
    @PostMapping("/saleHdkList")
    public ResponseResult saleHkdList(){

        VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
        vipTradeHkdSale.setSaleStatus(TradeStatus.TRADING.getCode());
        vipTradeHkdSale.getParams().put("VipTradeHkdSale"," order by sale_time desc limit 0,10");
        List<VipTradeHkdSale> vipTradeHkdSaleList = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);

        List list = new ArrayList();
        vipTradeHkdSaleList.stream().forEach(vipTradeHkdSale1 -> {
            Map map = new HashMap();
            map.put("number",vipTradeHkdSale1.getSaleNumber());  //数量
            map.put("time",vipTradeHkdSale1.getSaleTime());  //时间
            map.put("id",vipTradeHkdSale1.getId());
            map.put("vipId",vipTradeHkdSale1.getVipId());
            list.add(map);
        });
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);

    }

    /**
     * 查询某个人的hkd交易记录
     * @param vipId
     * @return
     */
    @PostMapping("/tradeRecord")
    public ResponseResult tradeRecord(@RequestParam("vipId") String vipId){

        VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();
        vipTradeSslBuy.setVipId(Integer.parseInt(vipId));
        vipTradeSslBuy.setBuyStatus(TradeStatus.SUCCESS.getCode());
        vipTradeSslBuy.getParams().put("VipTradeSslBuy"," order by buy_time desc limit 0,10");

        List<VipTradeSslBuy> vipTradeSslBuys = vipTradeSslBuyService.selectVipTradeBuyList(vipTradeSslBuy);

        VipTradeSslSale vipTradeSslSale = new VipTradeSslSale();
        vipTradeSslSale.setVipId(Integer.parseInt(vipId));
        vipTradeSslSale.setSaleStatus(TradeStatus.SUCCESS.getCode());
        vipTradeSslSale.getParams().put("VipTradeSslSale"," order by sale_time desc limit 0,10");

        List<VipTradeSslSale> vipTradeSslSales = vipTradeSaleService.selectVipTradeSaleList(vipTradeSslSale);

        VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
        vipTradeHkdSale.setVipId(Integer.parseInt(vipId));
        vipTradeHkdSale.setSaleStatus(TradeStatus.SUCCESS.getCode());
        vipTradeHkdSale.getParams().put("VipTradeHkdSale"," order by sale_time desc limit 0,10");

        List<VipTradeHkdSale> vipTradeHkdSales = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale);

        VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
        vipTradeHkdBuy.setVipId(Integer.parseInt(vipId));
        vipTradeHkdBuy.setBuyTime(TradeStatus.SUCCESS.getCode());
        vipTradeHkdBuy.getParams().put("VipTradeHkdBuy"," order by buy_time desc limit 0,10");

        List<VipTradeHkdBuy> vipTradeHkdBuys = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy);

        List list = new ArrayList();
        vipTradeSslBuys.stream().forEach(vipTradeSslBuy1 -> {
            Map map = new HashMap();
            //7挂买SSL
            map.put("type",vipTradeSslBuy1.getBuyType());
            map.put("number",vipTradeSslBuy1.getBuyNumber());
            map.put("time",vipTradeSslBuy1.getBuyTime());
            list.add(map);
        });
        vipTradeSslSales.stream().forEach(vipTradeSslSale1 -> {
            Map map = new HashMap();
            //6挂卖SSL
            map.put("type",vipTradeSslSale1.getSaleType());
            map.put("number",vipTradeSslSale1.getSaleNumber());
            map.put("time",vipTradeSslSale1.getSaleTime());
            list.add(map);
        });
        vipTradeHkdSales.stream().forEach(vipTradeHkdSale1 -> {
            Map map = new HashMap();
            //2挂买HKD
            map.put("type",vipTradeHkdSale1.getSaleType());
            map.put("number",vipTradeHkdSale1.getSaleNumber());
            map.put("time",vipTradeHkdSale1.getSaleTime());
            list.add(map);
        });
        vipTradeHkdBuys.stream().forEach(vipTradeHkdBuy1 -> {
            Map map = new HashMap();
            //3挂卖HKD
            map.put("type",vipTradeHkdBuy1.getBuyType());
            map.put("number",vipTradeHkdBuy1.getBuyNumber());
            map.put("time",vipTradeHkdBuy1.getBuyTime());
            list.add(map);
        });

        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String name1 = String.valueOf(DateUtils.parseDate(o1.get("time")).getTime());
                String name2 = String.valueOf(DateUtils.parseDate(o2.get("time")).getTime());
                return name2.compareTo(name1);
            }
        });

        List list1 = new ArrayList();
        if(list.size() > 10){
            list1 = list.subList(0, 10);
        }else {
            list1 = list;
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list1);
    }

    /**
     * 确认收款
     * @param token
     * @param id    订单id
     * @return
     */
    @PostMapping("/tradeConfirm")
    public ResponseResult tradeRecord(@RequestHeader("token") String token,@RequestParam("id") String id){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        try{
            vipTradeHkdSaleService.confirmOrder(vipUser,id);
            VipTradeHkdSale vipTradeHkdSale = vipTradeHkdSaleService.selectVipTradeHkdSaleById(Integer.parseInt(id));
            RedisUtils.del(CustomerConstants.LISTEN_TRADE_SALE_PREFIX_KEY+vipTradeHkdSale.getSaleNo());
            return ResponseResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }

    }


}
