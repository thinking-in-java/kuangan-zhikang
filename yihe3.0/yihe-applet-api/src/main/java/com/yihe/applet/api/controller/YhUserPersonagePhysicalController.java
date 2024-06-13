package com.yihe.applet.api.controller;
import io.swagger.annotations.Api;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserPersonagePhysicalApp;
import com.yihe.applet.api.service.YhUserPersonagePhysicalAppService;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.service.YhUserEnrolinfoService;

/**
 * YhUserPersonagePhysicalcontroller
 * Created by dong on 2020/2/18.
 */
@Controller
@RequestMapping("/api/YhUserPersonagePhysical")
@Api(value = "YhUserPersonagePhysical控制器", description = "YhUserPersonagePhysical管理")
public class YhUserPersonagePhysicalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPersonagePhysicalController.class);
    @Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
    @Autowired
    YhUserPersonagePhysicalAppService yhUserPersonagePhysicalService;
    
    /**
	     * 增加修改查体
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/physicaladd")
	@ResponseBody
	public int visceraadd(YhUserPersonagePhysicalApp cond) {
	int i=0;
	YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
	yhUserEnrolinfo.setEnergyUptime(new Date());
	yhUserEnrolinfo.setUserCode(cond.getUserCode());
	if(cond==null) {
		i=0;
	}else {
		YhUserPersonagePhysicalApp list=yhUserPersonagePhysicalService.showphysical(cond.getUserCode());
		
		if(list==null) {
			i=yhUserPersonagePhysicalService.insertSelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}else {
			cond.setId(list.getId());
			
			i=yhUserPersonagePhysicalService.updateByPrimaryKeySelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}
	}
	return i;
	}

	 /**
	     *查询查体
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/showphysical")
	@ResponseBody
	public YhUserPersonagePhysicalApp showviscera(String usercode) {
		YhUserPersonagePhysicalApp list=yhUserPersonagePhysicalService.showphysical(usercode);
	return list;
	}



}