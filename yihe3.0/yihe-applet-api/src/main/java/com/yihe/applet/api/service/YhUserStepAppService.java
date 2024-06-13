package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhMemberGrade;
import com.yihe.applet.api.domain.YhUserStepApp;

public interface YhUserStepAppService {

	YhUserStepApp selectAggregate (String userCode);

	Integer selectAggregateSum (String userCode);

	//修改总积分
    int updateAggregateScore (String userCode);
    int insertSelective(YhUserStepApp record);
    int updateByPrimaryKey(YhUserStepApp record);
    YhMemberGrade findGrade(Integer energy);
}
