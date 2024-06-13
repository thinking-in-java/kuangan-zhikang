package com.yihe.applet.api.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhLifeHabitRetroactiveApp;
import com.yihe.applet.api.mapper.YhLifeHabitRetroactiveAppMapper;
/**
 * 
 * @author 阳雄
 * @Date 2021年1月29日08:35:59
 *  补签卡控制器
 *
 */
import com.yihe.system.utils.Result;
@Controller
@RequestMapping("/api/YhLifeHabitRetroactiveApp")
public class YhLifeHabitRetroactiveAppController {
	@Autowired
	YhLifeHabitRetroactiveAppMapper yhLifeHabitRetroactiveAppMapper;
	
	@RequestMapping(value = "/selectYhLifeHabitCount")
	@ResponseBody
	public Result selectCount(String userCode) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<YhLifeHabitRetroactiveApp> list = yhLifeHabitRetroactiveAppMapper.selectAll(userCode);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setCreatedate(sdf.format(list.get(i).getPastTime()));
		}
		return Result.ok(list);
		
	}
}
