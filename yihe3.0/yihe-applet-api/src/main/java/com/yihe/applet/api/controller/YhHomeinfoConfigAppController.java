package com.yihe.applet.api.controller;

import com.yihe.applet.api.service.YhHomeinfoConfigAppService;
import com.yihe.system.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/YhHomeinfoConfig")
public class YhHomeinfoConfigAppController {
	@Autowired
	private YhHomeinfoConfigAppService yhHomeinfoConfigService;
	
	@RequestMapping("/selAllData")
	public Result selAllData() {
		return Result.ok(yhHomeinfoConfigService.selAllData());
	}
}
