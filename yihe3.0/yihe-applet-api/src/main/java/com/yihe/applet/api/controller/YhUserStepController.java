package com.yihe.applet.api.controller;
import com.yihe.applet.api.domain.YhMemberGrade;
import com.yihe.applet.api.service.YhUserStepAppService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * YhUserStepcontroller
 * Created by yx on 2020/5/18.
 */
@Controller
@RequestMapping("/api/YhUserStep")
@Api(value = "YhUserStep控制器", description = "YhUserStep管理")
public class YhUserStepController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserStepController.class);
	@Autowired
	YhUserStepAppService yhUserStepService;
	
	/**
	 * 查询总积分
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value="/selectAggregate",method=RequestMethod.POST)
	@ResponseBody 
	public Integer selectAggregate (String userCode) {
		return yhUserStepService.selectAggregateSum(userCode);
		
		}

	/**
	 * 查询等级
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value="/selectGrade",method=RequestMethod.POST)
	@ResponseBody
	public Map selectGrade (String userCode) {
		Map<String, Object> map = new HashMap<>();
		Integer integer = yhUserStepService.selectAggregateSum(userCode);
		integer = integer == null ? 0 : integer;
		YhMemberGrade grade = yhUserStepService.findGrade(integer);
		map.put("vipGrade", grade.getVipGrade());
		map.put("startEnergy", grade.getMinEnergy() == 0 ? 0 : grade.getMinEnergy() - 1 );
		map.put("nowEnergy", integer);
		map.put("img", grade.getImg());
		if (grade.getMaxEnergy() != null) {
			map.put("isfalg", true);
			map.put("nextEnergy", grade.getMaxEnergy());
			map.put("shortEnergy", grade.getMaxEnergy().intValue() - integer);
		} else {
			map.put("isfalg", false);
			map.put("nextEnergy", 0);
			map.put("shortEnergy", 0);
		}
		return map;
	}
}