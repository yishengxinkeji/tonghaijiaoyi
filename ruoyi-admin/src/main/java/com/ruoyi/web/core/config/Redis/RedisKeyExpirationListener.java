package com.ruoyi.web.core.config.Redis;

import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.TradeStatus;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeHkdBuy;
import com.ruoyi.yishengxin.domain.vipUser.VipTradeHkdSale;
import com.ruoyi.yishengxin.service.IVipTradeHkdBuyService;
import com.ruoyi.yishengxin.service.IVipTradeHkdSaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * redis键过期后处理
 */
@Component
public class RedisKeyExpirationListener implements  MessageListener {

    public static final Logger logger = LoggerFactory.getLogger(RedisKeyExpirationListener.class);

    @Autowired
    private IVipTradeHkdSaleService vipTradeHkdSaleService;
    @Autowired
    private IVipTradeHkdBuyService vipTradeHkdBuyService;

    /**
     * 针对redis数据失效事件，进行数据处理
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();

        String orderNo = "";
        //购买订单过期
        if(expiredKey.startsWith(CustomerConstants.LISTEN_TRADE_BUY_PREFIX_KEY) ){
            orderNo = expiredKey.split(CustomerConstants.LISTEN_TRADE_BUY_PREFIX_KEY)[1];
            VipTradeHkdBuy vipTradeHkdBuy = new VipTradeHkdBuy();
            vipTradeHkdBuy.setBuyNo(orderNo);
            VipTradeHkdBuy vipTradeHkdBuy1 = vipTradeHkdBuyService.selectVipTradeHkdBuyList(vipTradeHkdBuy).get(0);
            if(!vipTradeHkdBuy1.getBuyStatus().equals(TradeStatus.WAIT_SALE_CONFIRM.getCode())){
                //更新买/卖订单,并将钱退给卖家
                try {
                    int i = vipTradeHkdSaleService.updateOrderByNo(orderNo);
                    if(i > 0){
                        logger.info("过期订单更新成功 : "+orderNo);
                    }
                    logger.error("过期订单更新失败: " + orderNo);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("过期订单更新失败: " + e.getMessage());
                }
            }

        }else if(expiredKey.startsWith(CustomerConstants.LISTEN_TRADE_SALE_PREFIX_KEY)){
            orderNo = expiredKey.split(CustomerConstants.LISTEN_TRADE_SALE_PREFIX_KEY)[1];
            VipTradeHkdSale vipTradeHkdSale = new VipTradeHkdSale();
            vipTradeHkdSale.setSaleNo(orderNo);
            VipTradeHkdSale vipTradeHkdSale1 = vipTradeHkdSaleService.selectVipTradeHkdSaleList(vipTradeHkdSale).get(0);
            if(!vipTradeHkdSale1.getSaleStatus().equals(TradeStatus.SUCCESS.getCode())){
                //更新买/卖订单,并将钱退给卖家
                try {
                    int i = vipTradeHkdSaleService.updateOrderByNo(orderNo);
                    if(i > 0){
                        logger.info("过期订单更新成功 : "+orderNo);
                    }
                    logger.error("过期订单更新失败: " + orderNo);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("过期订单更新失败: " + e.getMessage());
                }
            }
        }
    }
}
