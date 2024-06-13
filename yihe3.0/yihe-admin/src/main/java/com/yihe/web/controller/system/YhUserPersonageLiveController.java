package com.yihe.web.controller.system;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserPersonageLive;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.service.YhUserPersonageLiveService;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhUserPersonageLive")
public class YhUserPersonageLiveController {

	@Autowired
	YhUserPersonageLiveService yhUserPersonageLiveService;
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@ApiOperation(value = "查询生活方式")
	@RequestMapping(value = "/selectLive", method = RequestMethod.GET)
	@ResponseBody
	public YhUserPersonageLive selectYhUserPersonageLive(String userCode) {
		return yhUserPersonageLiveService.selectByPrimaryKey(userCode);
		
	}
	@ApiOperation(value = "修改生活方式")
	@RequestMapping(value = "/updateLive", method = RequestMethod.POST)
	@ResponseBody
	public YhUserPersonageLive updateYhUserPersonageLive(YhUserPersonageLive yhUserPersonageLive,String[] dietsup) {
		//创建一个StringBuffer的对象
		
		StringBuffer str2 = new StringBuffer();
		for (int i = 0; i < dietsup.length; i++) {
			str2.append(dietsup[i]) ;
			if(i != dietsup.length - 1) {
				str2.append(",");
			}
			
		}
		yhUserPersonageLive.setDietsup(str2.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取String类型的时间
		String createdate = sdf.format(new Date());
		if(yhUserPersonageLive.getId()==null ||"".equals(yhUserPersonageLive.getId())) {
			yhUserPersonageLive.setAddtime(createdate);
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonageLive.getUsercode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			yhUserPersonageLiveService.insertSelective(yhUserPersonageLive);
		}else {
			yhUserPersonageLive.setUpdatetime(createdate);
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserPersonageLive.getUsercode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			yhUserPersonageLiveService.updateByPrimaryKey(yhUserPersonageLive);
		}
		
			String usercode = yhUserPersonageLive.getUsercode();
			YhUserPersonageLive personageLive = yhUserPersonageLiveService.selectByPrimaryKey(usercode);
		
			return personageLive;
		
	}
}
