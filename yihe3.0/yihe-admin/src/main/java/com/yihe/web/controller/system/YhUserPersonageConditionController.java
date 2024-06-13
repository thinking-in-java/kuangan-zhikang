package com.yihe.web.controller.system;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserPersonageCondition;
import com.yihe.system.domain.YhUserPersonageSymptom;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.service.YhUserPersonageConditionService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhUserPersonageCondition")
public class YhUserPersonageConditionController {
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@Autowired
	YhUserPersonageConditionService yhUserPersonageConditionService;	
	
	@ApiOperation(value = "查询个人状况")
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public YhUserPersonageCondition selectYhUserPersonageCondition(String userCode) {
		return yhUserPersonageConditionService.selectByPrimaryKey(userCode);
		
	}
	@ApiOperation(value = "修改个人状况")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public YhUserPersonageCondition updateYhUserPersonageCondition(YhUserPersonageCondition yhUserPersonageCondition) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取String类型的时间
		String createdate = sdf.format(new Date());
		
		if(yhUserPersonageCondition.getId()==null ||"".equals(yhUserPersonageCondition.getId())) {
			yhUserPersonageCondition.setAddtime(createdate);
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonageCondition.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			yhUserPersonageConditionService.insertSelective(yhUserPersonageCondition);
		}else {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonageCondition.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			yhUserPersonageCondition.setUpdatetime(createdate);
			yhUserPersonageConditionService.updateByPrimaryKey(yhUserPersonageCondition);
		}
		String userCode = yhUserPersonageCondition.getUserCode();
		YhUserPersonageCondition selectByPrimaryKey = yhUserPersonageConditionService.selectByPrimaryKey(userCode);
		return selectByPrimaryKey;
		
	}
}
