package com.yihe.applet.api.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserPersonageNonimmuneApp;
import com.yihe.applet.api.service.YhUserPersonageNonimmuneAppService;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;

/**
 * YhUserPersonageNonimmunecontroller
 * Created by dong on 2020/2/21.
 */
@Controller
@RequestMapping("/api/YhUserPersonageNonimmune")
@Api(value = "YhUserPersonageNonimmune控制器", description = "YhUserPersonageNonimmune管理")
public class YhUserPersonageNonimmuneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPersonageNonimmuneController.class);
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
    @Autowired
    YhUserPersonageNonimmuneAppService yhUserPersonageNonimmuneService;
    
    
    /**
	* 删除非免疫规划预防接种史
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/nonimmunedele")
	@ResponseBody
	public Result liveadd(int id) {
		if(id!=0) {
			int i=yhUserPersonageNonimmuneService.deleteByPrimaryKey(id);
			return Result.ok(i);
		}
		return Result.error();
	}
	
	
    /**
	* 增加非免疫规划预防接种史
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/nonimmuneadd")
	@ResponseBody
	public Result liveadd(YhUserPersonageNonimmuneApp cond) {
		int row = 0;
		YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
		yhUserEnrolinfo.setEnergyUptime(new Date());
		yhUserEnrolinfo.setUserCode(cond.getUserCode());
		if(cond.getId()==null || "".equals(cond.getId())) {
			row=yhUserPersonageNonimmuneService.insertSelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}else {
			yhUserPersonageNonimmuneService.updateByPrimaryKeySelective(cond);
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}
		return Result.ok(row);
	}

    /**
	 * 根据code查询全部历史信息
	 * @param eid
	 * @return
	 */
	@RequestMapping(value = "/shownonimmune")
	@ResponseBody
	public Result findInfoByeidAll(String usercode) {
		List<YhUserPersonageNonimmuneApp> list = yhUserPersonageNonimmuneService.shownonimmune(usercode);

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
	@RequestMapping(value = "/findInoculate")
	@ResponseBody
	public Result findinoculateByid(String id) {
		YhUserPersonageNonimmuneApp nonimmune = yhUserPersonageNonimmuneService.selectByPrimaryKey(Integer.valueOf(id));
		return Result.ok(nonimmune);
		
	}


}