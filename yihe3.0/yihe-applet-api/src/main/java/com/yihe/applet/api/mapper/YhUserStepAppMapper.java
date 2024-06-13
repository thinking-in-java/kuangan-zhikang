package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhMemberGrade;
import com.yihe.applet.api.domain.YhUserStepApp;

public interface YhUserStepAppMapper {
    int deleteByPrimaryKey(Integer stepId);

    int insert(YhUserStepApp record);

    int insertSelective(YhUserStepApp record);

    YhUserStepApp selectByPrimaryKey(Integer stepId);

    int updateByPrimaryKeySelective(YhUserStepApp record);

    int updateByPrimaryKey(YhUserStepApp record);
    
    YhUserStepApp selectAggregate (String userCode);

    Integer selectAggregateSum (String userCode);
    //修改总积分
    int updateAggregateScore (String userCode);

    YhMemberGrade findGrade(Integer energy);
}