package com.ruoyi.web.controller.ysxfront;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.yishengxin.domain.VipUser;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前端控制器公共类
 */
@Component
public class BaseFrontController {

    @Autowired
    private IVipUserService vipUserService;

    /**
     * 判断用户是否存在
     * @param token 数据库当前user对象
     * @return
     */
    protected VipUser userExist(String token){

        VipUser vipUser = RedisUtils.getJson(token, VipUser.class);
        if(vipUser == null){
            //用户不存在或者失效
            return null;
        }

        VipUser newUser = new VipUser();
        newUser.setPhone(vipUser.getPhone());
        newUser.setMoneyCode(vipUser.getMoneyCode());
        List<VipUser> userList = vipUserService.selectVipUserList(newUser);
        if(userList.size()  == 0){
            return null;
        }

        return userList.get(0);

    }



}
