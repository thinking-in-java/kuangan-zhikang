package com.yihe.applet.api.controller;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yihe.applet.api.domain.SymptomVojoApp;
import com.yihe.applet.api.domain.YhUserPersonageSymptomApp;
import com.yihe.applet.api.service.YhUserPersonageSymptomAppService;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.service.YhUserEnrolinfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * YhUserPersonageSymptomcontroller
 * Created by dong on 2020/2/12.
 */
@Controller
@RequestMapping("/api/YhUserPersonageSymptom")
@Api(value = "YhUserPersonageSymptom控制器", description = "YhUserPersonageSymptom管理")
public class YhUserPersonageSymptomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPersonageSymptomController.class);
	

	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
    @Autowired
    YhUserPersonageSymptomAppService yhUserPersonageSymptomService;
    
	
    /**
     * 根据code查询症状
     * @param name
     * @param usercode
     * @return
     */
	@RequestMapping(value = "/showsymptom")
	@ResponseBody
	public YhUserPersonageSymptomApp showSymptom(String userCode) {
		YhUserPersonageSymptomApp showsymptom = yhUserPersonageSymptomService.showsymptom(userCode);
		return showsymptom;
	
	}
	
    /**
     * 增加修改症状
     * @param name
     * @param usercode
     * @return
     */
	@RequestMapping(value = "/symptomadd")
	@ResponseBody
	public int modifyBmiInfo(YhUserPersonageSymptomApp ptom) {
		String usercode = ptom.getUserCode();
		YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
		yhUserEnrolinfo.setEnergyUptime(new Date());
		yhUserEnrolinfo.setUserCode(usercode);
		YhUserPersonageSymptomApp list=yhUserPersonageSymptomService.showsymptom(usercode);
		int i=0;
		if(list==null) {
			i=yhUserPersonageSymptomService.insertSelective(ptom);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}else {
			
			i=yhUserPersonageSymptomService.updateByPrimaryKeySelective(ptom);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}
		
		return i;
	}




}