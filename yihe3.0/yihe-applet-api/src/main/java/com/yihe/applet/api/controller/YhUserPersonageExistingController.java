package com.yihe.applet.api.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserPersonageExistingApp;
import com.yihe.applet.api.service.YhUserPersonageExistingAppService;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.service.YhUserEnrolinfoService;

import io.swagger.annotations.Api;

/**
 * YhUserPersonageExistingcontroller
 * Created by dong on 2020/2/20.
 */
@Controller
@RequestMapping("/api/YhUserPersonageExisting")
@Api(value = "YhUserPersonageExisting控制器", description = "YhUserPersonageExisting管理")
public class YhUserPersonageExistingController{

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPersonageExistingController.class);
    @Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
    @Autowired
    YhUserPersonageExistingAppService yhUserPersonageExistingService;

    /**
	     * 增加修改现存问题
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/existingadd")
	@ResponseBody
	public int liveadd(YhUserPersonageExistingApp cond) {
	int i=0;
	YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
	yhUserEnrolinfo.setEnergyUptime(new Date());
	yhUserEnrolinfo.setUserCode(cond.getUserCode());
	if(cond==null) {
		i=0;
	}else {
		YhUserPersonageExistingApp list=yhUserPersonageExistingService.showexisting(cond.getUserCode());
		if(list==null) {
			i=yhUserPersonageExistingService.insertSelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}else {
			cond.setId(list.getId());
			
			i=yhUserPersonageExistingService.updateByPrimaryKeySelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}
	}
	return i;
	}
	
	 /**
	     *查询现存问题
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/showexisting")
	@ResponseBody
	public YhUserPersonageExistingApp showlive(String usercode) {
		YhUserPersonageExistingApp list=yhUserPersonageExistingService.showexisting(usercode);
	return list;
	}
	


}