package com.yihe.web.controller.system;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.service.YhMakeCardInFoService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * YhEventEnrollcontroller
 * Created by dong on 2020/3/24.
 */
@Controller
@RequestMapping("/yhmakecardinfo")
@Api(value = "YhMakeCardInFoController控制器", description = "YhMakeCardInFoController管理")
public class YhMakeCardInFoController {
	 private static final Logger LOGGER = LoggerFactory.getLogger(YhMakeCardInFoController.class);
	 @Autowired
	 YhMakeCardInFoService yhmakecardinfoservice;
	 
	 
	 	@ApiOperation(value = "查询制卡信息")
	    @RequestMapping(value = "/usermakecardlog", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> usermakecardlog(Integer page,Integer limit,String guishu,String dates) {
	 		int count=yhmakecardinfoservice.selectcount(guishu,dates);
	 		Map<String, Object> map=new HashMap<String, Object>();
	 		map.put("data", yhmakecardinfoservice.selectmakekardlog(page,limit,guishu,dates));
	 		map.put("count",count);
	 		map.put("msg","");
	 		map.put("code",20000);
		 return map;
	 }
	 	

	 	@ApiOperation(value = "制卡")
	    @RequestMapping(value = "/makecard", method = RequestMethod.POST)
		@ResponseBody
		public Result makecard(Integer count,String Org_code,String miaoshu,Integer doctor) {
	 	yhmakecardinfoservice.makecard(count, Org_code, miaoshu,doctor);
		 return Result.ok(20000);
	 }
	 
}
