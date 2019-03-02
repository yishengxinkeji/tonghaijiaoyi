package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.web.controller.system.SysProfileController;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.domain.vipUser.*;
import com.ruoyi.yishengxin.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 个人中心接口
 */
@RestController
@RequestMapping("/front/vipUserCenter")
public class VipUserCenterController extends BaseFrontController {

    private static final Logger log = LoggerFactory.getLogger(SysProfileController.class);

    @Autowired
    private IVipUserService vipUserService;
    @Autowired
    private IVipProfitDetailService vipProfitDetailService;
    @Autowired
    private IVipAddressService vipAddressService;
    @Autowired
    private IVipAccountService vipAccountService;
    @Autowired
    private IVipFeedbackService feedbackService;
    @Autowired
    private IVipAboutusService aboutusService;
    @Autowired
    private IVipExchangeService vipExchangeService;
    @Autowired
    private IVipBuyService buyService;
    @Autowired
    private ITradeService tradeService;

    /**
     * 个人信息
     * @param token 传过来token
     * @return
     */
    @PostMapping("/base")
    public ResponseResult userCenter(String token){

        try {
            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }

            ResponseResult map = new ResponseResult();
            map.put("ID",vipUser.getId());
            map.put("avater",vipUser.getAvater());
            map.put("nickname",vipUser.getNickname());
            map.put("extension",vipUser.getExtensionCode());
            map.put("recommend",vipUser.getRecommendCode());
            map.put("token",token);

            //将用户最新的信息保存到Redis中
            RedisUtils.setJson(token,vipUser,Long.parseLong(Global.getConfig("spring.redis.expireTime")));
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        } catch (Exception e) {
           log.error("个人信息接口出错");
           return ResponseResult.error();
        }
    }

    /**
     * 用户头像上传
     * @param token
     * @param file
     * @return
     */
    @PostMapping("/avaterUpload")
    public ResponseResult avaterUpload(@RequestParam("token") String token,@RequestParam("file") MultipartFile file){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        try {
            if (!file.isEmpty()) {
                //图片地址
                String path = uploadFile(file);
                vipUser.setAvater(path);
                if(vipUserService.updateVipUser(vipUser) > 0){
                    RedisUtils.setJson(token,vipUser, Long.parseLong(Global.getConfig("spring.redis.expireTime")));
                    return ResponseResult.responseResult(ResponseEnum.SUCCESS,CustomerConstants.SERVER_LINK+Global.getFrontPath()+path);
                }
            }
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_AVATER);
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
     * 个人资产
     * @param token
     * @return
     */
    @PostMapping("/assets")
    public ResponseResult userAssets(@RequestParam("token") String token){

        try {
            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }
            ResponseResult map = new ResponseResult();
            map.put("ssl",vipUser.getSslMoney());
            map.put("hkd",vipUser.getHkdMoney());
            map.put("moneyCode",vipUser.getMoneyCode());

            //交易明细
            //TODO


            map.put("token",token);

            //将用户最新的信息保存到Redis中
            RedisUtils.setJson(token,vipUser,Long.parseLong(Global.getConfig("spring.redis.expireTime")));
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
    }

    /**
     * 邀请链接
     * @param token
     * @return
     */
    @PostMapping("/inviteLink")
    public ResponseResult userInviteLink(@RequestParam("token") String token){
        try {

            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }
            
            ResponseResult map = new ResponseResult();
            //二维码
            map.put("extension",Global.getFrontPath() + vipUser.getExtensionCode());
            //推荐码
            map.put("recommend",vipUser.getRecommendCode());
            map.put("inviteLink",vipUser.getInviteLink());
            map.put("token",token);

            //将用户最新的信息保存到Redis中
            RedisUtils.setJson(token,vipUser,Long.parseLong(Global.getConfig("spring.redis.expireTime")));
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
    }

    /**
     * 团队列表
     * @param token
     * @return
     */
    @PostMapping("/userTeam")
    public ResponseResult userTeam(@RequestParam("token") String token){
        Map map = new HashMap();

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        //查询该会员下的一级会员
        VipUser oneUser = new VipUser();
        oneUser.setParentCode(vipUser.getRecommendCode());

        //一,二级会员列表
        List<VipUser> list1 = vipUserService.selectUserByParentCode(oneUser);
        List<VipUser> list2 = vipUserService.selectUserByGrandParentCode(oneUser);

        List<Map> list_map1 = new ArrayList<>();
        List<Map> list_map2 = new ArrayList<>();
        for (VipUser user : list1) {
            Map userMap = new HashMap();
            userMap.put("id",user.getId());
            userMap.put("phone",user.getPhone());
            userMap.put("avater",user.getAvater());
            userMap.put("nickname",user.getNickname());
            userMap.put("createTime",user.getCreateTime());
            list_map1.add(userMap);
        }
        for (VipUser user : list2) {
            Map userMap = new HashMap();
            userMap.put("id",user.getId());
            userMap.put("phone",user.getPhone());
            userMap.put("avater",user.getAvater());
            userMap.put("nickname",user.getNickname());
            userMap.put("createTime",user.getCreateTime());
            list_map2.add(userMap);
        }

        map.put("one",list_map1);
        map.put("two",list_map2);
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
    }


    //TODO 收益明细还待测试
    /**
     * 用户收益明细
     * @param token
     * @return
     */
    @PostMapping("/userProfit")
    public ResponseResult userProfit(@RequestParam("token") String token) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipProfitDetail detail = new VipProfitDetail();
        detail.setVipId(vipUser.getId());
        List<VipProfitDetail> vipProfitDetails = vipProfitDetailService.selectVipProfitDetailList(detail);
        Stream<VipProfitDetail> vipProfitDetailStream = vipProfitDetails.stream().filter(vipProfitDetail -> !vipProfitDetail.getProfitType().equals("0"));
        List<VipProfitDetail> collect = vipProfitDetailStream.collect(Collectors.toList());
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,collect);
    }


    /**
     * 用户收货地址列表
     * @param token
     * @return
     */
    @PostMapping("/userAddressList")
    public ResponseResult userAddressList(@RequestParam("token") String token) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipAddress address = new VipAddress();
        address.setVipId(vipUser.getId());
        List<VipAddress> vipAddresses = vipAddressService.selectVipAddressList(address);
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,vipAddresses);
    }

    /**
     * 收货地址添加
     * @param token
     * @param vipAddress
     * @return
     */
    /**
     * token:a3083b44-3edf-4926-b063-dfb88bbf0ec6
     * phone:17607147323
     * receivUser:大大
     * addressDetail:dagada共发生大
     * isDefault:Y
     * province:河南省
     * city:灵宝市
     * district:助阳镇
     */
    @PostMapping("/userAddressAdd")
    public ResponseResult userAddressAdd(@RequestParam("token") String token,VipAddress vipAddress) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        if(vipAddress.getIsDefault().equalsIgnoreCase(CustomerConstants.YES)){
            vipAddressService.updateDefaultAddress(vipUser.getId());
        }
        vipAddress.setVipId(vipUser.getId());
        vipAddress.setIsDefault(vipAddress.getIsDefault().toUpperCase());
        vipAddressService.insertVipAddress(vipAddress);
        return ResponseResult.success();
    }

    /**
     * 收货地址修改回显
     * @param token
     * @param id 收货地址id
     * @return
     */
    @PostMapping("/userAddressToEdit")
    public ResponseResult userAddressToEdit(@RequestParam("token") String token,@RequestParam("id") String id) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        VipAddress address = vipAddressService.selectVipAddressById(Integer.parseInt(id));

        Map map = new HashMap();
        map.put("id",address.getId());
        map.put("vipId",address.getVipId());
        map.put("phone",address.getPhone());
        map.put("receivUser",address.getReceivUser());
        map.put("addressDetail",address.getAddressDetail());
        map.put("isDefault",address.getIsDefault());
        map.put("province",address.getProvince());
        map.put("city",address.getCity());
        map.put("district",address.getDistrict());

        return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
    }

    /**
     * 收货地址执行修改
     * @param token
     * @param vipAddress
     * @return
     */
    @PostMapping("/userAddressEdit")
    public ResponseResult userAddressEdit(@RequestParam("token") String token,VipAddress vipAddress) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        try{
            if(vipAddress.getIsDefault().equalsIgnoreCase(CustomerConstants.YES)){
                vipAddressService.updateDefaultAddress(vipAddress.getVipId());
            }
            vipAddress.setIsDefault(vipAddress.getIsDefault().toUpperCase());

            vipAddressService.updateVipAddress(vipAddress);
        }catch (Exception e){
            return ResponseResult.error();
        }
        return ResponseResult.success();
    }

    /**
     * 收货地址删除
     * @param token
     * @param id  收货地址id
     * @return
     */
    @PostMapping("/userAddressRemove")
    public ResponseResult userAddressRemove(@RequestParam("token") String token,@RequestParam("id") String id) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        vipAddressService.deleteVipAddressByIds(id);
        return ResponseResult.success();
    }


    /**
     * 收款账户列表
     * @param token
     * @return
     */
    @PostMapping("/userAccountList")
    public ResponseResult userAccountList(@RequestParam("token") String token) {

        VipUser vipUser = userExist(token);

        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        List<VipAccount> vipAccounts = vipAccountService.selectVipAccountList(new VipAccount());

        List list = new ArrayList();
        vipAccounts.stream().forEach(account -> {
            Map map = new HashMap();
            map.put("id",account.getId());
            map.put("vipId",account.getVipId());
            map.put("accountType",account.getAccountType());
            map.put("account_name",account.getAccountName());
            map.put("account_number",account.getAccountNumber());
            map.put("account_img",account.getAccountImg());
            map.put("is_Default",account.getIsDefault());
            list.add(map);
        });


        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }

    /**
     * 收款账户添加
     * @param token
     * @return
     */
    @PostMapping("/userAccountAdd")
    public ResponseResult userAccountAdd(@RequestParam("token") String token,VipAccount account) {

        VipUser vipUser = userExist(token);

        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        account.setVipId(vipUser.getId());
        vipAccountService.insertVipAccount(account);
        return ResponseResult.responseResult(ResponseEnum.SUCCESS);
    }

    /**
     *收款账户修改回显
     * @param token
     * @param id 收货地址id
     * @return
     */
    @PostMapping("/userAccountToEdit")
    public ResponseResult userAccountToEdit(@RequestParam("token") String token,@RequestParam("id") String id) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        VipAccount account = vipAccountService.selectVipAccountById(Integer.parseInt(id));

        Map map = new HashMap();
        map.put("id",account.getId());
        map.put("vipId",account.getVipId());
        map.put("accountType",account.getAccountType());
        map.put("account_name",account.getAccountName());
        map.put("account_number",account.getAccountNumber());
        map.put("account_img",account.getAccountImg());
        map.put("is_Default",account.getIsDefault());
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
    }


    /**
     * 收款账户执行修改
     * @param token
     * @param account
     * @return
     */
    @PostMapping("/userAccountEdit")
    public ResponseResult userAccountEdit(@RequestParam("token") String token,VipAccount account) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        try{
            if(vipAccountService.updateVipAccount(account) > 0){
                return ResponseResult.success();
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }
        return ResponseResult.success();
    }

    /**
     * 收款账户删除
     * @param token
     * @param id  收货地址id
     * @return
     */
    @PostMapping("/userAccountRemove")
    public ResponseResult userAccountRemove(@RequestParam("token") String token,@RequestParam("id") String id) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        vipAccountService.deleteVipAccountByIds(id);
        return ResponseResult.success();
    }

    /**
     * 收款账户上传
     * @param token
     * @param file
     * @return
     */
    @PostMapping("/accountUpload")
    public ResponseResult accountUpload(@RequestParam("token") String token,@RequestParam("file") MultipartFile file){

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
                    map.put("serverPath",CustomerConstants.SERVER_LINK+Global.getFrontPath()+path);
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
     * 设置默认收款账户
     * @param token
     * @param , id,isDefault两个参数
     * @return
     */
    @PostMapping("/accountDefault")
    public ResponseResult accountDefault(String token,VipAccount account){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(account.getIsDefault().equalsIgnoreCase(CustomerConstants.YES)){
            vipAccountService.updateDefaultAccount(vipUser.getId());
        }
        account.setIsDefault(account.getIsDefault().toUpperCase());
        if(vipAccountService.updateVipAccount(account) > 0){
            return ResponseResult.success();
        }
        return ResponseResult.error();
    }

    /**
     * 用户反馈
     * @param token
     * @param feedback
     * @return
     */
    @PostMapping("/accountFeedBack")
    public ResponseResult accountFeedBack(String token,VipFeedback feedback){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        feedback.setVipId(vipUser.getId());
        feedbackService.insertVipFeedback(feedback);
        return ResponseResult.success();
    }

    /**
     * 关于我们
     * @return
     */
    @PostMapping("/aboutUs")
    public ResponseResult aboutUs(){
        List<VipAboutus> vipAboutuses = aboutusService.selectVipAboutusList(new VipAboutus());
        if(vipAboutuses.size() > 0){
            Map map = new HashMap();
            map.put("title",vipAboutuses.get(0).getTitle());
            map.put("content",vipAboutuses.get(0).getContent());
            map.put("id",vipAboutuses.get(0).getId());

            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,null);
    }

    /**
     * 兑换查询
     * @param token
     * @return
     */
    @PostMapping("/userExchangeList")
    public ResponseResult userExchangeList(String token){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipExchange exchange = new VipExchange();
        exchange.setVipId(vipUser.getId());
        exchange.getParams().put("exchange"," order by create_time desc");
        List<VipExchange> vipExchanges = vipExchangeService.selectVipExchangeList(exchange);
        List list = new ArrayList();

        vipExchanges.stream().forEach(vipExchange -> {
            Map map = new HashMap();
            map.put("exchangeAmount",vipExchange.getExchangeAmount());
            map.put("exchangeCharge",vipExchange.getExchangeCharge());
            map.put("exchangeMoney",vipExchange.getExchangeMoney());
            map.put("exchangeAccount",vipExchange.getExchangeAccount());
            map.put("exchangeType",vipExchange.getExchangeTime());
            map.put("exchangeStatus",vipExchange.getExchangeStatus());
            map.put("exchangeTime",vipExchange.getCreateTime());
            map.put("exchangeDetail",CustomerConstants.PRE_INVI_LINK+vipExchange.getExchangeDetail());
            list.add(map);
        });
        Map map = new HashMap();
        List<Trade> trades = tradeService.selectTradeList(new Trade());
        if(trades.size() > 0){
            map.put("charge",trades.get(0).getHkdCharge());
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list,map);
    }

    /**
     * 兑换
     * @param token
     * @param exchange
     * @return
     */
    @PostMapping("/userExchangeAdd")
    public ResponseResult userExchangeAdd(String token,VipExchange exchange){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        try{
            VipUser nowUser = vipUserService.selectVipUserById(vipUser.getId());
            double excount = Double.parseDouble(exchange.getExchangeAmount());
            //当前用户的hkd
            double hkd = Double.parseDouble(nowUser.getHkdMoney());
            //手续费
            double charge = 0.00;
            if(NumberUtil.sub(hkd,excount) < 0){
                return ResponseResult.responseResult(ResponseEnum.NUMBER_TOO_MAX);
            }

            if(excount < 0){
                return ResponseResult.responseResult(ResponseEnum.NUMBER_TOO_LOSS);
            }


            List<Trade> trades = tradeService.selectTradeList(new Trade());
            if(trades.size() > 0){
                charge = Double.parseDouble(trades.get(0).getHkdCharge());
            }

            //实际兑换金额
            double exchangeMoney = NumberUtil.mul(excount, NumberUtil.sub(1, charge));

            VipExchange exchange1 = new VipExchange();
            exchange1.setVipId(vipUser.getId());
            exchange1.setExchangeAccount(exchange.getExchangeAccount());
            exchange1.setExchangeAmount(String.valueOf(excount));
            exchange1.setExchangeCharge(String.valueOf(charge));
            exchange1.setExchangeStatus(CustomerConstants.EXCHANGE_BUY_STATUS_WAIT);
            exchange1.setExchangeTime(DateUtils.dateTimeNow("yyyy-MM-dd"));
            exchange1.setExchangeMoney(String.valueOf(exchangeMoney));
            vipExchangeService.insertVipExchange(exchange1);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return ResponseResult.responseResult(ResponseEnum.NUMBER_TRANCT_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }

        return ResponseResult.success();



    }

}
