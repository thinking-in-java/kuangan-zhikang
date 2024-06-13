package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhUserEcgApp;
import com.yihe.applet.api.service.YhUserEcgAppService;
import com.yihe.system.domain.YhUserCellDetectionWeb;
import com.yihe.system.mapper.YhUserCellDetectionWebMapper;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 心电图
 * Created by dyb on 2019/12/25.
 */
@Controller
@RequestMapping("/api/yhUserCelldetection")
@Api(value = "yhUserCelldetection", description = "YhUserEcg管理")
public class YhUserCelldetectionAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserCelldetectionAppController.class);
	
	
    @Autowired
	YhUserCellDetectionWebMapper YhUserCellDetectionWebMapper;

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
	public Result selectByPrimaryKeyFindEntryTime(String usercode) {
		  if(null==usercode){
			  Result.error();
		  }

		  List<YhUserCellDetectionWeb> selectByPrimaryKeyFindEntryTime = YhUserCellDetectionWebMapper.selectByPrimaryKeyFindEntryTime(usercode);
		 

		return Result.ok(selectByPrimaryKeyFindEntryTime);
	}
	/**
	 * 查询当前日记录
	 */
	@RequestMapping(value = "/selectByPrimaryKeyFindEntryTimeAnduserid")
	@ResponseBody
	public Result selectByPrimaryKeyFindEntryTimeAnduserid(String usercode,String time) {
		  if(null==usercode||null==time){
			  Result.error();
		  }

	 List<YhUserCellDetectionWeb> selectByPrimaryKeyFindEntryTimeAnduserid = YhUserCellDetectionWebMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
		 

		return Result.ok(selectByPrimaryKeyFindEntryTimeAnduserid);
	}

}