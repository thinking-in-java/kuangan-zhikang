package com.yihe.applet.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhHealthLectureEnshrineApp;
import com.yihe.applet.api.service.YhHealthLectureEnshrineAppService;
import com.yihe.system.utils.Result;
/**
 * 大课堂视频收藏控制器
 * @author yx
 * 2020年10月9日15:14:06
 *
 */
@Controller
@RequestMapping("/api/YhHealthLectureEnshrineApp")
public class YhHealthLectureEnshrineAppController {
	@Autowired
	YhHealthLectureEnshrineAppService yhHealthLectureEnshrineAppService;
	
	/**
	 * 收藏or取消收藏
	 */
	@RequestMapping(value = "/collectOrNotByUsercode")
	@ResponseBody 
	public Result collectOrNotByUsercode(String userCode,Integer categoryid,Integer type) {
		int row = 0 ; 
		if(type == 1) {
			YhHealthLectureEnshrineApp enshrineApp = yhHealthLectureEnshrineAppService.selectWhetherEnshrineByParam(userCode, categoryid);
			if(enshrineApp==null) {
				YhHealthLectureEnshrineApp yhHealthLectureEnshrineApp = new YhHealthLectureEnshrineApp();
				yhHealthLectureEnshrineApp.setCategoryid(categoryid);
				yhHealthLectureEnshrineApp.setUserCode(userCode);
				Date date = new Date();
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    //获取当前时间String类型
				yhHealthLectureEnshrineApp.setNewTime(sdf.format(date));
				row = yhHealthLectureEnshrineAppService.insertSelective(yhHealthLectureEnshrineApp);
			}
			
		}
		if(type == 2) {
			row = yhHealthLectureEnshrineAppService.deleteWhetherEnshrineByParam(userCode, categoryid);
		}
		return Result.ok(row);
		
	}
	
	/**
	 * 查询用户是否收藏
	 */
	@RequestMapping(value = "/selectUserEnshrine")
	@ResponseBody 
	public Result selectUserEnshrine(String userCode,Integer categoryid) {
		YhHealthLectureEnshrineApp enshrineApp = yhHealthLectureEnshrineAppService.selectWhetherEnshrineByParam(userCode, categoryid);
		return Result.ok(enshrineApp);
		
	}
}
