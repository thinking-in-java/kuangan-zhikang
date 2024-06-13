package com.yihe.system.service;

import com.yihe.system.domain.YhUserPersonageCondition;
public interface YhUserPersonageConditionService{

	YhUserPersonageCondition selectByPrimaryKey(String userCode);
	int updateByPrimaryKey(YhUserPersonageCondition yhUserPersonageCondition);
	 int insertSelective(YhUserPersonageCondition yhUserPersonageCondition);
}
