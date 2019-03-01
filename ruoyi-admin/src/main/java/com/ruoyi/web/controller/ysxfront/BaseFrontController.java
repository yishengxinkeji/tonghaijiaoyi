package com.ruoyi.web.controller.ysxfront;

import cn.hutool.core.io.FileTypeUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.yishengxin.domain.VipUser;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
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

    //判断文件格式,可能后续会改,目前只支持这几种

    /**
     * 判断用户类型
     * @param filetype  文件后缀
     * @param type  为了后续扩展,传入不同type,进行不同判断
     * @return
     */
    protected boolean fileType(String filetype,String type){
        if(type.equals("pic")){
            if(filetype.equalsIgnoreCase("jpg") || filetype.equalsIgnoreCase("png")
                    || filetype.equalsIgnoreCase("jpeg")){
                return true;
            }
        }
        return false;
    }




}
