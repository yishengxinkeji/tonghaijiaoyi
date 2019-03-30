package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.http.useragent.UserAgentUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.json.JSON;
import com.ruoyi.common.utils.BaiduDwz;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.JwtUtils;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.Gift;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IGiftService;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/front/vipUser")
public class VipUserLoginController extends BaseFrontController {

    @Autowired
    private IVipUserService vipUserService;

    @Autowired
    private IGiftService giftService;

    private static Pattern pattern = Pattern.compile(UserConstants.PHONE_PHONE_NUMBER_PATTERN);

    /**
     * 用户注册接口
     * @param phone     手机号
     * @param verification      验证码
     * @param password  密码
     * @param inviCode  邀请码
     * @return
     */
    @PostMapping("/register")
    public ResponseResult register(@RequestParam("phone") String phone,
                                   @RequestParam("verification") String verification,
                                   @RequestParam("password") String password,
                                   @RequestParam(value = "inviCode",required = false) String inviCode){

        //跳过验证码环节
        //TODO


        //手机号码不正确
        if(!pattern.matcher(phone).matches()){
            return ResponseResult.responseResult(ResponseEnum.PHONE_ERROR);
        }

        VipUser vipUser = new VipUser();

        vipUser.setPhone(phone);
        List<VipUser> vipUsers = vipUserService.selectVipUserList(vipUser);
        if(vipUsers.size() > 0){
            //号码已经注册
            return ResponseResult.responseResult(ResponseEnum.PHONE_EXIST_ERROR);
        }

        VipUser new_User = new VipUser();
        new_User.setPhone(phone);
        String salt = ShiroUtils.randomSalt();
        new_User.setSalt(salt);
        new_User.setAvater("/img/default_avater.png");
        new_User.setNickname("新用户");
        new_User.setIsMark(CustomerConstants.NO);   //未认证
        String loginPassword= DigestUtils.md5Hex(password + salt);
        new_User.setLoginPassword(loginPassword);
        //推荐码
        String exten = StringUtils.get8UUID();
        new_User.setRecommendCode(exten);

        if(!FileUtil.exist(Global.getFrontPath())){
            FileUtil.mkdir(Global.getFrontPath());
        }
        //使用hutool生成一个默认的二维码,链接指向手机端 8081端口
        String qrUrl = BaiduDwz.createShortUrl(Global.getConfig("tonghaijiaoyi.QrCode") + "?invicode=" + exten);
        File file = QrCodeUtil.generate(qrUrl, 300, 300, FileUtil.file(Global.getFrontPath() + exten+".jpg"));
        new_User.setExtensionCode(Global.getFrontPath()+file.getName());

        //钱包地址
        new_User.setMoneyCode(IdUtil.simpleUUID());
        //邀请链接,链接8080端口,指向pc端
        String pcUrl = BaiduDwz.createShortUrl(Global.getConfig("tonghaijiaoyi.inviLink") + "?invicode=" + exten);
        new_User.setInviteLink(pcUrl);

        new_User.setHkdMoney("0");
        new_User.setSslMoney("0");
        //未领取新人礼包
        new_User.setNewReceive(CustomerConstants.NO);

        if(inviCode != null && !inviCode.isEmpty()) {
            vipUser.setPhone("");
            vipUser.setRecommendCode(inviCode);
            List<VipUser> userList = vipUserService.selectVipUserList(vipUser);
            if(userList.size() > 0){
                String isFrozen = userList.get(0).getIsFrozen();
                if(isFrozen.equals(CustomerConstants.YES)){
                    return ResponseResult.responseResult(ResponseEnum.VIP_USER_FROZEN);
                }

                //父级邀请码
                new_User.setParentCode(userList.get(0).getRecommendCode());
            }else {
                new_User.setParentCode("-1");
            }
        }else {
            new_User.setParentCode("-1");
        }
        new_User.setIsFrozen(CustomerConstants.NO);
        new_User.setMaxTradeDay("-1");

        vipUserService.insertVipUser(new_User);

        return ResponseResult.responseResult(ResponseEnum.SUCCESS);

    }


    /**
     * 修改密码
     * @param phone     手机号
     * @param verification      验证码
     * @param password  密码
     * @param confirm   确认密码
     * @Param type  密码类型  login,trade
     * @return
     */
    @PostMapping("/forgetPassword")
    public ResponseResult forgetPassword(@RequestParam("phone") String phone,
                                         @RequestParam("verification") String verification,
                                         @RequestParam("password") String password,
                                         @RequestParam("confirm") String confirm,
                                         @RequestParam("type") String type){

        //跳过验证码环节
        //TODO

        if(!password.equals(confirm)){
            return ResponseResult.responseResult(ResponseEnum.PHONE_DIFFERENT_ERROR);
        }


        VipUser vipUser = new VipUser();
        vipUser.setPhone(phone);
        List<VipUser> userList = vipUserService.selectVipUserList(vipUser);
        if(userList.size() == 0){
            //密码不存在
            return ResponseResult.responseResult(ResponseEnum.PHONE_NOTEXIST_ERROR);
        }

        String newpassword= DigestUtils.md5Hex(password + userList.get(0).getSalt());
        if(type.equalsIgnoreCase("login")){
            vipUser.setLoginPassword(newpassword);
        }else if(type.equalsIgnoreCase("trade")){
            vipUser.setTradePassword(newpassword);
        }
        vipUserService.updateVipUser(vipUser);
        return ResponseResult.responseResult(ResponseEnum.SUCCESS);
    }


    /**
     * 用户登陆
     * @param phone
     * @param password
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestParam("phone") String phone,
                                @RequestParam("password") String password){

        //手机号码不正确
        if(!pattern.matcher(phone).matches()){
            return ResponseResult.responseResult(ResponseEnum.PHONE_ERROR);
        }

        String token = "";
        VipUser vipUser = new VipUser();
        vipUser.setPhone(phone);
        List<VipUser> userList = vipUserService.selectVipUserList(vipUser);
        if(userList.size() == 0){
            return ResponseResult.responseResult(ResponseEnum.PHONE_ERROR);
        }
        String salt = userList.get(0).getSalt();
        String pwd = DigestUtils.md5Hex(password + salt);
        if(!userList.get(0).getLoginPassword().equals(pwd)){
            //密码错误
            return ResponseResult.responseResult(ResponseEnum.VIP_PASSWORD_ERROR);
        }

        try{
            token = IdUtil.randomUUID();
            RedisUtils.setJson(token,userList.get(0), Long.parseLong(Global.getConfig("spring.redis.expireTime")));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.responseResult(ResponseEnum.FAIL);
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,token);
    }


    /**
     * 用户退出
     * @param token
     * @return
     */
    @PostMapping("/logout")
    public ResponseResult logout(@RequestHeader("token") String token){
        try{
            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.SUCCESS);
            }
            RedisUtils.del(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.responseResult(ResponseEnum.FAIL);
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS);
    }


    /**
     * 注册用户领好礼 弹窗接口
     * @param token     用户token
     * @return
     */
    @PostMapping("/registerGift")
    public ResponseResult registerGift(@RequestHeader("token") String token){
        try{
            VipUser vipUser = userExist(token);
            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }

            if(vipUser.getIsMark().equals(CustomerConstants.NO)){
                return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
            }

            if(vipUser.getNewReceive().equals(CustomerConstants.YES)){
                //礼包已领
                return ResponseResult.responseResult(ResponseEnum.VIP_GIFT_RECEIVE);
            }
            //新人礼包设置
            Gift gift = new Gift();
            List<Gift> gifts = giftService.selectGiftList(gift);
            if(gifts.size() > 0){
                String newNumber = gifts.get(0).getNewGift();
                String newType = gifts.get(0).getNewType();

                //礼包类型 SSL/HKD
                Map map = new HashMap();
                map.put("giftType",newType);
                //礼包金额
                map.put("giftNumber",newNumber);

                return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
            }
        } catch (Exception e){
            return ResponseResult.responseResult(ResponseEnum.FAIL);
        }
        //后台没有配置礼包
        return ResponseResult.responseResult(ResponseEnum.SUCCESS);
    }

    /**
     * 领取礼包
     * @param token
     * @return
     */
    @PostMapping("/receiveGift")
    public ResponseResult receiveGift(@RequestHeader("token") String token){

        String giftType = "";
        String giftNumber = "";

        Gift gift = new Gift();
        List<Gift> gifts = giftService.selectGiftList(gift);
        if(gifts.size() > 0){
            giftType = gifts.get(0).getNewType();
            giftNumber = gifts.get(0).getNewGift();
        }
        try {
            VipUser vipUser = userExist(token);

            if(vipUser == null){
                return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
            }
            if(vipUser.getIsMark().equals(CustomerConstants.NO)){
                return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
            }

            if(vipUser.getNewReceive().equals(CustomerConstants.YES)){
                //说明该用户已经领取了
                return ResponseResult.responseResult(ResponseEnum.VIP_GIFT_RECEIVE);
            }
            if(giftType.equals(CustomerConstants.SSL)){
                double sslmoney = NumberUtil.add(Double.parseDouble(vipUser.getSslMoney()), Double.parseDouble(giftNumber));
                vipUser.setSslMoney(String.valueOf(sslmoney));
            }else if(giftType.equals(CustomerConstants.HKD)){
                double hkdmoney = NumberUtil.add(Double.parseDouble(vipUser.getHkdMoney()), Double.parseDouble(giftNumber));
                vipUser.setHkdMoney(String.valueOf(hkdmoney));
            }
            vipUser.setNewReceive(CustomerConstants.YES);

            vipUserService.newReceiveGift(vipUser,giftType,giftNumber);
            RedisUtils.setJson(token,vipUser, Long.parseLong(Global.getConfig("spring.redis.expireTime")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error("领取失败");
        }
        return ResponseResult.success();
    }


    /**
     * 扫描二维码过来注册的
     * @param inviCode     推广码
     * @return
     */
    @GetMapping("/inviteRegister/{inviCode}")
    public ResponseResult inviteRegister(@RequestParam("inviCode") String inviCode){

       VipUser vipUser = new VipUser();
       vipUser.setRecommendCode(inviCode);
       List<VipUser> userList = vipUserService.selectVipUserList(vipUser);
       if(userList.size() == 0){
           //说明该邀请码用户不存在
           return ResponseResult.responseResult(ResponseEnum.VIP_USER_INVITE);
       }
       return ResponseResult.success();
    }
}
