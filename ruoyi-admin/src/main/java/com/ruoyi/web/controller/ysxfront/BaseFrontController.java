package com.ruoyi.web.controller.ysxfront;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.web.controller.sms.SSLClient;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * 前端控制器公共类
 */
@Component
public class BaseFrontController extends BaseController {

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

    /**
     * 发送短信
     * @param type 短信类型: 注册,买/卖 对应不同模版
     * @param phone
     * @throws Exception
     */
    public int sendMsg(String type,String phone) throws Exception {
        String url = "https://dx.ipyy.net/sms.aspx";
        String accountName="tonghai190408";							//改为实际账号名
        String password="tonghai190408";								//改为实际发送密码

        int i = RandomUtil.randomInt(1000,10000);
        String text = "";
        if(type.equals("1")){
            //注册
            text = String.format(CustomerConstants.REGISTER_MSG_TEMPLATE,String.valueOf(i));
        }
        if(type.equals("2")){
            //卖家确认收货
            String s = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtil.offsetDay(new Date(), 1));
            System.out.println(s);
            text = String.format(CustomerConstants.CONFIRM_ORDER_TEMPLATE,s);
        }

        Map map = new HashMap();
        map.put("action","send");
        map.put("userid", "");
        map.put("account", accountName);
        map.put("password", password);
        map.put("mobile", phone);       //多个手机号用逗号分隔
        map.put("content", text);
        map.put("sendTime", "");
        map.put("extno", "");

        HttpResponse execute = HttpRequest.post(url)
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .timeout(10000)
                .form(map)
                .execute();
        return i;
    }
}
