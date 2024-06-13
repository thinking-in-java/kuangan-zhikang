package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.IntegralShoppingGroupingApp;
import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.service.IntegralShoppingGroupingAppService;
import com.yihe.applet.api.service.YhUserReginfoAppService;
import com.yihe.system.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/IntegralShoppingGroupingApp")
public class IntegralShoppingGroupingAppController {
	@Autowired
	IntegralShoppingGroupingAppService integralShoppingGroupingAppService;
	@Autowired
	YhUserReginfoAppService yhUserReginfoAppService;
	@RequestMapping(value = "/selectAllGroupingName")
	  @ResponseBody
	public Result selectAllGroupingName(String userCode) {
		YhUserReginfoApp reginfo = yhUserReginfoAppService.findYhUserReginfo(userCode);
		List<IntegralShoppingGroupingApp> list = integralShoppingGroupingAppService.selectAllGrouping(reginfo.getOrgCode().substring(0,5));
		IntegralShoppingGroupingApp app=new IntegralShoppingGroupingApp(0,"全部",reginfo.getOrgCode().substring(0,5));
		list.add(0,app);
		return Result.ok(list);
		
	}
}
