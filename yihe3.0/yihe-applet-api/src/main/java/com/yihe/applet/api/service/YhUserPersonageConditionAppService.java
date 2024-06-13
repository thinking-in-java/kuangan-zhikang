package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhUserPersonageConditionApp;

public interface YhUserPersonageConditionAppService {
	 YhUserPersonageConditionApp showcondition(String userCode);
	 int insertSelective(YhUserPersonageConditionApp record);

	    YhUserPersonageConditionApp selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(YhUserPersonageConditionApp record);

	YhUserPersonageConditionApp selectByHeightAndWeight(String userCode);
}
