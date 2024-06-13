/**
 * 
 */
package com.yihe.applet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhLifeHabitDictCategoryApp;
import com.yihe.applet.api.mapper.YhLifeHabitDictCategoryAppMapper;

import io.swagger.annotations.Api;

/**
 * @author dong
 *
 */
@Controller
@RequestMapping("/api/YhLifeHabitDictCategoryAppController")
@Api(value = "YhLifeHabitDictCategory控制器", description = "YhLifeHabitDictCategory管理")
public class YhLifeHabitDictCategoryAppController {
	 @Autowired
	 YhLifeHabitDictCategoryAppMapper yhLifeHabitDictCategoryAppMapper;
	 /**
	     * 查询习惯大类
	     * @param usercode
	     * @param id
	     * @param beizhu
	     * @return
	     */
		@RequestMapping(value = "/showheading", method = RequestMethod.POST)
		@ResponseBody // ajax注解
		public List<YhLifeHabitDictCategoryApp> showheading() {
			List<YhLifeHabitDictCategoryApp> list=yhLifeHabitDictCategoryAppMapper.showheading();
			return list;
		}
	 
}
