package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhpressureAssessment;

public interface YhpressureAssessmentMapper {
    int deleteByPrimaryKey(Integer paId);

    int insert(YhpressureAssessment record);

    int insertSelective(YhpressureAssessment record);

    YhpressureAssessment selectByPrimaryKey(Integer paId);

    int updateByPrimaryKeySelective(YhpressureAssessment record);

    int updateByPrimaryKey(YhpressureAssessment record);
    
    List<YhpressureAssessment> selectAllPstriPressure();
}