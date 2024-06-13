package com.yihe.applet.api.controller;

import java.util.Date;
import java.util.List;

import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserPersonageHospitalizedApp;
import com.yihe.applet.api.service.YhUserPersonageHospitalizedAppService;

import io.swagger.annotations.Api;

/**
 * YhUserPersonageHospitalizedcontroller
 * Created by dong on 2020/2/20.
 */
@Controller
@RequestMapping("/api/YhUserPersonageHospitalized")
@Api(value = "YhUserPersonageHospitalized控制器", description = "YhUserPersonageHospitalized管理")
public class YhUserPersonageHospitalizedController  {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPersonageHospitalizedController.class);
    @Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
    @Autowired
    YhUserPersonageHospitalizedAppService yhUserPersonageHospitalizedService;
    
    
    /**
	* 删除住院情况
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/hospitalizeddele")
	@ResponseBody
	public Result liveadd(int id) {
		if(id!=0) {
			int i=yhUserPersonageHospitalizedService.deleteByPrimaryKey(id);
			return Result.ok(i);
		}
		return Result.error();
	}
	
	
    /**
	* 增加住院情况
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/hospitalizedadd")
	@ResponseBody
	public Result liveadd(YhUserPersonageHospitalizedApp cond) {
		int row=0;
		YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
		yhUserEnrolinfo.setEnergyUptime(new Date());
		yhUserEnrolinfo.setUserCode(cond.getUserCode());
		if(cond.getId()==null || "".equals(cond.getId())) {
			row = yhUserPersonageHospitalizedService.insertSelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			
		}else{
			row = yhUserPersonageHospitalizedService.updateByPrimaryKeySelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);

		}
		return Result.ok(row);
	}

    /**
	 * 根据code查询全部历史信息
	 * @param eid
	 * @return
	 */
	@RequestMapping(value = "/showhospitalized")
	@ResponseBody
	public Result findInfoByeidAll(String usercode) {

		List<YhUserPersonageHospitalizedApp> list = yhUserPersonageHospitalizedService.showhospitalized(usercode);

		if (null != list) {
			return Result.ok(list);
		}

		return Result.error();
	}
	/**
	 * 根据id查询
	 * @param eid
	 * @return
	 */
	@RequestMapping(value = "/findhospitalized")
	@ResponseBody
	public Result findInfoByid (Integer id) {
		YhUserPersonageHospitalizedApp hospitalized = yhUserPersonageHospitalizedService.selectByPrimaryKey(id);
		return Result.ok(hospitalized);
		
	}

}