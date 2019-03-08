package com.ruoyi.web.controller.ysxfront;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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


    /**
     * 上传文件
     * @param file
     * @return  图片路径
     * @throws IOException
     */
    protected String uploadFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String filetype = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        if(!fileType(filetype,"pic")){
            //头像格式不支持
            return null;
        }
        String avatar = FileUploadUtils.upload(Global.getFrontPath(), file,filetype);
        return avatar;
    }




    //判断文件格式,可能后续会改,目前只支持这几种

    /**
     * 判断用户类型
     * @param filetype  文件后缀
     * @param type  为了后续扩展,传入不同type,进行不同判断
     * @return
     */
    protected boolean fileType(String filetype,String type){
        //pic就代表是图片类型
        if(type.equals("pic")){
            if(filetype.equalsIgnoreCase(".jpg") || filetype.equalsIgnoreCase(".png")
                    || filetype.equalsIgnoreCase(".jpeg")){
                return true;
            }
        }
        return false;
    }






}
