package com.yihe.applet.api.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserPersonagePharmacyApp;
import com.yihe.applet.api.service.YhUserPersonagePharmacyAppService;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;

/**
 * YhUserPersonagePharmacycontroller
 * Created by dong on 2020/2/20.
 */
@Controller
@RequestMapping("/api/YhUserPersonagePharmacy")
@Api(value = "YhUserPersonagePharmacy控制器", description = "YhUserPersonagePharmacy管理")
public class YhUserPersonagePharmacyController  {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPersonagePharmacyController.class);
    @Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;

    @Autowired
    YhUserPersonagePharmacyAppService yhUserPersonagePharmacyService;
    
    
    /**
	* 删除主要用药
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/pharmacydele")
	@ResponseBody
	public Result liveadd(int id) {
		if(id!=0) {
			int i=yhUserPersonagePharmacyService.deleteByPrimaryKey(id);
			return Result.ok(i);
		}
		return Result.error();
	}
	
	
    /**
	* 增加主要用药
	* @param name
	* @param usercode
	* @return
	*/
	@RequestMapping(value = "/pharmacyadd")
	@ResponseBody
	public Result liveadd(YhUserPersonagePharmacyApp cond) {
		YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
		yhUserEnrolinfo.setEnergyUptime(new Date());
		yhUserEnrolinfo.setUserCode(cond.getUserCode());
		int row = 0;
		if(cond.getId()==null || "".equals(cond.getId())) {
			 row = yhUserPersonagePharmacyService.insertSelective(cond);
			 yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
		}else {
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			row = yhUserPersonagePharmacyService.updateByPrimaryKeySelective(cond);
		}
		return Result.ok(row);
	}

    /**
	 * 根据code查询全部历史信息
	 * @param eid
	 * @return
	 */
	@RequestMapping(value = "/showpharmacy")
	@ResponseBody
	public Result findInfoByeidAll(String usercode) {

		List<YhUserPersonagePharmacyApp> list = yhUserPersonagePharmacyService.showpharmacy(usercode);

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
	@RequestMapping(value = "/findPharmacy")
	@ResponseBody
	public Result findPharmacyByid(Integer id) {
		YhUserPersonagePharmacyApp pharmacy = yhUserPersonagePharmacyService.selectByPrimaryKey(id);
		return Result.ok(pharmacy);
		
	}


}