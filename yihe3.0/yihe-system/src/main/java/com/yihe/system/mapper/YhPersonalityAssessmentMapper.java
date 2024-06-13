package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhPersonalityAssessment;

public interface YhPersonalityAssessmentMapper {
    int deleteByPrimaryKey(Integer paId);

    int insert(YhPersonalityAssessment record);

    int insertSelective(YhPersonalityAssessment record);

    YhPersonalityAssessment selectByPrimaryKey(Integer paId);

    int updateByPrimaryKeySelective(YhPersonalityAssessment record);

    int updateByPrimaryKey(YhPersonalityAssessment record);
    
    List<YhPersonalityAssessment> selectAllTypeAPersonality(); 
}