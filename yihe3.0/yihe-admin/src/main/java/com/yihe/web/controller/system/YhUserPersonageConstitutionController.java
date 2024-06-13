package com.yihe.web.controller.system;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserPersonageConstitution;
import com.yihe.system.domain.YhUserPersonageExamination;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.service.YhUserPersonageConstitutionService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhUserPersonageConstitution")
public class YhUserPersonageConstitutionController {
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@Autowired 
	YhUserPersonageConstitutionService  yhUserPersonageConstitutionService;
	
	@ApiOperation(value = "查询体质辨识")
	@RequestMapping(value = "/selectConstitution", method = RequestMethod.GET)
	@ResponseBody
	public YhUserPersonageConstitution selectYhUserPersonageConstitution(String userCode) {
		return yhUserPersonageConstitutionService.selectByPrimaryKey(userCode);

	}
	
	@ApiOperation(value = "修改体质辨识")
	@RequestMapping(value = "/updateConstitution", method = RequestMethod.POST)
	@ResponseBody
	public YhUserPersonageConstitution updateYhUserPersonageConstitution(YhUserPersonageConstitution yhUserPersonageConstitution) {
		
		if(yhUserPersonageConstitution.getId()==null ||"".equals(yhUserPersonageConstitution.getId())) {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonageConstitution.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			yhUserPersonageConstitutionService.insertSelective(yhUserPersonageConstitution);
		}else {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonageConstitution.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			yhUserPersonageConstitutionService.updateByPrimaryKey(yhUserPersonageConstitution);
		}
		
		
		String userCode = yhUserPersonageConstitution.getUserCode();
		 YhUserPersonageConstitution constitution = yhUserPersonageConstitutionService.selectByPrimaryKey(userCode);
		return constitution;
		
		
	}

}
