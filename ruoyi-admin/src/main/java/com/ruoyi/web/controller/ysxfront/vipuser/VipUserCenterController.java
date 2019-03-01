package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.JwtUtils;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.web.controller.system.SysProfileController;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.VipAddress;
import com.ruoyi.yishengxin.domain.VipProfitDetail;
import com.ruoyi.yishengxin.domain.VipUser;
import com.ruoyi.yishengxin.service.IVipAddressService;
import com.ruoyi.yishengxin.service.IVipProfitDetailService;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    /**
     * 个人信息
     * @param token 传过来token
     * @return
     */
    @PostMapping("/base")
    public ResponseResult userCenter(@RequestParam("token") String token){

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
    public ResponseResult avaterUpload(@RequestParam("token") String token,@RequestParam("avatarfile") MultipartFile file){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        try {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String filetype = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
                if(!fileType(filetype,"pic")){
                    //头像格式不支持
                    return ResponseResult.responseResult(ResponseEnum.VIP_USER_AVATER_TYPE);
                }

                String avatar = FileUploadUtils.upload(Global.getFrontPath(), file,filetype);
                vipUser.setAvater(avatar);
                if(vipUserService.updateVipUser(vipUser) > 0){
                    RedisUtils.setJson(token,vipUser, Long.parseLong(Global.getConfig("spring.redis.expireTime")));
                    return ResponseResult.success();
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
        if(vipAddress.getIsDefault().equals(CustomerConstants.YES)){
            vipAddressService.updateDefaultAddress(vipUser.getId());
        }
        vipAddress.setVipId(vipUser.getId());
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
            if(vipAddress.getIsDefault().equals(CustomerConstants.YES)){
                vipAddressService.updateDefaultAddress(vipAddress.getVipId());
            }
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


}
