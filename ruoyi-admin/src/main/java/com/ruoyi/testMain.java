package com.ruoyi;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.ServerConfig;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.net.URL;

public class testMain {
    public static void main(String[] args) {
        /*String uuid = StringUtils.get8UUID();
        System.out.println(uuid);*/
        String hostIp = IpUtils.getHostIp();
        String hostName = IpUtils.getHostName();
        String link = hostIp+":"+ Global.getConfig("server.port");
        System.out.println(link);
       /* String encode = Base64Encoder.encode(link);
        String decode = Base64Decoder.decodeStr(encode);
        System.out.println(encode);
        System.out.println(decode);

        String s = IdUtil.simpleUUID();
        System.out.println(s);


        String loginPassword= DigestUtils.md5Hex("aa" + "bb");
        System.out.println(loginPassword);

        QrConfig config = new QrConfig(300, 300);
        config.setImg("D:\\profile\\avatar\\2019\\02\\28\\ff3f162c1c6c92d5583bb1ef6eac2148.jpg");

        QrCodeUtil.generate("http://hutool.cn/", 300, 300, FileUtil.file("D:/a.png"));


        File file = QrCodeUtil.generate("http://hutool.cn/", 300, 300, FileUtil.file(Global.getAvatarPath() + "/1111.jpg"));
        System.out.println(file.getName());*/

        /*String s = IdUtil.randomUUID();
        System.out.println(s);*/

        File file = new File("D:\\profile\\avatar\\01d24d16.jpg");
        String type = FileTypeUtil.getType(file);
        System.out.println(type);

    }
}
