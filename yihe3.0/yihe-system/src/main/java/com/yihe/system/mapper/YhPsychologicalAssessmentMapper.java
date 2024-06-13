package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhPsychologicalAssessment;

public interface YhPsychologicalAssessmentMapper {
    int deleteByPrimaryKey(Integer paId);

    int insert(YhPsychologicalAssessment record);

    int insertSelective(YhPsychologicalAssessment record);

    YhPsychologicalAssessment selectByPrimaryKey(Integer paId);

    int updateByPrimaryKeySelective(YhPsychologicalAssessment record);

    int updateByPrimaryKey(YhPsychologicalAssessment record);
    
    List<YhPsychologicalAssessment> selectAllmentalMentalHealth();
}