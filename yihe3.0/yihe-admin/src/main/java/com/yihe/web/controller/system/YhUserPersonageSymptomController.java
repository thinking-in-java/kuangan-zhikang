package com.yihe.web.controller.system;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserPersonageSymptom;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.service.YhUserPersonageSymptomService;
import com.yihe.system.service.YhUserReginfoService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhUserPersonageSymptom")
public class YhUserPersonageSymptomController {
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@Autowired 
	YhUserPersonageSymptomService yhUserPersonageSymptomService;
	@Autowired
	YhUserReginfoService yhUserReginfoService;
	@ApiOperation(value = "查询症状信息")
	@RequestMapping(value = "/selectSymptom", method = RequestMethod.GET)
	@ResponseBody
	public YhUserPersonageSymptom selectYhUserPersonageSymptom(String userCode) {
		return yhUserPersonageSymptomService.selectByPrimaryKey(userCode);
		
	}
	
	@ApiOperation(value = "修改症状信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public YhUserPersonageSymptom updateYmptom(YhUserPersonageSymptom yhUserPersonageSymptom,String[] like,String[] array) {
		StringBuffer str2 = new StringBuffer();
		if(like.length>0) {
			//创建一个StringBuffer的对象
		for (int i = 0; i < like.length; i++) {
			str2.append(like[i]+",") ;
		}
		String str=str2.replace(str2.lastIndexOf(","), str2.length(), "").toString();
		yhUserPersonageSymptom.setPersonageName(str);
	}else {
		yhUserPersonageSymptom.setPersonageName("");
	}
		if(array.length>0) {
			//创建一个StringBuffer的对象
		StringBuffer str3 = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			str3.append(array[i]+",") ;
		}
		String str=str3.replace(str3.lastIndexOf(","), str3.length(), "").toString();
		yhUserPersonageSymptom.setStandby(str);
		}
		
		/*if (like.length > 0) {
			 String str = str2.toString().substring(0, str2.length() - 1);
			 yhUserPersonageSymptom.setPersonageName(str);
		}*/
		//YhUserPersonageSymptom list = yhUserPersonageSymptomService.selectByPrimaryKey(userCode);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取String类型的时间
		String createdate = sdf.format(new Date());
		
		if(yhUserPersonageSymptom.getId()==null ||"".equals(yhUserPersonageSymptom.getId())) {
			yhUserPersonageSymptom.setAddtime(createdate);
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonageSymptom.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			 yhUserPersonageSymptomService.insertSelective(yhUserPersonageSymptom);
		}else {
			yhUserPersonageSymptom.setUpdatetime(createdate);
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonageSymptom.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			 yhUserPersonageSymptomService.updateByPrimaryKeySelective(yhUserPersonageSymptom);
		}
		String userCode = yhUserPersonageSymptom.getUserCode();
		YhUserPersonageSymptom selectByPrimaryKey = yhUserPersonageSymptomService.selectByPrimaryKey(userCode);
		return selectByPrimaryKey;
		
		
	}
}
