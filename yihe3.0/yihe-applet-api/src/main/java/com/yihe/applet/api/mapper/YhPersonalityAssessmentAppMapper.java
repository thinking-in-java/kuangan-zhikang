package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhPersonalityAssessmentApp;

public interface YhPersonalityAssessmentAppMapper {
    int deleteByPrimaryKey(Integer paId);

    int insert(YhPersonalityAssessmentApp record);

    int insertSelective(YhPersonalityAssessmentApp record);

    YhPersonalityAssessmentApp selectByPrimaryKey(Integer paId);

    int updateByPrimaryKeySelective(YhPersonalityAssessmentApp record);

    int updateByPrimaryKey(YhPersonalityAssessmentApp record);
    
    List<YhPersonalityAssessmentApp> selectAll();
}