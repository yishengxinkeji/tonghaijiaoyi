package com.ruoyi.web.controller.ysxfront.goods;

import java.util.List;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;

import com.ruoyi.yishengxin.domain.goods.GoodsRefundreason;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import com.ruoyi.yishengxin.service.IGoodsRefundreasonService;


/**
 * 退款原因 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-19
 */
@Controller
@RequestMapping("/front/goodsRefundreason")
public class GoodsRefundreasonController
{

	
	@Autowired
	private IGoodsRefundreasonService goodsRefundreasonService;

	
	/**
	 * 查询退款原因列表
	 */

	@PostMapping("/list")
	@ResponseBody
	public  ResponseResult list(@RequestHeader("token")String token,GoodsRefundreason goodsRefundreason){

        List<GoodsRefundreason> list = goodsRefundreasonService.selectGoodsRefundreasonList(goodsRefundreason);

		return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);

	}

	
}
