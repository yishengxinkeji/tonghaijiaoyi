package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.util.JwtUtils;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.VipUser;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人中心接口
 */
@RestController
@RequestMapping("/front/vipUserCenter")
public class VipUserCenterController extends BaseFrontController {

    @Autowired
    private IVipUserService vipUserService;

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
            e.printStackTrace();
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



}
