package com.yihe.applet.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserMakeCardApp;
import com.yihe.applet.api.mapper.YhUserMakeCardAppMapper;
import com.yihe.applet.api.service.YhOrgTreeAppService;
import com.yihe.applet.api.service.YhUserMakeCardAppService;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/api/YhUserMakeCard")
@Api(value = "YhUserMakeCard控制器", description = "YhUserMakeCard管理")
public class YhUserMakeCardController {
	
	@Autowired
	YhUserMakeCardAppService  yhUserMakeCardAppService;
	@Autowired
	YhOrgTreeAppService  yhOrgTreeAppService;
	 
	@RequestMapping(value = "/selectOrgIdParam")
	@ResponseBody
	public Map<String, Object> selectOrgIdParam(String userCode) {
		YhUserMakeCardApp selectOrgIdParam = yhUserMakeCardAppService.selectOrgIdParam(userCode);
		 Map<String, Object> map  = new HashMap<String, Object>();
		if(selectOrgIdParam!=null) {
			String fullOrgName = yhOrgTreeAppService.selectFullOrgName(selectOrgIdParam.getOrgId(),new String());
			map.put("fullOrgName", fullOrgName);
		}
		return map;
	} 
	
	
}
