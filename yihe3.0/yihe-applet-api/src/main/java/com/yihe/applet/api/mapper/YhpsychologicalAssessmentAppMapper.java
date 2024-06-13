package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhpsychologicalAssessmentApp;

public interface YhpsychologicalAssessmentAppMapper {
    int deleteByPrimaryKey(Integer paId);

    int insert(YhpsychologicalAssessmentApp record);

    int insertSelective(YhpsychologicalAssessmentApp record);

    YhpsychologicalAssessmentApp selectByPrimaryKey(Integer paId);

    int updateByPrimaryKeySelective(YhpsychologicalAssessmentApp record);

    int updateByPrimaryKey(YhpsychologicalAssessmentApp record);
    
    List<YhpsychologicalAssessmentApp> selectAllHeartHealth();
}