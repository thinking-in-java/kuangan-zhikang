package com.yihe.web.controller.system;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserPersonageExamination;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.service.YhUserPersonageExaminationService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhUserPersonageExamination")
public class YhUserPersonageExaminationController {
	@Autowired 
	YhUserPersonageExaminationService yhUserPersonageExaminationService;
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@ApiOperation(value = "查询辅助检查")
	@RequestMapping(value = "/selectExamination", method = RequestMethod.GET)
	@ResponseBody
	public YhUserPersonageExamination selectYhUserPersonageExamination(String userCode) {
		return yhUserPersonageExaminationService.selectByPrimaryKey(userCode);

	}
	
	@ApiOperation(value = "修辅助检查")
	@RequestMapping(value = "/updateExamination", method = RequestMethod.POST)
	@ResponseBody
	public YhUserPersonageExamination updateYhUserPersonageExamination(YhUserPersonageExamination yhUserPersonageExamination) {
		
		if(yhUserPersonageExamination.getId()==null ||"".equals(yhUserPersonageExamination.getId())) {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonageExamination.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			yhUserPersonageExaminationService.insertSelective(yhUserPersonageExamination);
		}else {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonageExamination.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			yhUserPersonageExaminationService.updateByPrimaryKey(yhUserPersonageExamination);
		}
		
		String userCode = yhUserPersonageExamination.getUserCode();
		 YhUserPersonageExamination personageExamination = yhUserPersonageExaminationService.selectByPrimaryKey(userCode);
		
		return personageExamination;
		
		
	}
}
