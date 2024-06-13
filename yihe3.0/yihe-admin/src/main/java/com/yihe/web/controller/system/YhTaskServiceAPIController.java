package com.yihe.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhManageTask;
import com.yihe.system.service.YhTaskServiceAPIService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/yhtaskserviceapicontroller")
@Api(value = "YhTaskServiceAPIController控制器", description = "YhTaskServiceAPIController管理")
public class YhTaskServiceAPIController {
	@Autowired
	YhTaskServiceAPIService yhtaskservuceapiservice;
	@RequestMapping("/queryswitchbyid")
	@ResponseBody
	// typeid评估2/检测1 childname子节点名字
	public Result queryswitchbyid(Integer switchid,Integer typeid,String	childname){
			return yhtaskservuceapiservice.queryswitchbyid(switchid,typeid, childname);
	}
	
	@RequestMapping("/insertperson")
	@ResponseBody

	public Result insertperson(YhManageTask task){

		return yhtaskservuceapiservice.insertperson(task);
	}
	
	@RequestMapping("/selectbloodpressurecount")
	@ResponseBody

	public Result selectbloodpressurecount(String Usercode){

		return Result.ok(yhtaskservuceapiservice.selectbloodpressurecount(Usercode));
	}
	
	
	@RequestMapping("/selectbloodsuger")
	@ResponseBody

	public Result selectbloodsuger(String Usercode){

		return Result.ok(yhtaskservuceapiservice.selectbloodsuger(Usercode));
	}
	
	@RequestMapping("/selectbloodfat")
	@ResponseBody

	public Result selectbloodfat(String Usercode){

		return Result.ok(yhtaskservuceapiservice.selectbloodfat(Usercode));
	}
	
	@RequestMapping("/selectbmicount")
	@ResponseBody

	public Result selectbmicount(String Usercode){

		return Result.ok(yhtaskservuceapiservice.selectbmicount(Usercode));
	}
}
