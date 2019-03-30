package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.*;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.utils.BaiduDwz;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.RegexUtils;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.web.controller.system.SysProfileController;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.PlatData;
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.domain.Transfer;
import com.ruoyi.yishengxin.domain.vipUser.*;
import com.ruoyi.yishengxin.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
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
    @Autowired
    private IPlatDataService platDataService;
    @Autowired
    private IVipTradeService vipTradeService;
    @Autowired
    private IVipAppealService appealService;
    @Autowired
    private ITransferService transferService;


    /**
     * 个人信息
     * @param token 传过来token
     * @return
     */
    @PostMapping("/base")
    public ResponseResult userCenter(@RequestHeader("token") String token){

        System.out.println(token);
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
            map.put("isMark",vipUser.getIsMark());

            //将用户最新的信息保存到Redis中
            RedisUtils.setJson(token,vipUser,Long.parseLong(Global.getConfig("spring.redis.expireTime")));
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        } catch (Exception e) {
           log.error("个人信息接口出错");
           return ResponseResult.error();
        }
    }

    /**
     * 修改昵称
     * @param token
     * @return
     */
    @PostMapping("/nickname")
    public ResponseResult nickname(@RequestHeader("token") String token,@RequestParam("nickname") String nickname){
        try {
            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }

            if(vipUser.getIsMark().equals(CustomerConstants.NO)){
                return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
            }

            vipUser.setNickname(nickname);
            int i = vipUserService.updateVipUser(vipUser);
            if(i > 0){
                return ResponseResult.success();
            }
            return ResponseResult.error();
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
    public ResponseResult avaterUpload(@RequestHeader("token") String token,@RequestParam("file") MultipartFile file){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }
        try {
            if (!file.isEmpty()) {
                //图片地址
                String path = uploadFile(file);
                vipUser.setAvater(Global.getFrontPath()+path);
                if(vipUserService.updateVipUser(vipUser) > 0){
                    RedisUtils.setJson(token,vipUser, Long.parseLong(Global.getConfig("spring.redis.expireTime")));
                    return ResponseResult.responseResult(ResponseEnum.SUCCESS,Global.getFrontPath()+path);
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
    public ResponseResult userAssets(@RequestHeader("token") String token){

        try {
            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }

            if(vipUser.getIsMark().equals(CustomerConstants.NO)){
                return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
            }

            List<Transfer> transfers = transferService.selectTransferList(new Transfer());
            ResponseResult map = new ResponseResult();
            map.put("ssl",vipUser.getSslMoney());
            map.put("hkd",vipUser.getHkdMoney());
            map.put("moneyCode",vipUser.getMoneyCode());
            if(transfers.size() > 0){
                map.put("transferId",transfers.get(0).getId());
            }else {
                map.put("transferId","");
            }
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
    }

    /**
     * 转出
     * @param token
     * @param type  类型:SSL,HKD
     * @param number 数量
     * @param toMoneyCode 目标钱包地址
     * @param toId  目标ID
     * @return
     */
    @PostMapping("/tranSport")
    public ResponseResult tranSport(@RequestHeader("token") String token,
                                    @RequestParam("type") String type,
                                    @RequestParam("number") String number,
                                    @RequestParam("toMoneyCode") String toMoneyCode,
                                    @RequestParam("toId") String toId){


        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }

        if(toId.equals(String.valueOf(vipUser.getId())) || toMoneyCode.equals(vipUser.getMoneyCode()) ){
            return ResponseResult.responseResult(ResponseEnum.CAN_NOT_BUY_YOURSELF);
        }

        if(vipUser.getIsFrozen().equalsIgnoreCase(CustomerConstants.YES)){
            //用户已被冻结
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_FROZEN);
        }
        VipUser myVip = vipUserService.selectVipUserById(vipUser.getId());

        String sslMoney = myVip.getSslMoney();
        String hkdMoney = myVip.getHkdMoney();
        double tranMoney = 0.00;
        //目标会员
        VipUser vipUser1 = new VipUser();
        vipUser1.setId(Integer.parseInt(toId));
        vipUser1.setMoneyCode(toMoneyCode);
        List<VipUser> vipUsers = vipUserService.selectVipUserList(vipUser1);
        //不存在该用户
        if(vipUsers.size() == 0){
            return ResponseResult.responseResult(ResponseEnum.PHONE_NOTEXIST_ERROR);
        }

        //ssl每天最大互转量
        double maxSslDeliverDay = 0.00;
        //ssl每笔最低互转量
        double minSslDeliverTime = 0.00;
        //hkd每天最大互转量
        double maxHkdDeliverDay = 0.00;
        //hkd每笔最小互转量
        double minHkdDeliverTime = 0.00;

        List<Trade> trades = tradeService.selectTradeList(new Trade());
        if(trades.size() > 0){

            maxSslDeliverDay  =Double.parseDouble(trades.get(0).getMaxSslDeliverDay());
            minSslDeliverTime =Double.parseDouble(trades.get(0).getMinSslDeliverTime());
            maxHkdDeliverDay =Double.parseDouble(trades.get(0).getMaxHdkDeliverDay());
            minHkdDeliverTime =Double.parseDouble(trades.get(0).getMinHdkDeliverTime());

        }


        VipTrade vipTrade = new VipTrade();
        vipTrade.setVipId(vipUser.getId());

        try{
            if(type.equalsIgnoreCase("ssl")){
                tranMoney = NumberUtil.sub(Double.parseDouble(sslMoney),Double.parseDouble(number));
                if(tranMoney < 0){
                    //SSL币不足
                    return ResponseResult.responseResult(ResponseEnum.VIP_USER_SSLINSUFFICIENT);
                }
                if(Double.parseDouble(number) < minSslDeliverTime){
                    //单次转出少于最小转出
                    return ResponseResult.responseResult(ResponseEnum.MIN_TRADE_BY_TIME);
                }

                vipTrade.setVipTrade(TradeType.OUT_SSL.getCode());
                String tranDay = vipTradeService.selectTranByDay(vipTrade);
                if(Double.parseDouble(tranDay) > maxSslDeliverDay){
                    //今日交易已达上限
                    return ResponseResult.responseResult(ResponseEnum.MAX_TRADE_BY_DAY);
                }

                int i = vipUserService.tranSport(myVip,vipUsers.get(0),type,number,tranMoney);
                if(i > 0){
                    return ResponseResult.success();
                }
            }else if(type.equalsIgnoreCase("hkd")){
                tranMoney = NumberUtil.sub(Double.parseDouble(hkdMoney),Double.parseDouble(number));
                if(tranMoney < 0){
                    //HKD币不足
                    return ResponseResult.responseResult(ResponseEnum.VIP_USER_HKDINSUFFICIENT);
                }

                if(Double.parseDouble(number) > maxHkdDeliverDay){
                    //单次交易已达上限
                    return ResponseResult.responseResult(ResponseEnum.MAX_TRADE_BY_TIME);
                }
                if(Double.parseDouble(number) < minHkdDeliverTime){
                    //单次转出少于最小转出
                    return ResponseResult.responseResult(ResponseEnum.MIN_TRADE_BY_TIME);
                }
                int i = vipUserService.tranSport(myVip,vipUsers.get(0),type,number,tranMoney);
                if(i > 0){
                    return ResponseResult.success();
                }
            }
            return ResponseResult.error();
        }catch (Exception e){
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
    public ResponseResult userInviteLink(@RequestHeader("token") String token){
        try {

            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }

            if(vipUser.getIsMark().equals(CustomerConstants.NO)){
                return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
            }
            ResponseResult map = new ResponseResult();
            //二维码
            map.put("extension",vipUser.getExtensionCode());
            //推荐码
            map.put("recommend",vipUser.getRecommendCode());
            map.put("inviteLink",vipUser.getInviteLink());  //pc端的
            //使用hutool生成一个默认的二维码,链接指向手机端 8081端口
            String qrUrl = BaiduDwz.createShortUrl(Global.getConfig("tonghaijiaoyi.QrCode") + "?invicode=" + vipUser.getRecommendCode());
            map.put("mobileLink",qrUrl);

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
    public ResponseResult userTeam(@RequestHeader("token") String token){
        Map map = new HashMap();

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
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


    /**
     * 用户收益明细
     * @param token
     * @return
     */
    @PostMapping("/userProfit")
    public ResponseResult userProfit(@RequestHeader("token") String token) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }
        try{
            VipProfitDetail detail = new VipProfitDetail();
            detail.setVipId(vipUser.getId());
            List<VipProfitDetail> vipProfitDetails = vipProfitDetailService.selectVipProfitDetailList(detail);
            Stream<VipProfitDetail> vipProfitDetailStream = vipProfitDetails.stream().filter(vipProfitDetail -> !vipProfitDetail.getProfitType().equals(ProfitType.SELF.getCode()));
            List<VipProfitDetail> collect = vipProfitDetailStream.collect(Collectors.toList());
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,collect);
        }catch (Exception e){
            return ResponseResult.error();
        }

    }


    /**
     * 用户收货地址列表
     * @param token
     * @param id  可以查询某一个
     * @return
     */
    @PostMapping("/userAddressList")
    public ResponseResult userAddressList(@RequestHeader("token") String token,@RequestParam(value = "id",required = false) String id) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }
        try{
            VipAddress address = new VipAddress();
            address.setVipId(vipUser.getId());
            if(id != null && !id.equals("")){
                address.setId(Integer.parseInt(id));
            }
            List<VipAddress> vipAddresses = vipAddressService.selectVipAddressList(address);
            List list = new ArrayList();
            vipAddresses.stream().forEach(vipAddress->{
                Map map = new HashMap();
                map.put("id",vipAddress.getId());
                map.put("vipId",vipAddress.getVipId());
                map.put("phone",vipAddress.getPhone());
                map.put("receivUser",vipAddress.getReceivUser());
                map.put("addressDetail",vipAddress.getAddressDetail());
                map.put("isDefault",vipAddress.getIsDefault());
                map.put("province",vipAddress.getProvince());
                map.put("city",vipAddress.getCity());
                map.put("district",vipAddress.getDistrict());
                list.add(map);
            });

            return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
        }catch (Exception e){
            return ResponseResult.error();
        }

    }

    /**
     * 收货地址添加
     * @param token
     * @param vipAddress
     * @return
     */
    @PostMapping("/userAddressAdd")
    public ResponseResult userAddressAdd(@RequestHeader("token") String token,VipAddress vipAddress) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }
        try{
            if(vipAddress.getIsDefault().equalsIgnoreCase(CustomerConstants.YES)){
                vipAddressService.updateDefaultAddress(vipUser.getId());
            }
            vipAddress.setVipId(vipUser.getId());
            vipAddress.setIsDefault(vipAddress.getIsDefault().toUpperCase());
            vipAddressService.insertVipAddress(vipAddress);
            return ResponseResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }

    }

    /**
     * 购买页面查询默认地址
     * @param token
     * @return
     */
    @PostMapping("/userAddress")
    public ResponseResult userAddress(@RequestHeader("token") String token) {
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }

        VipAddress vipAddress = new VipAddress();
        vipAddress.setVipId(vipUser.getId());
        vipAddress.setIsDefault(CustomerConstants.YES);
        List<VipAddress> vipAddresses = vipAddressService.selectVipAddressList(vipAddress);
        if(vipAddresses.size() == 0){
            return ResponseResult.responseResult(ResponseEnum.VIP_ACCOUNT_NO_DEFAULT);
        }

        vipAddress = vipAddresses.get(0);
        Map map = new HashMap();
        map.put("id",vipAddress.getId());
        map.put("vipId",vipAddress.getVipId());
        map.put("phone",vipAddress.getPhone());
        map.put("receivUser",vipAddress.getReceivUser());
        map.put("addressDetail",vipAddress.getAddressDetail());
        map.put("isDefault",vipAddress.getIsDefault());
        map.put("province",vipAddress.getProvince());
        map.put("city",vipAddress.getCity());
        map.put("district",vipAddress.getDistrict());

        return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
    }

    /**
     * 收货地址修改回显
     * @param token
     * @param id 收货地址id
     * @return
     */
    @PostMapping("/userAddressToEdit")
    public ResponseResult userAddressToEdit(@RequestHeader("token") String token,@RequestParam("id") String id) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
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
    public ResponseResult userAddressEdit(@RequestHeader("token") String token,VipAddress vipAddress) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
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
     * 设置默认地址
     * @param token
     * @param id
     * @return
     */
    @PostMapping("/userAddressDefault")
    public ResponseResult userAddressDefault(@RequestHeader("token") String token,@RequestParam("id") String id) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }

        try{
            VipAddress vipAddress = new VipAddress();
            vipAddress.setId(Integer.parseInt(id));
            vipAddress.setIsDefault(CustomerConstants.YES);
            if(vipAddressService.updateDefaultAddress(vipUser.getId()) > 0){
                vipAddressService.updateVipAddress(vipAddress);
            }
            return ResponseResult.success();
        }catch (Exception e){
            return ResponseResult.error();
        }
    }

    /**
     * 收货地址删除
     * @param token
     * @param id  收货地址id
     * @return
     */
    @PostMapping("/userAddressRemove")
    public ResponseResult userAddressRemove(@RequestHeader("token") String token,@RequestParam("id") String id) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }
        try{
            vipAddressService.deleteVipAddressByIds(id);
            return ResponseResult.success();
        }catch (Exception e){
            return ResponseResult.error();
        }

    }


    /**
     * 收款账户列表
     * @param token
     * @return
     */
    @PostMapping("/userAccountList")
    public ResponseResult userAccountList(@RequestHeader("token") String token) {

        VipUser vipUser = userExist(token);

        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }

        VipAccount vipAccount = new VipAccount();
        vipAccount.setVipId(vipUser.getId());
        List<VipAccount> vipAccounts = vipAccountService.selectVipAccountList(vipAccount);

        List list = new ArrayList();
        vipAccounts.stream().forEach(account -> {
            Map map = new HashMap();
            map.put("id",account.getId());
            map.put("vipId",account.getVipId());
            map.put("accountType",account.getAccountType());
            map.put("accountName",account.getAccountName());
            map.put("accountNumber",account.getAccountNumber());
            map.put("accountImg",account.getAccountImg());
            map.put("isDefault",account.getIsDefault());
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
    public ResponseResult userAccountAdd(@RequestHeader("token") String token,VipAccount account) {

        VipUser vipUser = userExist(token);

        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }

        account.setVipId(vipUser.getId());
        account.setIsDefault(CustomerConstants.NO);
        try{
            vipAccountService.insertVipAccount(account);
            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }catch (Exception e){
            return ResponseResult.error();
        }

    }

    /**
     *收款账户修改回显
     * @param token
     * @param id 收货地址id
     * @return
     */
    @PostMapping("/userAccountToEdit")
    public ResponseResult userAccountToEdit(@RequestHeader("token") String token,@RequestParam("id") String id) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }
        VipAccount account = vipAccountService.selectVipAccountById(Integer.parseInt(id));

        Map map = new HashMap();
        map.put("id",account.getId());
        map.put("vipId",account.getVipId());
        map.put("accountType",account.getAccountType());
        map.put("accountName",account.getAccountName());
        map.put("accountNumber",account.getAccountNumber());
        map.put("accountImg",account.getAccountImg());
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
    }


    /**
     * 收款账户执行修改
     * @param token
     * @param account
     * @return
     */
    @PostMapping("/userAccountEdit")
    public ResponseResult userAccountEdit(@RequestHeader("token") String token,VipAccount account) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
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
    public ResponseResult userAccountRemove(@RequestHeader("token") String token,@RequestParam("id") String id) {

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
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
    public ResponseResult accountUpload(@RequestHeader("token") String token,@RequestParam("file") MultipartFile file){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
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
     * 设置默认收款账户
     * @param token
     * @param , id
     * @return
     */
    @PostMapping("/accountDefault")
    public ResponseResult accountDefault(@RequestHeader("token")String token,@RequestParam("id") String id){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }

        if(vipAccountService.updateDefaultAccount(vipUser.getId(),Integer.parseInt(id)) > 0){
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
    public ResponseResult accountFeedBack(@RequestHeader("token")String token,VipFeedback feedback){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }
        feedback.setVipId(vipUser.getId());
        feedbackService.insertVipFeedback(feedback);
        return ResponseResult.success();
    }

    /**
     * 关于我们
     * @return
     */
    @GetMapping("/aboutUs")
    public ResponseResult aboutUs(){
        List<VipAboutus> vipAboutuses = aboutusService.selectVipAboutusList(new VipAboutus());
        List list = new ArrayList();
        if(vipAboutuses.size() > 0){
            Map map = new HashMap();
            map.put("title",vipAboutuses.get(0).getTitle());
            map.put("content",vipAboutuses.get(0).getContent());
            map.put("id",vipAboutuses.get(0).getId());
            list.add(map);

        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }

    /**
     * 我的申诉
     * @return
     */
    @PostMapping("/myAppeal")
    public ResponseResult myAppeal(@RequestHeader("token")String token){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }
        VipAppeal vipAppeal = new VipAppeal();
        vipAppeal.setAppealVipId(String.valueOf(vipUser.getId()));
        vipAppeal.getParams().put("VipAppeal"," order by id asc");
        List<VipAppeal> vipAppeals = appealService.selectVipAppealList(vipAppeal);
        List list = new ArrayList();
        vipAppeals.stream().forEach(vipAppeal1 -> {
            Map map = new HashMap();
            map.put("time",vipAppeal1.getAppealTime());
            map.put("status",vipAppeal1.getAppealStatus());
            map.put("content",vipAppeal1.getContent());
            list.add(map);
        });
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }

    /**
     * 兑换查询
     * @param token
     * @return
     */
    @PostMapping("/userExchangeList")
    public ResponseResult userExchangeList(@RequestHeader("token") String token){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
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
            map.put("exchangeStatus",vipExchange.getExchangeStatus());
            map.put("exchangeTime",vipExchange.getCreateTime());
            if(vipExchange.getExchangeStatus().equals(CustomerConstants.EXCHANGE_BUY_STATUS_DEAL)){
                map.put("exchangeDetail",vipExchange.getExchangeDetail());
            }
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
    public ResponseResult userExchangeAdd(@RequestHeader("token") String token,VipExchange exchange){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }

        if(vipUser.getIsFrozen().equalsIgnoreCase(CustomerConstants.YES)){
            //用户已被冻结
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_FROZEN);
        }
        try{
            VipUser nowUser = vipUserService.selectVipUserById(vipUser.getId());
            double excount = Double.parseDouble(exchange.getExchangeAmount());
            //当前用户的hkd
            double hkd = Double.parseDouble(nowUser.getHkdMoney());
            //手续费
            double charge = 0.00;
            if(NumberUtil.sub(hkd,excount) < 0){
                //hkd币不足
                return ResponseResult.responseResult(ResponseEnum.VIP_USER_HKDINSUFFICIENT);
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

    /**
     * 购买查询
     * @param token
     * @return
     */
        @PostMapping("/userBuyList")
    public ResponseResult userBuyList(@RequestHeader("token") String token){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

            if(vipUser.getIsMark().equals(CustomerConstants.NO)){
                return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
            }
        VipBuy vipBuy = new VipBuy();
        vipBuy.setVipId(vipUser.getId());
        vipBuy.getParams().put("buy"," order by create_time desc");

        List<VipBuy> vipBuys = buyService.selectVipBuyList(vipBuy);
        List list = new ArrayList();

        vipBuys.stream().forEach(vipBuy1 -> {
            Map map = new HashMap();
            map.put("buyAmount",vipBuy1.getBuyAmount());
            map.put("buyMoney",vipBuy1.getBuyMoney());
            map.put("buyDetail",vipBuy1.getBuyDetail());
            map.put("buyStatus",vipBuy1.getBuyStatus());
            map.put("createTime",DateUtils.parseDateToStr("yyyy-MM-dd",vipBuy1.getCreateTime()));
            list.add(map);
        });

        Map map = new HashMap();
        List<PlatData> platData = platDataService.selectPlatDataList(new PlatData());
        if(platData.size() > 0){
            map.put("account",platData.get(0).getPlatAccount());
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list,map);
    }

    /**
     * 用户购买提交凭证
     * @param token
     * @param file
     * @return
     */
    @PostMapping("/buyUpload")
    public ResponseResult userBuyUpdate(@RequestHeader("token") String token,@RequestParam("file") MultipartFile file){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }
        try {
            if (!file.isEmpty()) {
                //图片地址
                String path = uploadFile(file);
                Map map = new HashMap();
                map.put("serverPath",Global.getFrontPath()+path);
                map.put("path",Global.getFrontPath()+path);
                return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
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
     * 购买
     * @param token
     * @param vipBuy
     * @return
     */
    @PostMapping("/buyAdd")
    public ResponseResult buyAdd(@RequestHeader("token") String token,VipBuy vipBuy){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }

        if(vipUser.getIsFrozen().equalsIgnoreCase(CustomerConstants.YES)){
            //用户已被冻结
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_FROZEN);
        }
        try{
            VipUser nowUser = vipUserService.selectVipUserById(vipUser.getId());
            VipBuy vipBuy1 = new VipBuy();
            vipBuy1.setVipId(nowUser.getId());
            vipBuy1.setBuyAccount(vipBuy.getBuyAccount());
            vipBuy1.setBuyAmount(vipBuy.getBuyAmount());
            vipBuy1.setBuyDetail(vipBuy.getBuyDetail());
            vipBuy1.setBuyMoney(vipBuy.getBuyMoney());
            vipBuy1.setBuyStatus(CustomerConstants.EXCHANGE_BUY_STATUS_WAIT);
            vipBuy1.setCreateTime(vipBuy.getCreateTime());
            buyService.insertVipBuy(vipBuy1);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return ResponseResult.responseResult(ResponseEnum.NUMBER_TRANCT_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }

        return ResponseResult.success();
    }

    /**
     * 身份证正反面图片上传
     * @param file
     * @return
     */
    @PostMapping("/cardUpload")
    public ResponseResult cardUpload(@RequestParam("file") MultipartFile file){

        try {
            if (!file.isEmpty()) {
                //图片地址
                String path = uploadFile(file);
                return ResponseResult.responseResult(ResponseEnum.SUCCESS,Global.getFrontPath()+path);
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
     * 身份认证
     * @param token
     * @return
     */
    @PostMapping("/identify")
    public ResponseResult identify(@RequestHeader("token") String token,
                                   @RequestParam("idCard") String idCard,
                                   @RequestParam("realName") String realName,
                                   @RequestParam("frontImg") String frontImg,
                                   @RequestParam("backImg") String backImg
                                   ){

        VipUser vipUser1 = userExist(token);
        if(vipUser1 == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(!idCard.isEmpty()){
            if(!Pattern.matches(RegexUtils.IDENTIFY_REGEX,idCard)){
                //身份证格式不正确
                return ResponseResult.responseResult(ResponseEnum.IDENTIFY_REGEX);
            }
        }
        vipUser1.setIsMark(CustomerConstants.YES);
        vipUser1.setIdCard(idCard);
        vipUser1.setRealName(realName);
        vipUser1.setFrontImg(frontImg);
        vipUser1.setBackImg(backImg);

        vipUserService.updateVipUser(vipUser1);
        RedisUtils.setJson(token,vipUser1, Long.parseLong(Global.getConfig("spring.redis.expireTime")));
        return ResponseResult.success();
    }

    /**
     * 去进行身份认证
     * @param token
     * @return
     */
    @PostMapping("/toLdentify")
    public ResponseResult toLdentify(@RequestHeader("token") String token){

        VipUser vipUser1 = userExist(token);
        if(vipUser1 == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        Map map = new HashMap();
        map.put("idCard",vipUser1.getIdCard());
        map.put("realName",vipUser1.getRealName());
        map.put("frontImg",vipUser1.getFrontImg());
        map.put("backImg",vipUser1.getBackImg());
        map.put("isMark",vipUser1.getIsMark());
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
    }


}
