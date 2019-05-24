package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.RegexUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.PlatData;
import com.ruoyi.yishengxin.domain.vipUser.*;
import com.ruoyi.yishengxin.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * 交易挂买中心
 */
@RestController
@RequestMapping("/front/tradeBuy")
public class TradeBuyController extends BaseFrontController {

    private static final Logger log = LoggerFactory.getLogger(TradeBuyController.class);

    @Autowired
    private IVipTradeSslBuyService vipTradeBuyService;

    @Autowired
    private IVipTradeHkdBuyService vipTradeHkdBuyService;

    @Autowired
    private IVipTradeHkdSaleService vipTradeHkdSaleService;

    @Autowired
    private IVipUserService vipUserService;
    @Autowired
    private IVipAccountService accountService;
    @Autowired
    private IPlatDataService platDataService;


    /**
     * 挂买SSL
     * @param token
     * @param number   数量
     * @param price 单价
     * @return
     */
    //保存挂买订单
    //更新用户的hdk
    @PostMapping("/buySsl")
    public ResponseResult buySsl(@RequestHeader("token") String token,
                                 @RequestParam("number") String number,
                                 @RequestParam("price") String price
                                 ){
        List<PlatData> platData = platDataService.selectPlatDataList(new PlatData());
        PlatData platData1 = null;
        if(platData.size() >0 ){
            platData1 = platData.get(0);
        }

        String tradeBegin = DateUtil.format(new Date(),DateUtils.YYYY_MM_DD) + " 00:00:00";
        String tradeEnd = DateUtil.format(new Date(),DateUtils.YYYY_MM_DD) + " 23:59:59";
        if(platData1 != null){
            if(platData1.getTradeBegin() != null) {
                tradeBegin = DateUtil.format(new Date(),DateUtils.YYYY_MM_DD) + " " + platData1.getTradeBegin();
            }
            if(platData1.getTradeEnd() != null) {
                tradeEnd = DateUtil.format(new Date(),DateUtils.YYYY_MM_DD) + " " + platData1.getTradeEnd();
            }
        }

        if( DateUtil.between(DateUtil.parseDateTime(tradeBegin),new Date(), DateUnit.SECOND,false) < 0 ||
                DateUtil.between(new Date(),DateUtil.parseDateTime(tradeEnd), DateUnit.SECOND,false) < 0
        ) {
            //今日交易时间已过
            return ResponseResult.responseResult(ResponseEnum.NOT_IN_TRADE_TIME);
        }

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }
        if((!ReUtil.isMatch(RegexUtils.DECIMAL_REGEX,number) && !ReUtil.isMatch(RegexUtils.INTEGER_REGEX,number))
                || (!ReUtil.isMatch(RegexUtils.DECIMAL_REGEX,price) && !ReUtil.isMatch(RegexUtils.INTEGER_REGEX,price))){

            //数字格式不正确
            return ResponseResult.responseResult(ResponseEnum.NUMBER_TRANCT_ERROR);
        }
        if(vipUser.getIsFrozen().equalsIgnoreCase(CustomerConstants.YES)){
            //用户已被冻结
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_FROZEN);
        }

        try{
            int i = vipTradeBuyService.buySsl(vipUser, number, price);
            if(i == 0){
                //HDK资产不足
                return ResponseResult.responseResult(ResponseEnum.VIP_USER_HKDINSUFFICIENT);
            }

            Map map = new HashMap();
            map.put("hdk",NumberUtil.roundStr(NumberUtil.mul(Double.parseDouble(number),Double.parseDouble(price)),2)); //保留两位小数
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseResult.error();
        }
    }

    /**
     * ssl的挂买列表
     * @param token
     * @param type 传查的是列表,不传查的是个人
     * @return
     */
    @PostMapping("/buySslList")
    public ResponseResult buySslList(@RequestHeader(value = "token") String token,
                                     @RequestParam(value = "type",defaultValue = "") String type){
        try{
            VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();

            vipTradeSslBuy.getParams().put("VipTradeSslBuy"," order by unit_price desc limit 0,10");
            vipTradeSslBuy.setBuyStatus(TradeStatus.TRADING.getCode());
            List list = new ArrayList();
            List<VipTradeSslBuy> vipTradeSslBuys = new ArrayList<>();
            if("".equals(type)){
                //查的是个人
                VipUser vipUser = userExist(token);
                if(vipUser == null){
                    return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
                }
                vipTradeSslBuy.setVipId(vipUser.getId());
                vipTradeSslBuys = vipTradeBuyService.selectVipTradeBuyList(vipTradeSslBuy);
                if(vipTradeSslBuys.size() > 0){
                    vipTradeSslBuys.stream().forEach(vipTradeBuy1 -> {
                        Map map = new HashMap();
                        map.put("type",TradeType.BUY_SSL.getCode());
                        map.put("number",vipTradeBuy1.getBuyNumber());  //数量
                        map.put("price",vipTradeBuy1.getUnitPrice());   //单价
                        map.put("time",vipTradeBuy1.getBuyTime());  //时间
                        map.put("id", vipTradeBuy1.getId());
                        list.add(map);
                    });
                }
            }else if(!type.equals("")){
                //查的是列表
                //交易中的数据按照单价分组统计数量
                List<Map<String,String>> list1 = vipTradeBuyService.selectSumNumberByUnitPrice();
                if(list1.size() > 0){
                    list1.stream().forEach(map1 -> {
                        Map map = new HashMap();
                        map.put("type",TradeType.BUY_SSL.getCode());
                        map.put("number",map1.get("buy_number"));  //数量
                        map.put("price",map1.get("unit_price"));   //单价
                        list.add(map);
                    });
                }
            }

            List list1 = new ArrayList();
            if(list.size() > 10){
                list1 = list.subList(0, 10);
            }else {
                list1 = list;
            }
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,list1);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseResult.error();
        }
    }


    /**
     * 取消挂买
     * @param token
     * @Param id  订单id
     * @return
     */
    @PostMapping("/cancelBuy")
    public ResponseResult cancelBuy(@RequestHeader("token") String token,@RequestParam("id") String id){

        try{
            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }

            if(vipUser.getIsFrozen().equalsIgnoreCase(CustomerConstants.YES)){
                //用户已被冻结
                return ResponseResult.responseResult(ResponseEnum.VIP_USER_FROZEN);
            }

            if(RedisUtils.get(CustomerConstants.TASK_STATUS_KEY) == null || RedisUtils.get(CustomerConstants.TASK_STATUS_KEY).equals("N")){
                if(vipTradeBuyService.cancelBuy(vipUser,id) > 0){
                    return ResponseResult.success();
                }
            }else if(RedisUtils.get(CustomerConstants.TASK_STATUS_KEY).equals("Y")){
                //表示任务正在进行中,所以暂时不能操作
                return ResponseResult.responseResult(ResponseEnum.SYS_DEAL_TRADE);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseResult.error();
        }
        return ResponseResult.error();
    }


    /**
     * 购买 hkd
     * @param token
     * @Param id  记录id
     * @param number    购买数量
     * @param tradeWord 交易密码
     * @return
     */
    @PostMapping("/buyHkd")
    public ResponseResult buyHkd(@RequestHeader("token") String token,
                                 @RequestParam("id") String id,
                                 @RequestParam("number") String number,
                                 @RequestParam("tradeWord") String tradeWord){

        List<PlatData> platData = platDataService.selectPlatDataList(new PlatData());
        PlatData platData1 = null;
        if(platData.size() >0 ){
            platData1 = platData.get(0);
        }

        String tradeBegin = DateUtil.format(new Date(),DateUtils.YYYY_MM_DD) + " 00:00:00";
        String tradeEnd = DateUtil.format(new Date(),DateUtils.YYYY_MM_DD) + " 23:59:59";
        if(platData1 != null){
            if(platData1.getTradeBegin() != null) {
                tradeBegin = DateUtil.format(new Date(),DateUtils.YYYY_MM_DD) + " " + platData1.getTradeBegin();
            }
            if(platData1.getTradeEnd() != null) {
                tradeEnd = DateUtil.format(new Date(),DateUtils.YYYY_MM_DD) + " " + platData1.getTradeEnd();
            }
        }

        if( DateUtil.between(DateUtil.parseDateTime(tradeBegin),new Date(), DateUnit.SECOND,false) < 0 ||
                DateUtil.between(new Date(),DateUtil.parseDateTime(tradeEnd), DateUnit.SECOND,false) < 0
        ) {
            //今日交易时间已过
            return ResponseResult.responseResult(ResponseEnum.NOT_IN_TRADE_TIME);
        }

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }

        VipUser vipUser1 = vipUserService.selectVipUserById(vipUser.getId());
        if(!DigestUtils.md5Hex(tradeWord + vipUser1.getSalt()).equals(vipUser1.getTradePassword())){
            //交易密码错误
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_TRADPASSWORDERROR);
        }

        if(vipUser.getIsFrozen().equalsIgnoreCase(CustomerConstants.YES)){
            //用户已被冻结
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_FROZEN);
        }
        //用户冻结
        VipTradeHkdSale vipTradeHkdSale = vipTradeHkdSaleService.selectVipTradeHkdSaleById(Integer.parseInt(id));
        if(vipTradeHkdSale.getVipId().equals(vipUser.getId())){
            //不能与自己订单交易
            return ResponseResult.responseResult(ResponseEnum.CAN_NOT_BUY_YOURSELF);
        }
        try {
            String order = vipTradeHkdBuyService.buyHkd(vipUser,id,number);
            if("100".equals(order)){
                //购买数量超出订单数量
                return ResponseResult.responseResult(ResponseEnum.OVER_ORDER_TOP);
            }
            if("9999".equals(order)){
                //不能购买自己的订单
                return ResponseResult.responseResult(ResponseEnum.CAN_NOT_BUY_YOURSELF);
            }
            RedisUtils.set(CustomerConstants.LISTEN_TRADE_BUY_PREFIX_KEY+ order,order,43200);
            return ResponseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
    }


    /**
     * 跳转打款凭证
     * @param token
     * @param id    订单id
     * @return
     */
    @PostMapping("/toPayment")
    public ResponseResult toPayment(@RequestHeader("token") String token,@RequestParam("id") String id){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        try{
            //订单
            VipTradeHkdBuy vipTradeHkdBuy = vipTradeHkdBuyService.selectVipTradeHkdBuyById(Integer.parseInt(id));

            Map map = new HashMap();
            map.put("orderNo",vipTradeHkdBuy.getBuyNo());
            map.put("time",vipTradeHkdBuy.getBuyTime());
            map.put("number",vipTradeHkdBuy.getBuyNumber());
            map.put("total",vipTradeHkdBuy.getBuyTotal());
            map.put("saleId",vipTradeHkdBuy.getSaleId());
            map.put("salePhone",vipTradeHkdBuy.getSalePhone());
            map.put("saleAccount",vipTradeHkdBuy.getSaleAccount());
            map.put("accountImg",vipTradeHkdBuy.getSaleAccountProof());
            map.put("id",id);

            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);

        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseResult.error();
        }
    }


    /**
     * 上传打款凭证
     * @param token
     * @param file
     * @return
     */
    @PostMapping("/tradeImg")
    public ResponseResult tradeImg(@RequestHeader("token") String token,@RequestParam("file") MultipartFile file){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        try {
            if (!file.isEmpty()) {
                //图片地址
                String path = uploadFile(file);
                if(path != null){
                    Map map = new HashMap();
                    map.put("picName",Global.getFrontPath()+path);
                    map.put("serverPath",Global.getFrontPath()+path);
                    return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
                }
            }
            return ResponseResult.error();
        } catch(FileSizeLimitExceededException e){
            log.error("文件过大");
            return ResponseResult.responseResult(ResponseEnum.FILE_TOO_MAX);
        }catch (FileNameLengthLimitExceededException e2){
            log.error("文件名过长");
            return ResponseResult.responseResult(ResponseEnum.FILE_NAME_LENGTH);
        }catch (IOException e3){
            return ResponseResult.error();
        }
    }


    /**
     * 提交打款订单
     * @param token
     * @param img   图片地址
     * @param id
     * @return
     */
    @PostMapping("/confirmImg")
    public ResponseResult confirmImg(@RequestHeader("token") String token,
                                     @RequestParam("img") String img,
                                     @RequestParam("id") String id){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        try{
            vipTradeHkdBuyService.updateBuyNo(id,img);

            VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
            vipTradeHkdBuy.setId(Integer.parseInt(id));
            VipTradeHkdBuy vipTradeHkdBuy1 = vipTradeHkdBuyService.selectVipTradeHkdBuyById(Integer.parseInt(id));
            //删掉买订单的计时,更新卖订单计时
            RedisUtils.del(CustomerConstants.LISTEN_TRADE_BUY_PREFIX_KEY+vipTradeHkdBuy1.getBuyNo());
            RedisUtils.set(CustomerConstants.LISTEN_TRADE_SALE_PREFIX_KEY+vipTradeHkdBuy1.getBuyNo(),vipTradeHkdBuy1.getBuyNo(),86400);
            //给卖家发短信,让其及时确认收货
            sendMsg("2",vipTradeHkdBuy1.getSalePhone());
            return ResponseResult.success();
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseResult.error();
        }

    }

}
