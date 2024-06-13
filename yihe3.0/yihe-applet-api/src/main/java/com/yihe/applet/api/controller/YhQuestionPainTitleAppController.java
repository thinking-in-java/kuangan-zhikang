package com.yihe.applet.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yihe.applet.api.domain.YhQuestionPainTitleApp;
import com.yihe.applet.api.service.YhQuestionPainTitleAppService;
import com.yihe.system.utils.Result;
/**
 * 
 * @author yx 
 *
 */
@RestController
@RequestMapping("/api/YhQuestionPainTitleApp")
public class YhQuestionPainTitleAppController {
	private static final Logger LOGGER = LoggerFactory.getLogger(YhQuestionPainTitleAppController.class);
	@Autowired
	YhQuestionPainTitleAppService  yhQuestionPainTitleAppService;
	
	@RequestMapping(value = "/selectAllYhQuestionPainTitle")
	@ResponseBody
	public Result selectAll() {
	List<YhQuestionPainTitleApp> selectAll = yhQuestionPainTitleAppService.selectAll();
	return Result.ok(selectAll);
		
	}
}
