package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhPressureAssessmentApp;

public interface YhPressureAssessmentAppMapper {
    int deleteByPrimaryKey(Integer paId);

    int insert(YhPressureAssessmentApp record);

    int insertSelective(YhPressureAssessmentApp record);

    YhPressureAssessmentApp selectByPrimaryKey(Integer paId);

    int updateByPrimaryKeySelective(YhPressureAssessmentApp record);

    int updateByPrimaryKey(YhPressureAssessmentApp record);
    
    List<YhPressureAssessmentApp> selectAllpstre();
    
    
}