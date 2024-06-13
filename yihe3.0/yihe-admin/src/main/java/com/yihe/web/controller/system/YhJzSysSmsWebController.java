/**
 * 
 */
package com.yihe.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhJzSysSmsWebVojo;
import com.yihe.system.domain.YhWxInformationVojo;
import com.yihe.system.service.YhJzSysSmsWebService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.ApiOperation;

/**
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhJzSysSmsWebController")
public class YhJzSysSmsWebController {
	
	@Autowired
	YhJzSysSmsWebService YhJzSysSmsWebService;

	
	
	@ApiOperation(value = "查询微信消息通知")
	   @RequestMapping(value = "/selectbyWxINfo", method = RequestMethod.POST)
		@ResponseBody
	   public Result  selectbyWxINfo() { 
		YhWxInformationVojo smsvoji=YhJzSysSmsWebService.selectbyWxINfo();
	       return Result.ok(smsvoji);
	   }

	
	@ApiOperation(value = "添加与修改短信配置")
	   @RequestMapping(value = "/WxInfoAdd", method = RequestMethod.POST)
		@ResponseBody
	   public Result  insertSelectiveWxInfo(YhWxInformationVojo record) { 
		int smsvo=YhJzSysSmsWebService.insertSelectiveWxInfo(record);
	       return Result.ok(smsvo);
	   }
	
	
		@ApiOperation(value = "查询不等于空的短信配置")
	   @RequestMapping(value = "/selectSmsNull", method = RequestMethod.POST)
		@ResponseBody
	   public Result  selectSmsNull() { 
		YhJzSysSmsWebVojo smsvoji=YhJzSysSmsWebService.selectSmsNull();
	       return Result.ok(smsvoji);
	   }
	
		@ApiOperation(value = "添加与修改短信配置")
	   @RequestMapping(value = "/SmsAdd", method = RequestMethod.POST)
		@ResponseBody
	   public Result  SmsAdd(YhJzSysSmsWebVojo record) { 
		int smsvo=YhJzSysSmsWebService.insertSelective(record);
	       return Result.ok(smsvo);
	   }
		
	
}
