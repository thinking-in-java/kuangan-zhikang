package com.yihe.applet.api.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserPersonageLiveApp;
import com.yihe.applet.api.service.YhUserPersonageLiveAppService;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.service.YhUserEnrolinfoService;

import io.swagger.annotations.Api;

/**
 * YhUserPersonageLivecontroller
 * Created by dong on 2020/2/17.
 */
@Controller
@RequestMapping("/api/YhUserPersonageLive")
@Api(value = "YhUserPersonageLive控制器", description = "YhUserPersonageLive管理")
public class YhUserPersonageLiveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPersonageLiveController.class);

	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
    @Autowired
    YhUserPersonageLiveAppService yhUserPersonageLiveService;

    /**
	     * 增加修改生活方式
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/liveadd")
	@ResponseBody
	public int liveadd(YhUserPersonageLiveApp cond) {
	int i=0;
	YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
	yhUserEnrolinfo.setEnergyUptime(new Date());
	yhUserEnrolinfo.setUserCode(cond.getUsercode());
	if(cond==null) {
		i=0;
	}else {
		YhUserPersonageLiveApp list=yhUserPersonageLiveService.showlive(cond.getUsercode());
		
		if(list==null) {
			i=yhUserPersonageLiveService.insertSelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}else {
			cond.setId(list.getId());
			i=yhUserPersonageLiveService.updateByPrimaryKeySelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}
	}
	return i;
	}
	
	 /**
	     *查询生活方式
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/showlive")
	@ResponseBody
	public YhUserPersonageLiveApp showlive(String usercode) {
		YhUserPersonageLiveApp list=yhUserPersonageLiveService.showlive(usercode);
	return list;
	}


}