/**
 * 
 */
package com.yihe.applet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserRenalfunctionApp;
import com.yihe.applet.api.domain.YhUserServicestatisticApp;
import com.yihe.applet.api.mapper.YhUserServicestatisticAppMapper;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;

/**
 * 用户点击统计
 * @author dong
 *
 */
@Controller
@RequestMapping("/api/YhUserServicestatisticAppController")
@Api(value = "YhUserServicestatisticAppController控制器", description = "YhUserServicestatisticAppController管理")
public class YhUserServicestatisticAppController {

	@Autowired
	YhUserServicestatisticAppMapper yhUserServicestatisticAppMapper;
	
	
	/**
	 * 增加用户统计数据
	 * @param genre
	 * @return
	 */
	@RequestMapping(value = "/selectByPrimaryKeyGenre")
	@ResponseBody
	public Result selectByPrimaryKeyGenre(String genre) {
		YhUserServicestatisticApp cestatis = yhUserServicestatisticAppMapper.selectByPrimaryKeyGenre(genre);
		YhUserServicestatisticApp vojo=new YhUserServicestatisticApp();
		vojo.setRecord(cestatis.getRecord()+1);
		vojo.setId(cestatis.getId());
		yhUserServicestatisticAppMapper.updateByPrimaryKeySelective(vojo);
		return Result.ok();
	}
	
}
