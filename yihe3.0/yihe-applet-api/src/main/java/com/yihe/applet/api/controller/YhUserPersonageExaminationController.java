package com.yihe.applet.api.controller;
import io.swagger.annotations.Api;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserPersonageExaminationApp;
import com.yihe.applet.api.service.YhUserPersonageExaminationAppService;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.service.YhUserEnrolinfoService;

/**
 * YhUserPersonageExaminationcontroller
 * Created by dong on 2020/2/20.
 */
@Controller
@RequestMapping("/api/YhUserPersonageExamination")
@Api(value = "YhUserPersonageExamination控制器", description = "YhUserPersonageExamination管理")
public class YhUserPersonageExaminationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPersonageExaminationController.class);
    @Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
    @Autowired
    YhUserPersonageExaminationAppService yhUserPersonageExaminationService;

    /**
	     * 增加修改辅助查询 
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/examinationadd")
	@ResponseBody
	public int liveadd(YhUserPersonageExaminationApp cond) {
	int i=0;
	YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
	yhUserEnrolinfo.setEnergyUptime(new Date());
	yhUserEnrolinfo.setUserCode(cond.getUserCode());
	if(cond==null) {
		i=0;
	}else {
		YhUserPersonageExaminationApp list=yhUserPersonageExaminationService.showexamination(cond.getUserCode());
		
		if(list==null) {
			i=yhUserPersonageExaminationService.insertSelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}else {
			cond.setId(list.getId());
			
			i=yhUserPersonageExaminationService.updateByPrimaryKeySelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}
	}
	return i;
	}
	
	 /**
	*查询辅助查询 
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/showexamination")
	@ResponseBody
	public YhUserPersonageExaminationApp showlive(String usercode) {
		YhUserPersonageExaminationApp list=yhUserPersonageExaminationService.showexamination(usercode);
	return list;
	}
	


}