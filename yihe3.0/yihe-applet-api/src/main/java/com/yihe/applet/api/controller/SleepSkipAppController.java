package com.yihe.applet.api.controller;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhDicIntroductionApp;
import com.yihe.applet.api.domain.sleepSkipApp;
import com.yihe.applet.api.mapper.sleepSkipAppMapper;
import com.yihe.system.utils.Result;

@Controller
@RequestMapping("/api/SleepSkipApp")
public class SleepSkipAppController {
	@Autowired
	sleepSkipAppMapper sleepSkipAppMapper;
	
	 @RequestMapping(value = "/addSkip")
	  @ResponseBody
	public Result addSkip(String userCode) {
		 sleepSkipApp skip = new sleepSkipApp();
		 skip.setUserCode(userCode);
		 skip.setQtime(new Date());
		 int selective = sleepSkipAppMapper.insertSelective(skip);
		return Result.ok(selective);
		
	}
	 
	 @RequestMapping(value = "/selectSkip")
	   	@ResponseBody
	public Result selectSkip(String userCode) {
		  sleepSkipApp skipApp = sleepSkipAppMapper.selectByPrimaryKey(userCode);
		return Result.ok(skipApp);
		
	}

}
