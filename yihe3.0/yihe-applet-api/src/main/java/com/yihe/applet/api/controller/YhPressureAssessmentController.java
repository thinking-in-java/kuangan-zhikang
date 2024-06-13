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

import com.yihe.applet.api.domain.YhPressureAssessmentApp;
import com.yihe.applet.api.service.YhPressureAssessmentAppService;
import com.yihe.system.utils.Result;

/**
 * YhPressureAssessmentcontroller
 * Created by dyb on 2020/2/21.
 */
@Controller
@RequestMapping("/api/YhPressureAssessment")
@Api(value = "YhPressureAssessment控制器", description = "YhPressureAssessment管理")
public class YhPressureAssessmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhPressureAssessmentController.class);
	
	@Autowired
	 YhPressureAssessmentAppService YhPressureAssessmentService;
	
	@RequestMapping(value = "/findInfoAll")
	@ResponseBody
	public Result findInfoAll() {
		List<YhPressureAssessmentApp> selectAllpstre = YhPressureAssessmentService.selectAllpstre();
		return Result.ok(selectAllpstre);
	}

}