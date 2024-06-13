package com.yihe.web.controller.system;

import com.yihe.system.domain.YhJzService;
import com.yihe.system.domain.YhJzServiceAssessmentSwitch;
import com.yihe.system.domain.YhJzServiceBodydataSwitch;
import com.yihe.system.service.YhDivisionsettingService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/YhDivisionsetting")
@Api(value = "健管师服务管理", description = "健管师服务管理")
public class YhDivisionsettingController {
		@Autowired
		YhDivisionsettingService yhdivisionsettingservice;
		@ApiOperation(value = "查询所有服务")
	    @RequestMapping(value = "/selectallservice", method = RequestMethod.POST)
		@ResponseBody
	    public Result  selectallservice() {
	    	
	    	return Result.ok(yhdivisionsettingservice.selectallservice());
	    }
		
		@ApiOperation(value = "修改服务内容")
	    @RequestMapping(value = "/updateallservice", method = RequestMethod.POST)
		@ResponseBody
	    public Result  updateallservice(@RequestBody List<YhJzService> YhJzService) {
	    	return Result.ok(yhdivisionsettingservice.updateservie(YhJzService));
	    }
		
		@ApiOperation(value = "查询身体 数据开关")
	    @RequestMapping(value = "/selectbodyswitch", method = RequestMethod.POST)
		@ResponseBody
	    public Result  selectbodyswitch() {
	    
	    	return Result.ok(yhdivisionsettingservice.selectbodyswitch());
	    }
		
		@ApiOperation(value = "修改身体 数据开关")
	    @RequestMapping(value = "/updatebodyswitch", method = RequestMethod.POST)
		@ResponseBody
	    public Result  updatebodyswitch(@RequestBody List<YhJzServiceBodydataSwitch> YhJzServiceBodydataSwitch) {
	    
	    	return Result.ok(yhdivisionsettingservice.updatebodyswitch(YhJzServiceBodydataSwitch));
	    }
		
		@ApiOperation(value = "查询评估开关")
	    @RequestMapping(value = "/selectassessmentswitch", method = RequestMethod.POST)
		@ResponseBody
	    public Result  selectassessmentswitch() {	    
	    	return Result.ok(yhdivisionsettingservice.selectassessmentswitch());
	    }



		
		@ApiOperation(value = "修改评估 数据开关")
	    @RequestMapping(value = "/updateassessmentswitch", method = RequestMethod.POST)
		@ResponseBody
	    public Result  updateassessmentswitch(@RequestBody List<YhJzServiceAssessmentSwitch> YhJzServiceAssessmentSwitch) {
	    
	    	return Result.ok(yhdivisionsettingservice.updateassessmentswitch(YhJzServiceAssessmentSwitch));
	    }
}
