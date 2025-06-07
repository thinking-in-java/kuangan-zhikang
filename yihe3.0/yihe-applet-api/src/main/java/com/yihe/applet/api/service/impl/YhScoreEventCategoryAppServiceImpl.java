package com.yihe.applet.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yihe.applet.api.domain.YhHealthStarApp;
import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.domain.YhUserStepApp;
import com.yihe.applet.api.service.YhHealthStarAppService;
import com.yihe.applet.api.service.YhUserStepAppService;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhScoreEventCategoryWebService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhScoreEventCategoryApp;
import com.yihe.applet.api.mapper.YhScoreEventCategoryAppMapper;
import com.yihe.applet.api.service.YhScoreEventCategoryAppService;
@Service
public class YhScoreEventCategoryAppServiceImpl implements YhScoreEventCategoryAppService{
	private static final Logger LOGGER = LoggerFactory.getLogger(YhScoreEventCategoryAppServiceImpl.class);

	@Autowired
	YhScoreEventCategoryAppMapper yhScoreEventCategoryAppMapper;

	@Autowired
	// 积分规则
			YhScoreEventCategoryAppService yhScoreEventCategoryService;
	@Autowired
	YhHealthStarAppService yhHealthStarService;
	@Autowired
	// 总积分
			YhUserStepAppService yhUserStepService;
	@Autowired
	YhUserReginfoService yhUserReginfoApp;

	@Autowired
	YhScoreEventCategoryWebService scoreEventCategoryWebService;

	@Override
	public YhScoreEventCategoryApp selectEventType(String eventType, String orgCode) {
		// TODO Auto-generated method stub
		return yhScoreEventCategoryAppMapper.selectEventType(eventType,orgCode);
	}

	@Override
	public List<Map<String, Object>> selectIntegralStatus(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhScoreEventCategoryAppMapper.selectIntegralStatus(map);
	}

	@Override
	public String seleventType(String eventName) {
		return yhScoreEventCategoryAppMapper.seleventType(eventName);
	}

	@Override
	public Integer stepPoints(String usercode, String step) {
		if (StringUtil.isEmpty(usercode) || StringUtil.isEmpty(step)) {
			return 0;
		}
		Integer stepNum = Integer.valueOf(step);
		if (stepNum < 6000) {
			return 0;
		}

		if (stepNum >= 6000 && stepNum < 8000) {
			return scoreEventCategoryWebService.saveMark("sport_step_6000",usercode);
		} else if (stepNum >= 8000 && stepNum < 10000) {
			return  scoreEventCategoryWebService.saveMark("sport_step_8000",usercode);
		} else if (stepNum >= 10000) {
			return  scoreEventCategoryWebService.saveMark("sport_step_10000",usercode);
		}
		return 0;
	}

}
