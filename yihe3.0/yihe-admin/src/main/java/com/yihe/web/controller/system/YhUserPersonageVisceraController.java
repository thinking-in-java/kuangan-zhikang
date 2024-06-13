package com.yihe.web.controller.system;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserPersonageViscera;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.service.YhUserPersonageVisceraService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhUserPersonageViscera")
public class YhUserPersonageVisceraController {

	@Autowired
	YhUserPersonageVisceraService yhUserPersonageVisceraService;
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@ApiOperation(value = "查询脏器功能")
	@RequestMapping(value = "/selectViscera", method = RequestMethod.GET)
	@ResponseBody
	public YhUserPersonageViscera selectYhUserPersonageViscera(String userCode) {
		return yhUserPersonageVisceraService.selectByPrimaryKey(userCode);

	}
	
	@ApiOperation(value = "修改脏器功能")
	@RequestMapping(value = "/updateViscera", method = RequestMethod.POST)
	@ResponseBody
	public YhUserPersonageViscera updateYhUserPersonageViscera(YhUserPersonageViscera yhUserPersonageViscera) {
		if(yhUserPersonageViscera.getId()==null ||"".equals(yhUserPersonageViscera.getId())) {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonageViscera.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			yhUserPersonageVisceraService.insertSelective(yhUserPersonageViscera);
		}else {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonageViscera.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			yhUserPersonageVisceraService.updateByPrimaryKey(yhUserPersonageViscera);
		}
		
		String userCode = yhUserPersonageViscera.getUserCode();
		YhUserPersonageViscera personageViscera = yhUserPersonageVisceraService.selectByPrimaryKey(userCode);
		return personageViscera;
		
		
	}
}
