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
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.tinify.Source;
import com.tinify.Tinify;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.digest.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

public class testMain {
    public static void main(String[] args) {
        BigDecimal round = NumberUtil.round(1.234564123, 2);
        System.out.println(round);

        String s = NumberUtil.roundStr(1.2341341, 2);
        System.out.println(s);

        Tinify.setKey("23CVG114Ctgxh6LCcNzdhG4rKjbvKpvl");
        Source source = null;
        try {
            source = Tinify.fromFile("C:\\Users\\Administrator\\Pictures\\1213123.jpg");
            source.toFile("D:\\download\\optimized.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
