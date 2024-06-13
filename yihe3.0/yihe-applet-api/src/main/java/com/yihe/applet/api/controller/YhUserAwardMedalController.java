package com.yihe.applet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserAwardMedal;
import com.yihe.applet.api.service.YhUserAwardMedalService;
import com.yihe.applet.api.util.common.util.Result;

@Controller
@RequestMapping("/api/YhUserAwardMedal")
public class YhUserAwardMedalController {
	@Autowired
	YhUserAwardMedalService yhUserAwardMedalService;
	
	@RequestMapping("/selectYhUserAwardMedal")
	@ResponseBody
	public Result selectYhUserAwardMedal(String userCode) {
		List<YhUserAwardMedal> list = yhUserAwardMedalService.selectYhUserAwardMedal(userCode);
		return Result.ok(list);
		
	}
}
