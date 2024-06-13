package com.yihe.applet.api.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhpsychologicalAssessmentApp;
import com.yihe.applet.api.service.YhpsychologicalAssessmentAppService;
import com.yihe.system.utils.Result;

/**
 * YhPsychologicalAssessmentcontroller
 * Created by dyb on 2020/2/21.
 */
@Controller
@RequestMapping("/api/YhPsychologicalAssessment")
@Api(value = "YhPsychologicalAssessment控制器", description = "YhPsychologicalAssessment管理")
public class YhPsychologicalAssessmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhPsychologicalAssessmentController.class);
	
    @Autowired
    YhpsychologicalAssessmentAppService yhPsychologicalAssessmentService;
	

	@RequestMapping(value = "/findInfoAll")
	@ResponseBody
	public Result findInfoAll() {
		List<YhpsychologicalAssessmentApp> selectAllHeartHealth = yhPsychologicalAssessmentService.selectAllHeartHealth();
		return Result.ok(selectAllHeartHealth);
	}

}