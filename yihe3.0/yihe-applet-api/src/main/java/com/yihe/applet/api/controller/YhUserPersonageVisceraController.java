package com.yihe.applet.api.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserPersonageVisceraApp;
import com.yihe.applet.api.service.YhUserPersonageVisceraAppService;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.service.YhUserEnrolinfoService;

import io.swagger.annotations.Api;

/**
 * YhUserPersonageVisceracontroller
 * Created by dong on 2020/2/18.
 */
@Controller
@RequestMapping("/api/YhUserPersonageViscera")
@Api(value = "YhUserPersonageViscera控制器", description = "YhUserPersonageViscera管理")
public class YhUserPersonageVisceraController{

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPersonageVisceraController.class);
    @Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
    @Autowired
    YhUserPersonageVisceraAppService yhUserPersonageVisceraService;
    
    /**
	     * 增加修改脏器功能
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/visceraadd")
	@ResponseBody
	public int visceraadd(YhUserPersonageVisceraApp cond) {
	int i=0;
	YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
	yhUserEnrolinfo.setEnergyUptime(new Date());
	yhUserEnrolinfo.setUserCode(cond.getUserCode());
	if(cond==null) {
		i=0;
	}else {
		YhUserPersonageVisceraApp list=yhUserPersonageVisceraService.showviscera(cond.getUserCode());
		
		if(list==null) {
			i=yhUserPersonageVisceraService.insertSelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}else {
			cond.setId(list.getId());
			i=yhUserPersonageVisceraService.updateByPrimaryKeySelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}
	}
	return i;
	}

	 /**
	     *查询脏器功能
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/showviscera")
	@ResponseBody
	public YhUserPersonageVisceraApp showviscera(String usercode) {
		YhUserPersonageVisceraApp list=yhUserPersonageVisceraService.showviscera(usercode);
	return list;
	}
}