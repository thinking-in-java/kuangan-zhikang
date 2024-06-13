package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserPersonageConditionApp;
import com.yihe.applet.api.mapper.YhUserPersonageConditionAppMapper;
import com.yihe.applet.api.service.YhUserPersonageConditionAppService;

@Service
public class YhUserPersonageConditionAppServiceImpl implements YhUserPersonageConditionAppService{
	@Autowired
	YhUserPersonageConditionAppMapper  yhUserPersonageConditionAppMapper;

	@Override
	public YhUserPersonageConditionApp showcondition(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageConditionAppMapper.showcondition(userCode);
	}

	@Override
	public int insertSelective(YhUserPersonageConditionApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageConditionAppMapper.insertSelective(record);
	}

	@Override
	public YhUserPersonageConditionApp selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonageConditionAppMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserPersonageConditionApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageConditionAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhUserPersonageConditionApp selectByHeightAndWeight(String userCode) {
		return yhUserPersonageConditionAppMapper.selectByHeightAndWeight(userCode);
	}
}
