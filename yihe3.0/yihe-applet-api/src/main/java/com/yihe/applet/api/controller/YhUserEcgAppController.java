package com.yihe.applet.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserEcgApp;
import com.yihe.applet.api.service.YhUserEcgAppService;
import com.yihe.system.utils.Result;

/**
 * 心电图
 * Created by dyb on 2019/12/25.
 */
@Controller
@RequestMapping("/api/YhUserEcg")
@Api(value = "YhUserEcg控制器", description = "YhUserEcg管理")
public class YhUserEcgAppController  {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserEcgAppController.class);
	
	
    @Autowired
    YhUserEcgAppService yhUserEcgService;
    
	/**
	 * 新增记录
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/addInfo")
	@ResponseBody
	public Result addInfo(YhUserEcgApp record) {
		System.out.println(record);
		record.setIsDel(2);

		record.setEntryTime(record.getEntryTime()+NowString());

		int insertSelective = yhUserEcgService.insertSelective(record);
		if (insertSelective > 0) {
			return Result.ok();
		}

		return Result.error();
	}
	 /**
		 * 获取当前系统时间String类型返回值
		 * 
		 * @return
		 */
		public String NowString() {
			SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");// 设置日期格式
			return df.format(new Date());// new Date()为获取当前系统时间
		}
    /**
	 * 查询已经录入的日期集合selectByPrimaryKeyFindEntryTime
	 */
	
	@RequestMapping(value = "/selectByPrimaryKeyFindEntryTime")
	@ResponseBody
	public Result selectByPrimaryKeyFindEntryTime(Integer usercode) {
		  if(null==usercode){
			  Result.error();
		  }

		  List<YhUserEcgApp> selectByPrimaryKeyFindEntryTime = yhUserEcgService.selectByPrimaryKeyFindEntryTime(usercode);
		 

		return Result.ok(selectByPrimaryKeyFindEntryTime);
	}
	
	/**
	 * 查询当前日记录
	 */
	@RequestMapping(value = "/selectByPrimaryKeyFindEntryTimeAnduserid")
	@ResponseBody
	public Result selectByPrimaryKeyFindEntryTimeAnduserid(Integer usercode,String time) {
		  if(null==usercode||null==time){
			  Result.error();
		  }

	 List<YhUserEcgApp> selectByPrimaryKeyFindEntryTimeAnduserid = yhUserEcgService.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
		 

		return Result.ok(selectByPrimaryKeyFindEntryTimeAnduserid);
	}
	
    /**
	 * 根据eid查询最新7条体脂记录
	 * @param eid 
	 * @return
	 */
	@RequestMapping(value = "/findInfo")
	@ResponseBody
	public Result findInfo(String uEcgId) {
		System.out.println(uEcgId);
		
		YhUserEcgApp selectByPrimaryKeyOrderByTimeLimit1 = yhUserEcgService.selectByPrimaryKeyOrderByTimeLimit1(uEcgId);
		if (null != selectByPrimaryKeyOrderByTimeLimit1) {
			return Result.ok(selectByPrimaryKeyOrderByTimeLimit1);
		}

		return Result.error();
	}
}