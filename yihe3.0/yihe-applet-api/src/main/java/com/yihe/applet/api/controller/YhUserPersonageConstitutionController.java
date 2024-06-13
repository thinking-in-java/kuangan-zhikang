package com.yihe.applet.api.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserPersonageConstitutionApp;
import com.yihe.applet.api.service.YhUserPersonageConstitutionAppService;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.service.YhUserEnrolinfoService;

import io.swagger.annotations.Api;

/**
 * YhUserPersonageConstitutioncontroller
 * Created by dong on 2020/2/20.
 */
@Controller
@RequestMapping("/api/YhUserPersonageConstitution")
@Api(value = "YhUserPersonageConstitution控制器", description = "YhUserPersonageConstitution管理")
public class YhUserPersonageConstitutionController  {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPersonageConstitutionController.class);
    @Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
    @Autowired
    YhUserPersonageConstitutionAppService yhUserPersonageConstitutionService;

    /**
	     * 增加修改中医体质
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/constitutionadd")
	@ResponseBody
	public int liveadd(YhUserPersonageConstitutionApp cond) {
	int i=0;
	YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
	yhUserEnrolinfo.setEnergyUptime(new Date());
	yhUserEnrolinfo.setUserCode(cond.getUserCode());
	if(cond==null) {
		i=0;
	}else {
		YhUserPersonageConstitutionApp list=yhUserPersonageConstitutionService.showconstitution(cond.getUserCode());
		
		if(list==null) {
			i=yhUserPersonageConstitutionService.insertSelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}else {
			cond.setId(list.getId());
			
			i=yhUserPersonageConstitutionService.updateByPrimaryKeySelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}
	}
	return i;
	}
	
	 /**
	     *查询中医体质
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/showconstitution")
	@ResponseBody
	public YhUserPersonageConstitutionApp showlive(String usercode) {
		YhUserPersonageConstitutionApp list=yhUserPersonageConstitutionService.showconstitution(usercode);
	return list;
	}


}