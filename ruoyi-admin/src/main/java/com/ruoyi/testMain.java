package com.ruoyi;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ImageUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.BaiduDwz;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.ServerConfig;

import org.apache.commons.codec.digest.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

public class testMain {
    public static void main(String[] args) throws UnknownHostException {
//        BigDecimal round = NumberUtil.round(1.234564123, 2);
//        System.out.println(round);
//
//        String s = NumberUtil.roundStr(1.2341341, 2);
//        System.out.println(s);
//
//        Tinify.setKey("23CVG114Ctgxh6LCcNzdhG4rKjbvKpvl");
//        Source source = null;
//        try {
//            source = Tinify.fromFile("C:\\Users\\Administrator\\Pictures\\1213123.jpg");
//            source.toFile("D:\\download\\optimized.jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
       /* String res = BaiduDwz.createShortUrl("http://122.114.239.176:8080/dist/index.html#/register?invicode=3d94fe87");
        System.out.println(res);*/


     /*   InetAddress ia2 = InetAddress.getByName("dx.ipyy.net");
        System.out.println(ia2.toString());
        System.out.println(ia2.getHostName());//域名               127
        System.out.println(ia2.getHostAddress());//ip地址*/

     List<Map<String,String>> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("price","5.3");   //单价
        Map map1 = new HashMap();
        map1.put("price","6.5");   //单价
        Map map2 = new HashMap();
        map2.put("price","10.3");   //单价
        list.add(map);
        list.add(map1);
        list.add(map2);

        Collections.sort(list, (o1,o2)->{
            double d1=Double.valueOf(o1.get("price"));
            double d2=Double.valueOf(o2.get("price"));
            double result=d2-d1;
            if (result==0) {
                return 0;
            }
            if (result>0) {
                return 1;
            }
            return -1;
        });
        System.out.println(list);
    }
}
