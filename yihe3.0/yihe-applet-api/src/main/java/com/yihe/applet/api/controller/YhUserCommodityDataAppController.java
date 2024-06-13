package com.yihe.applet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserCommodityDataApp;
import com.yihe.applet.api.service.YhUserCommodityDataAppService;
import com.yihe.system.utils.Result;
/**
 * 商品信息控制器
 * @author 阳雄
 *@date 2021年1月12日16:11:53
 */
@Controller
@RequestMapping("/api/YhUserCommodityDataApp")
public class YhUserCommodityDataAppController {
	@Autowired
	YhUserCommodityDataAppService yhUserCommodityDataAppService;
	
	@RequestMapping(value = "/selectCommodity")
	@ResponseBody
	public Result selectCommodityDataByParam (Integer groupingId,Integer variable,String userCode,Integer page,Integer pageSize) {
		List<YhUserCommodityDataApp> list = yhUserCommodityDataAppService.selectCommodityDataByParam(groupingId,variable,userCode,(page-1)*pageSize,pageSize);
		return Result.ok(list);
		
	}
	
	@RequestMapping(value = "/selectCommodityDetails")
	@ResponseBody
	public Result selectCommodityDetails (Integer merchandiseId) {
		 YhUserCommodityDataApp commodityDataApp = yhUserCommodityDataAppService.selectByPrimaryKey(merchandiseId);
		return Result.ok(commodityDataApp);
		
	}
}
