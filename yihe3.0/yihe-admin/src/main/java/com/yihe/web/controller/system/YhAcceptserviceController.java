package com.yihe.web.controller.system;

import com.yihe.system.domain.YhJzServicePerson;
import com.yihe.system.domain.YhUserReginfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhServicePage;
import com.yihe.system.service.YhAcceptserviceService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhAcceptserviceController")
@Api(value = "YhAcceptserviceController控制器", description = "YhAcceptserviceController管理")
public class YhAcceptserviceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(YhAcceptserviceController.class);
	@Autowired
	YhAcceptserviceService yhacceptserviceservice;
	@ApiOperation(value = "查询此人服务记录总数")
    @RequestMapping(value = "/selectallservice", method = RequestMethod.POST)
	@ResponseBody
    public Result  selectservicecountbyusercode(YhServicePage page) {
    	return Result.ok(yhacceptserviceservice.selectservicecountbyusercode(page));
    }
	
	@ApiOperation(value = "查询此人(等待、正在)服务的分页条数")
    @RequestMapping(value = "/selectservicecountconditions", method = RequestMethod.POST)
	@ResponseBody
    public Result  selectservicecountconditions(YhServicePage page) {
    	return Result.ok(yhacceptserviceservice.selectservicecountconditions(page));
    }
	@ApiOperation(value = "查询此人(等待、正在)服务")
    @RequestMapping(value = "/selectservicebyusercode", method = RequestMethod.POST)
	@ResponseBody
    public Result  selectservicebyusercode(YhServicePage page) {
    	return Result.ok(yhacceptserviceservice.selectservicebyusercode(page));
    }
	@ApiOperation(value = "点击受理")
    @RequestMapping(value = "/acceptservice", method = RequestMethod.POST)
	@ResponseBody
    public Result  acceptservice(String[] serviceid) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
    	return Result.ok(yhacceptserviceservice.acceptservice(serviceid,reginfo.getUserCode()));
    }
	
	@ApiOperation(value = "查询该用户的配合程度 " )
	@RequestMapping(value = "/selectCooperate", method = RequestMethod.POST)
	@ResponseBody
	public Result  selectCooperate(String usercode) {
		//return Result.ok(yhacceptserviceservice.acceptservice(serviceid));
		
		return Result.ok(yhacceptserviceservice.selectCooperate(usercode));
	}

	@ApiOperation(value = "查询用户医生下的姓名并排序")
	@RequestMapping(value = "/selectusername", method = RequestMethod.POST)
	@ResponseBody
	public Result selectusername() {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		return Result.ok(yhacceptserviceservice.selectusername(yh.getUserCode()));
	}

	@ApiOperation(value = "插入任务服务")
	@RequestMapping(value = "/insertserviceplan", method = RequestMethod.POST)
	@ResponseBody
	public Result insertserviceplan(YhJzServicePerson yhJzServicePerson) {

		return Result.ok(yhacceptserviceservice.insertserviceplan(yhJzServicePerson));
	}

}
