package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhMemberGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserStepApp;
import com.yihe.applet.api.mapper.YhUserStepAppMapper;
import com.yihe.applet.api.service.YhUserStepAppService;
@Service
public class YhUserStepAppServiceImpl implements YhUserStepAppService{
	@Autowired
	YhUserStepAppMapper yhUserStepAppMapper;
	
	@Override
	public YhUserStepApp selectAggregate(String userCode) {
		// TODO Auto-generated method stub
		return yhUserStepAppMapper.selectAggregate(userCode);
	}

	@Override
	public Integer selectAggregateSum(String userCode) {
		return yhUserStepAppMapper.selectAggregateSum(userCode);
	}

	@Override
	public int updateAggregateScore(String userCode) {
		// TODO Auto-generated method stub
		return yhUserStepAppMapper.updateAggregateScore(userCode);
	}

	@Override
	public int insertSelective(YhUserStepApp record) {
		// TODO Auto-generated method stub
		return yhUserStepAppMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhUserStepApp record) {
		// TODO Auto-generated method stub
		return yhUserStepAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhMemberGrade findGrade(Integer energy) {
		return yhUserStepAppMapper.findGrade(energy);
	}


}
