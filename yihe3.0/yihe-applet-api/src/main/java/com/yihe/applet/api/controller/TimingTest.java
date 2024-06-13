/**
 * 
 */
package com.yihe.applet.api.controller;

import java.text.ParseException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserLifeDiaryAppWithBLOBs;
import com.yihe.applet.api.mapper.YhUserLifeDiaryAppMapper;
import com.yihe.applet.api.service.impl.TimingAppServiceImpl;
import com.yihe.system.mapper.YhUserNotifyMapper;
import com.yihe.system.utils.Result;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/TimingTest")
public class TimingTest {

	@Autowired
	TimingAppServiceImpl timingAppServiceImpl;

	@Autowired
	private YhUserLifeDiaryAppMapper yhUserLifeDiaryAppMapper;
	
	
	/**
	 * 每月
	 * @return
	 * @throws ParseException
	 */
	@Scheduled(cron = "0 59 23 28-31 * ?")
	public Result addYhUserFoodWeekly() throws ParseException {
			timingAppServiceImpl.livereport(1);
		return Result.error();
		
	}
	
	/**
	 * 每季
	 * @return
	 * @throws ParseException
	 */
	@Scheduled(cron = "0 59 23 28-31 3,6,9,12 ?")
	public Result addYhUserFoodWeekly1() throws ParseException {
			timingAppServiceImpl.livereport(2);
		return Result.error();
	}
	
	/**
	 * 每年
	 * @return
	 * @throws ParseException
	 */
	@Scheduled(cron = "0 0 0 31 12 ?")
	public Result addYhUserFoodWeekly2() throws ParseException {
		timingAppServiceImpl.livereport(3);
		return Result.error();
		
	}
	
	
	/**
	 * 查询报告记录
	 * @return
	 */
	@RequestMapping(value = "/showreportAll")
	@ResponseBody
	public Result showreportAll(int id){
		YhUserLifeDiaryAppWithBLOBs lobs=yhUserLifeDiaryAppMapper.selectByPrimaryKey(id);
		return Result.ok(lobs);
	}
	
}
