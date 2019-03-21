package com.ruoyi.web.controller.ysxback.goods;

import com.ruoyi.yishengxin.domain.goods.GoodsStatistics;
import com.ruoyi.yishengxin.service.IGoodsOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 售量统计 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-04-20
 */
@Controller
@RequestMapping("/yishengxin/goodsStatistics")
public class GoodsStatisticsController extends BaseController
{
    private String prefix = "yishengxin/goodsStatistics";

	@Autowired
	private IGoodsOrderService goodsOrderService;
	
	@RequiresPermissions("yishengxin:goodsStatistics:view")
	@GetMapping()
	public String goodsStatistics(){
	    return prefix + "/goodsStatistics";
	}
	
	/**
	 * 查询售量统计列表
	 */
	@RequiresPermissions("yishengxin:goodsStatistics:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsStatistics goodsStatistics){
		startPage();
		String goodsName = goodsStatistics.getGoodsName();
		Date startTime = goodsStatistics.getStartTime();
		Date stopTime = goodsStatistics.getStopTime();
		int i = goodsOrderService.selectSoleNumber(goodsName, startTime, stopTime);
		goodsStatistics.setSales(i+"");
		List<GoodsStatistics> list = new ArrayList<>();
		list.add(goodsStatistics);
		return getDataTable(list);
	}
	
	

	
}
