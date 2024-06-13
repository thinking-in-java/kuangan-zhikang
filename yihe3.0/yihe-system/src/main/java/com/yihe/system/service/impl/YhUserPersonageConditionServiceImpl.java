package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhUserPersonageCondition;
import com.yihe.system.mapper.YhUserPersonageConditionMapper;
import com.yihe.system.service.YhUserPersonageConditionService;
@Service
public class YhUserPersonageConditionServiceImpl implements YhUserPersonageConditionService{

	@Autowired
	YhUserPersonageConditionMapper yhUserPersonageConditionMapper;
	@Override
	public YhUserPersonageCondition selectByPrimaryKey(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageConditionMapper.selectByPrimaryKey(userCode);
	}
	@Override
	public int updateByPrimaryKey(YhUserPersonageCondition yhUserPersonageCondition) {
		// TODO Auto-generated method stub
		return yhUserPersonageConditionMapper.updateByPrimaryKeySelective(yhUserPersonageCondition);
	}
	@Override
	public int insertSelective(YhUserPersonageCondition yhUserPersonageCondition) {
		// TODO Auto-generated method stub
		return yhUserPersonageConditionMapper.insertSelective(yhUserPersonageCondition);
	}

}
