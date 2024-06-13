package com.yihe.web.controller.system;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserPersonageExisting;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.service.YhUserPersonageExistingService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhUserPersonageExisting")
public class YhUserPersonageExistingController {
	@Autowired
	YhUserPersonageExistingService yhUserPersonageExistingService;
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@ApiOperation(value = "查询健康问题")
	@RequestMapping(value = "/selectExisting", method = RequestMethod.GET)
	@ResponseBody
	public YhUserPersonageExisting selectYhUserPersonageExisting(String userCode) {
		return yhUserPersonageExistingService.selectByPrimaryKey(userCode);
		
	}
	@ApiOperation(value = "修改健康问题")
	@RequestMapping(value = "/updateExisting", method = RequestMethod.POST)
	@ResponseBody
	public YhUserPersonageExisting updateYhUserPersonageExisting(YhUserPersonageExisting yhUserPersonageExisting,String[] bloodvessel,
			String[] kidney,String[] heart,String[] blooddisease,String[] ocular) {
		//创建一个StringBuffer的对象
		StringBuffer str2 = new StringBuffer();
		for (int i = 0; i < bloodvessel.length; i++) {
			str2.append(bloodvessel[i]) ;
			if(i != bloodvessel.length - 1) {
				str2.append(",");
			}
			
		}
		
		//创建一个StringBuffer的对象
				StringBuffer str3 = new StringBuffer();
				for (int i = 0; i < kidney.length; i++) {
					str3.append(kidney[i]) ;
					if(i != kidney.length - 1) {
						str3.append(",");
					}
					
				}
				
				//创建一个StringBuffer的对象
				StringBuffer str4 = new StringBuffer();
				for (int i = 0; i < heart.length; i++) {
					str4.append(heart[i]) ;
					if(i != heart.length - 1) {
						str4.append(",");
					}
					
				}
				
				//创建一个StringBuffer的对象
				StringBuffer str5 = new StringBuffer();
				for (int i = 0; i < blooddisease.length; i++) {
					str5.append(blooddisease[i]) ;
					if(i != blooddisease.length - 1) {
						str5.append(",");
					}
					
				}
				
				//创建一个StringBuffer的对象
				StringBuffer str6 = new StringBuffer();
				for (int i = 0; i < ocular.length; i++) {
					str6.append(ocular[i]) ;
					if(i != ocular.length - 1) {
						str6.append(",");
					}
					
				}
	
				
			yhUserPersonageExisting.setBloodvessel(str2.toString());
			yhUserPersonageExisting.setKidney(str3.toString());
			yhUserPersonageExisting.setHeart(str4.toString());
			yhUserPersonageExisting.setBlooddisease(str5.toString());
			yhUserPersonageExisting.setOcular(str6.toString());
		
			if(yhUserPersonageExisting.getId()==null ||"".equals(yhUserPersonageExisting.getId())) {
				YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
				yhUserEnrolinfo.setEnergyUptime(new Date());
				yhUserEnrolinfo.setUserCode(yhUserPersonageExisting.getUserCode());
				yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
				yhUserPersonageExistingService.insertSelective(yhUserPersonageExisting);
			}else {
				YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
				yhUserEnrolinfo.setEnergyUptime(new Date());
				yhUserEnrolinfo.setUserCode(yhUserPersonageExisting.getUserCode());
				yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
				yhUserPersonageExistingService.updateByPrimaryKey(yhUserPersonageExisting);
			}
			
			String userCode = yhUserPersonageExisting.getUserCode();
			YhUserPersonageExisting existing = yhUserPersonageExistingService.selectByPrimaryKey(userCode);
		 		return existing;
		
	}
}
