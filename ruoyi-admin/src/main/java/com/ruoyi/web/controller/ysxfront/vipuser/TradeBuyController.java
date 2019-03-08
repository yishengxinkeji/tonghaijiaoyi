package com.ruoyi.web.controller.ysxfront.vipuser;

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
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if((!ReUtil.isMatch(RegexUtils.DECIMAL_REGEX,number) && !ReUtil.isMatch(RegexUtils.INTEGER_REGEX,number))
                || (!ReUtil.isMatch(RegexUtils.DECIMAL_REGEX,price) && ReUtil.isMatch(RegexUtils.INTEGER_REGEX,price))){

            //数字格式不正确
            return ResponseResult.responseResult(ResponseEnum.NUMBER_TRANCT_ERROR);
        }

        try{
            int i = vipTradeBuyService.buySsl(vipUser, number, price);
            if(i == 0){
                //HDK资产不足
                return ResponseResult.responseResult(ResponseEnum.VIP_USER_HKDINSUFFICIENT);
            }

            Map map = new HashMap();
            map.put("hdk",NumberUtil.mul(Double.parseDouble(number),Double.parseDouble(price)));
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseResult.error();
        }
    }

    /**
     * 挂买列表
     * @param token
     * @return
     */
    @PostMapping("/buyList")
    public ResponseResult busSslList(@RequestHeader("token") String token){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();
        vipTradeSslBuy.setVipId(vipUser.getId());
        //交易中
        vipTradeSslBuy.setBuyStatus(TradeStatus.TRADING.getCode());
        vipTradeSslBuy.getParams().put("vipTradeSslBuy"," order by buy_time desc");
        List<VipTradeSslBuy> vipTradeSslBuys = vipTradeBuyService.selectVipTradeBuyList(vipTradeSslBuy);
        List list = new ArrayList();
        vipTradeSslBuys.stream().forEach(vipTradeBuy1 -> {
            Map map = new HashMap();
            map.put("type","SSL");
            map.put("number",vipTradeBuy1.getBuyNumber());  //数量
            map.put("price",vipTradeBuy1.getUnitPrice());   //单价
            map.put("time",vipTradeBuy1.getBuyTime());  //时间
            map.put("id",vipTradeBuy1.getId());
            list.add(map);
        });
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }


    /**
     * 取消挂买
     * @param token
     * @Param id  订单id
     * @return
     */
    @PostMapping("/cancelBuy")
    public ResponseResult cancelBuy(@RequestHeader("token") String token,@RequestParam("id") String id){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(RedisUtils.get(CustomerConstants.TASK_STATUS_KEY).equals("Y")){
            //表示任务正在进行中,所以暂时不能操作
            return ResponseResult.responseResult(ResponseEnum.SYS_DEAL_TRADE);
        }

        VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();
        vipTradeSslBuy.setId(Integer.parseInt(id));
        vipTradeSslBuy.setBuyStatus(TradeStatus.CANCEL.getCode());
        if(vipTradeBuyService.updateVipTradeBuy(vipTradeSslBuy) > 0){
            return ResponseResult.success();
        }
        return ResponseResult.error();
    }

    /**
     * ssl的挂买列表
     * @return
     */
    @PostMapping("/buySslList")
    public ResponseResult buySslList(){

        VipTradeSslBuy vipTradeSslBuy = new VipTradeSslBuy();
        vipTradeSslBuy.getParams().put("VipTradeSslBuy"," order by buy_time desc limit 0,10");
        List<VipTradeSslBuy> vipTradeSslBuys = vipTradeBuyService.selectVipTradeBuyList(vipTradeSslBuy);
        List list = new ArrayList();
        vipTradeSslBuys.stream().forEach(vipTradeBuy1 -> {
            Map map = new HashMap();
            map.put("type","SSL");
            map.put("number",vipTradeBuy1.getBuyNumber());  //数量
            map.put("price",vipTradeBuy1.getUnitPrice());   //单价
            map.put("time",vipTradeBuy1.getBuyTime());  //时间
            list.add(map);
        });
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
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


        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipUser vipUser1 = vipUserService.selectVipUserById(vipUser.getId());
        if(!DigestUtils.md5Hex(tradeWord + vipUser1.getSalt()).equals(vipUser1.getTradePassword())){
            //交易密码错误
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_TRADPASSWORDERROR);
        }

        try {
            int i = vipTradeHkdBuyService.buyHkd(vipUser,id,number);
            if(i == 100){
                //购买数量超出订单数量
                return ResponseResult.responseResult(ResponseEnum.OVER_ORDER_TOP);
            }

            if(i == 9999){
                //不能购买自己的订单
                return ResponseResult.responseResult(ResponseEnum.CAN_NOT_BUY_YOURSELF);
            }
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

            VipAccount vipAccount = new VipAccount();
            vipAccount.setIsDefault(CustomerConstants.YES);
            vipAccount.setVipId(vipUser.getId());
            List<VipAccount> vipAccounts = accountService.selectVipAccountList(vipAccount);
            if(vipAccounts.size() == 0){
                //缺少默认收款账户
                return ResponseResult.responseResult(ResponseEnum.VIP_ACCOUNT_NO_DEFAULT);
            }

            Map map = new HashMap();
            map.put("orderNo",vipTradeHkdBuy.getBuyNo());
            map.put("time",vipTradeHkdBuy.getBuyTime());
            map.put("type", CustomerConstants.HKD);
            map.put("number",vipTradeHkdBuy.getBuyNumber());
            map.put("total",vipTradeHkdBuy.getBuyTotal());
            map.put("saleId",vipTradeHkdBuy.getSaleId());
            map.put("salePhone",vipTradeHkdBuy.getSalePhone());
            map.put("saleAccount",vipAccounts.get(0).getAccountNumber());
            map.put("accountImg",vipAccounts.get(0).getAccountImg());
            map.put("id",id);

            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);

        }catch (Exception e){
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
                    map.put("picName",path);
                    map.put("serverPath",CustomerConstants.SERVER_LINK+ Global.getFrontPath()+path);
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
            return ResponseResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }

    }

}
