package com.yihe.applet.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhDicIntroductionApp;
import com.yihe.applet.api.service.YhDicIntroductionAppService;
import com.yihe.system.utils.Result;

/**
 * YhDicIntroductioncontroller
 * Created by yx on 2020/5/26.
 */
@Controller
@RequestMapping("/api/YhDicIntroduction")
@Api(value = "YhDicIntroduction控制器", description = "YhDicIntroduction管理")
public class YhDicIntroductionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhDicIntroductionController.class);
	@Autowired
	YhDicIntroductionAppService yhDicIntroductionService;
	
	 @RequestMapping(value = "/selectBimcalculate")
	   	@ResponseBody
	public Result selectBim(String name) {
		YhDicIntroductionApp bmi = yhDicIntroductionService.selectBmi("体重指数计算");
		return Result.ok(bmi);
		
	}
	 @RequestMapping(value = "/selectWHR")
	   	@ResponseBody
	public Result selectWHR(String name) {
		 YhDicIntroductionApp whr = yhDicIntroductionService.selectWHR("腰臀比计算");
		return Result.ok(whr);
		
	}
	 @RequestMapping(value = "/selectExercise")
	   	@ResponseBody
	public Result selectExercise(String name) {
		 YhDicIntroductionApp exercise = yhDicIntroductionService.selectExercise("运动能耗计算");
		return Result.ok(exercise);
		
	}
	 @RequestMapping(value = "/selecTnutrient")
	   	@ResponseBody
	public Result selecTnutrient(String name) {
		 YhDicIntroductionApp nutrient = yhDicIntroductionService.selecTnutrient("营养素计算");
		return Result.ok(nutrient);
		
	}
	 @RequestMapping(value = "/selectEverydayConsumption")
	   	@ResponseBody
	public Result selectEverydayConsumption(String name) {
		 YhDicIntroductionApp consumption = yhDicIntroductionService.selectEverydayConsumption("每天能耗需求");
		return Result.ok(consumption);
		
	}

}