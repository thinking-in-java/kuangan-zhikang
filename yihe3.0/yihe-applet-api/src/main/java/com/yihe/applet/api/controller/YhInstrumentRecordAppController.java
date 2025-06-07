package com.yihe.applet.api.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhInstrumentRecordApp;
import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.domain.YhWxUser;
import com.yihe.applet.api.service.YhInstrumentRecordAppService;
import com.yihe.applet.api.service.YhWxUserAppService;
import com.yihe.system.domain.YhUserReginfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author yx
 *
 */

@Controller
@RequestMapping("/api/YhInstrumentRecordApp")
@Api(value = "YhInstrumentRecordApp控制器", description = "YhInstrumentRecordApp管理")
public class YhInstrumentRecordAppController {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserReginfoController.class);
	
	@Autowired
	YhInstrumentRecordAppService  yhInstrumentRecordAppService;
	@Autowired
	YhWxUserAppService yhWxUserAppService;
	
	@ApiOperation(value = "查询用户使用次数")
	@RequestMapping(value = "/selectCount", method = RequestMethod.POST)
	@ResponseBody
	public int selectCount( String type) {
		System.out.println("--"+type);
		int countData = yhInstrumentRecordAppService.countData(type);
		return countData;
	}
	
	@ApiOperation(value = "添加使用次数记录")
	@RequestMapping(value = "/addTime", method = RequestMethod.POST)
	@ResponseBody
	public int addTime(YhInstrumentRecordApp yhInstrumentRecordApp) {
		System.out.println("--"+yhInstrumentRecordApp.getUserCode());
		 int insertSelective = yhInstrumentRecordAppService.insertSelective(yhInstrumentRecordApp);
		return insertSelective;
	}
	
	@ApiOperation(value = "随机查询4个用户的头像")
	@RequestMapping(value = "/selectRandomheandImg", method = RequestMethod.POST)
	@ResponseBody
	public List<String> selectRandomheandImg( String type) {
		List<String> list = yhWxUserAppService.selectRandomheandImg(type);
		return list;
	}
	
}
