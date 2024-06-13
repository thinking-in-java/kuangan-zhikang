package com.yihe.applet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhWxInformationApp;
import com.yihe.applet.api.service.YhWxInformationAppService;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/api/YhWxInformationApp")
@Api(value = "YhWxInformationApp控制器", description = "微信消息模板管理")
public class YhWxInformationAppController {
	 @Autowired
	YhWxInformationAppService yhWxInformationAppService;
	 
	 @RequestMapping(value = "/selectAll")
		@ResponseBody
	 public List<YhWxInformationApp> selectAll(){
		 List<YhWxInformationApp> selectAll = yhWxInformationAppService.selectAll();
		return selectAll;
		 
	 }
}
