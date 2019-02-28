package com.ruoyi;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.ServerConfig;
import org.apache.commons.codec.digest.DigestUtils;

public class testMain {
    public static void main(String[] args) {
        /*String uuid = StringUtils.get8UUID();
        System.out.println(uuid);*/
        String hostIp = IpUtils.getHostIp();
        String hostName = IpUtils.getHostName();
        String link = hostIp+":"+ Global.getConfig("server.port");
        String encode = Base64Encoder.encode(link);
        String decode = Base64Decoder.decodeStr(encode);
        System.out.println(encode);
        System.out.println(decode);

        String s = IdUtil.simpleUUID();
        System.out.println(s);


        String loginPassword= DigestUtils.md5Hex("aa" + "bb");
        System.out.println(loginPassword);


    }
}
