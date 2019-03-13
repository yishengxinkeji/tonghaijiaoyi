package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.LockStatus;
import com.ruoyi.common.enums.LockType;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.RegexUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.Trade;
import com.ruoyi.yishengxin.domain.vipUser.VipLock;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.ITradeService;
import com.ruoyi.yishengxin.service.IVipLockService;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 锁仓
 */
@RestController
@RequestMapping("front/lock")
public class LockController extends BaseFrontController {

    @Autowired
    private ITradeService tradeService;
    @Autowired
    private IVipLockService vipLockService;
    @Autowired
    private IVipUserService vipUserService;

    /**
     * 锁仓页面
     * @return
     */
    @GetMapping("/toLock")
    public ResponseResult toLock(){

        List<Trade> trades = tradeService.selectTradeList(new Trade());
        Trade trade = trades.get(0);
        Map map = new HashMap();
        map.put("one",trade.getOneRate());
        map.put("three",trade.getThreeRate());
        map.put("six",trade.getSixRate());
        map.put("twelve",trade.getTwelveRate());
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
    }

    /**
     * 锁仓中
     * @param token
     * @param type  锁仓类型(1,3,6,12);
     * @return
     */
    @PostMapping("/locking")
    public ResponseResult locking(@RequestHeader("token") String token,@RequestParam(value = "type",required = false) String type){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipLock vipLock = new VipLock();
        vipLock.setLockStatus(LockStatus.LOCKING.getCode());
        if(type != null && !type.equals("")){
            vipLock.setLockType(type);
        }
        vipLock.setVipId(vipUser.getId());

        List<VipLock> vipLocks = vipLockService.selectVipLockList(vipLock);
        List list = new ArrayList();
        if(vipLocks.size() > 0){
            vipLocks.stream().forEach(vipLock1 -> {
                Map map = new HashMap();
                map.put("time",vipLock1.getLockTime());
                map.put("number",vipLock1.getLockNumber());
                map.put("profit",NumberUtil.sub(Double.parseDouble(vipLock1.getLockProfit()),Double.parseDouble(vipLock1.getLockNumber())));
                map.put("id",vipLock1.getId());
                list.add(map);
            });
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }

    /**
     * 已收货
     * @param token
     * @param type  锁仓类型(1,3,6,12);
     * @return
     */
    @PostMapping("/lockProfit")
    public ResponseResult lockProfit(@RequestHeader("token") String token,@RequestParam(value = "type",required = false) String type){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipLock vipLock = new VipLock();
        vipLock.setVipId(vipUser.getId());
        if(type != null && !type.equals("")){
            vipLock.setLockType(type);
        }
        List<VipLock> vipLocks = vipLockService.selectVipLockList(vipLock);
        List list = new ArrayList();

        if(vipLocks.size() > 0){
            vipLocks.stream().forEach(vipLock1 -> {
                Map map = new HashMap();
                map.put("time",vipLock1.getLockTime());
                map.put("number",vipLock1.getLockNumber());
                if(Double.parseDouble(vipLock1.getLockProfit())< 0){
                    map.put("profit",vipLock1.getLockProfit());
                }else {
                    map.put("profit",String.valueOf(NumberUtil.sub(Double.parseDouble(vipLock1.getLockProfit()),Double.parseDouble(vipLock1.getLockNumber()))));
                }
                list.add(map);
            });
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }


    /**
     * 锁仓
     * @param token
     * @param type  类型(1,3,6,12)
     * @param number    锁仓数量
     * @return
     */
    @PostMapping("/lock")
    public ResponseResult lock(@RequestHeader("token") String token,
                               @RequestParam("type") String type,
                               @RequestParam("number") String number){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        try{

            if(!ReUtil.isMatch(RegexUtils.INTEGER_REGEX, number)){
                //只能输入整数
                return ResponseResult.responseResult(ResponseEnum.NUMBER_INTEGRAL);
            }

            List<Trade> trades = tradeService.selectTradeList(new Trade());
            Trade trade = trades.get(0);
            String minLockPosition = trade.getMinLockPosition();    //锁仓最低值
            String lockMultipleNumber = trade.getLockMultipleNumber();  //锁仓的整数倍
            if(Double.parseDouble(number) < Double.parseDouble(minLockPosition)){
                return ResponseResult.responseResult(ResponseEnum.NUMBER_LOW_LOCK);
            }

            if(Double.parseDouble(number) % Double.parseDouble(lockMultipleNumber) != 0){
                return ResponseResult.responseResult(ResponseEnum.NUMBER_MULTIPLE_LOCK,lockMultipleNumber);
            }

            VipUser vipUser1 = vipUserService.selectVipUserById(vipUser.getId());
            String sslMoney = vipUser1.getSslMoney();
            if(Double.parseDouble(sslMoney) < Double.parseDouble(number)){
                //ssl币不足
                return ResponseResult.responseResult(ResponseEnum.VIP_USER_SSLINSUFFICIENT);
            }

            VipLock vipLock = new VipLock();
            vipLock.setVipId(vipUser.getId());
            vipLock.setLockStatus(LockStatus.LOCKING.getCode());
            vipLock.setLockTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD));
            vipLock.setLockNumber(number);
            if(type.equals(LockType.ONE.getCode())){
                //1个月
                String oneRate = trade.getOneRate();
                double mul = NumberUtil.mul(Double.parseDouble(number), (1 + Double.parseDouble(oneRate)));

                vipLock.setLockType(LockType.ONE.getCode());
                vipLock.setLockExpire(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,DateUtil.nextMonth()));//下个月
                vipLock.setLockProfit(String.valueOf(mul));
            }else if(type.equals(LockType.THREE.getCode())){
                //3个月
                String threeRate = trade.getThreeRate();
                double mul = NumberUtil.mul(Double.parseDouble(number), (1 + Double.parseDouble(threeRate)));
                vipLock.setLockType(LockType.THREE.getCode());
                vipLock.setLockExpire(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,DateUtil.offset(new Date(), DateField.MONTH,3)));//3个月
                vipLock.setLockProfit(String.valueOf(mul));

            }else if(type.equals(LockType.SIX.getCode())){
                //6个月
                String sixRate = trade.getSixRate();
                double mul = NumberUtil.mul(Double.parseDouble(number), (1 + Double.parseDouble(sixRate)));
                vipLock.setLockType(LockType.SIX.getCode());
                vipLock.setLockExpire(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,DateUtil.offset(new Date(), DateField.MONTH,6)));//6个月
                vipLock.setLockProfit(String.valueOf(mul));
            }else if(type.equals(LockType.TWELVE.getCode())){
                //12个月
                String twelveRate = trade.getTwelveRate();
                double mul = NumberUtil.mul(Double.parseDouble(number), (1 + Double.parseDouble(twelveRate)));
                vipLock.setLockType(LockType.SIX.getCode());
                vipLock.setLockExpire(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,DateUtil.offset(new Date(), DateField.MONTH,12)));//12个月
                vipLock.setLockProfit(String.valueOf(mul));
            }

            vipLockService.vipLock(vipUser,vipLock);
            return ResponseResult.success();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }

    }


    /**
     * 中断锁仓
     * @param token
     * @param id    订单id
     * @return
     */
    //更改订单状态,扣除手续费,更新用户信息
    @PostMapping("/interuptLock")
    public ResponseResult interuptLock(@RequestHeader("token") String token,
                               @RequestParam("id") String id){

        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        VipLock vipLock = vipLockService.selectVipLockById(Integer.parseInt(id));
        if(vipLock.getLockStatus().equals(LockStatus.FINISH)){
            //订单已经锁仓结束
            return ResponseResult.responseResult(ResponseEnum.ALREADY_FINISH_LOCK);
        }

        try{
            if(vipLockService.interuptLock(vipUser,vipLock) > 0){
                return ResponseResult.success();
            }
            return ResponseResult.error();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error();
        }

    }
}
