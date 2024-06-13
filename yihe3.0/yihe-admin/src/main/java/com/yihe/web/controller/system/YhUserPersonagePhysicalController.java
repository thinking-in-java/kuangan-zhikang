package com.yihe.web.controller.system;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserPersonagePhysical;
import com.yihe.system.domain.YhUserPersonageViscera;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.service.YhUserPersonagePhysicalService;
import com.yihe.system.service.YhUserPersonageVisceraService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhUserPersonagePhysical")
public class YhUserPersonagePhysicalController {

	@Autowired
	YhUserPersonagePhysicalService yhUserPersonagePhysicalService;
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@ApiOperation(value = "查询查体")
	@RequestMapping(value = "/selectPhysical", method = RequestMethod.GET)
	@ResponseBody
	public YhUserPersonagePhysical selectYhUserPersonagePhysical(String userCode) {
		return yhUserPersonagePhysicalService.selectByPrimaryKey(userCode);

	}
	
	@ApiOperation(value = "修改查体")
	@RequestMapping(value = "/updatePhysical", method = RequestMethod.POST)
	@ResponseBody
	public YhUserPersonagePhysical updateYhUserPersonagePhysical(YhUserPersonagePhysical yhUserPersonagePhysical,String[] anas,String[] breast) {
		//创建一个StringBuffer的对象
		StringBuffer str2 = new StringBuffer();
		for (int i = 0; i < anas.length; i++) {
			str2.append(anas[i]+",") ;
		}
		if(anas.length>0) {
			 String str = str2.toString().substring(0, str2.length() - 1);
			yhUserPersonagePhysical.setAnas(str);
		}
		
			//创建一个StringBuffer的对象
				StringBuffer str3 = new StringBuffer();
				for (int i = 0; i < breast.length; i++) {
					str3.append(breast[i]+",") ;
				}
				if(breast.length>0) {
					 String strBreast = str3.toString().substring(0, str3.length() - 1);
					yhUserPersonagePhysical.setBreast(strBreast);
				}
		
		if(yhUserPersonagePhysical.getId()==null ||"".equals(yhUserPersonagePhysical.getId())) {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonagePhysical.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			yhUserPersonagePhysicalService.insertSelective(yhUserPersonagePhysical);
		}else {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonagePhysical.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			yhUserPersonagePhysicalService.updateByPrimaryKey(yhUserPersonagePhysical);
		}
		String userCode = yhUserPersonagePhysical.getUserCode();
		YhUserPersonagePhysical physical = yhUserPersonagePhysicalService.selectByPrimaryKey(userCode);
		return physical;	
	}
	
	
}
